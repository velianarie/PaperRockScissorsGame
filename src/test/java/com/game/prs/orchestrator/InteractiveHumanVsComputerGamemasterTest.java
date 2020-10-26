package com.game.prs.orchestrator;

import com.game.prs.move.MoveSelection;
import com.game.prs.player.Human;
import com.game.prs.player.Computer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class InteractiveHumanVsComputerGamemasterTest {

    // We can't really test startGame() as it is interactive, so we'll test parseInput instead
    @Nested
    class ParseInput {

        InteractiveHumanVsComputerGamemaster gamemaster;

        @BeforeEach
        void setUp() {

            Human human = mock(Human.class);
            Computer computer = mock(Computer.class);
            TwoPlayerJudge judge = mock(TwoPlayerJudge.class);
            gamemaster = new InteractiveHumanVsComputerGamemaster(human, computer, judge);
        }

        @Test
        void inputPShouldBeParsedToPaper() {

            assertEquals(MoveSelection.PAPER, gamemaster.parse("P"));
            assertEquals(MoveSelection.PAPER, gamemaster.parse("p"));
        }

        @Test
        void inputRShouldBeParsedToRock() {

            assertEquals(MoveSelection.ROCK, gamemaster.parse("R"));
            assertEquals(MoveSelection.ROCK, gamemaster.parse("r"));
        }

        @Test
        void inputSShouldBeParsedToScissors() {

            assertEquals(MoveSelection.SCISSORS, gamemaster.parse("S"));
            assertEquals(MoveSelection.SCISSORS, gamemaster.parse("s"));
        }

        @Test
        void shouldErrorOnNotRecognisedInput() {

            assertThrows(IllegalStateException.class, () -> gamemaster.parse("meow"));
            assertThrows(IllegalStateException.class, () -> gamemaster.parse("PAPER"));
            assertThrows(IllegalStateException.class, () -> gamemaster.parse("ROCK"));
            assertThrows(IllegalStateException.class, () -> gamemaster.parse("SCISSORS"));
        }
    }
}
