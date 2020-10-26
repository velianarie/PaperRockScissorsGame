package com.game.prs.orchestrator;

import com.game.prs.move.MoveSelection;
import com.game.prs.player.Player;
import com.game.prs.result.MoveResult;
import com.game.prs.move.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TwoPlayerJudgeTest {

    private TwoPlayerJudge judge;

    @BeforeEach
    void setUp() {

        judge = new TwoPlayerJudge();
    }

    @Nested
    class ValidateMove {

        @Mock
        private Move move;

        @Test
        void shouldErrorOnNullMove() {

            assertThrows(IllegalStateException.class, () -> judge.validateMove(move));
        }

        @Test
        void shouldErrorWhenMoveWinsAgainstNothingAndLosesToNothing() {

            when(move.winsAgainst()).thenReturn(List.of());
            when(move.losesTo()).thenReturn(List.of());
            assertThrows(IllegalStateException.class, () -> judge.validateMove(move));
        }

        @Test
        void shouldErrorWhenMoveWinsAgainstAndLosesToTheSameMove() {

            when(move.winsAgainst()).thenReturn(List.of(MoveSelection.PAPER));
            when(move.losesTo()).thenReturn(List.of(MoveSelection.PAPER));
            assertThrows(IllegalStateException.class, () -> judge.validateMove(move));
        }
    }

    @Nested
    class JudgeFromFirstPlayerPointOfView {

        @Mock
        private Player firstPlayer;

        @Mock
        private Player secondPlayer;

        @Nested
        class FirstPlayerPaperMove {

            @BeforeEach
            void setUp() {

                when(firstPlayer.getMove()).thenReturn(MoveSelection.PAPER);
            }

            @Test
            void shouldLoseToSecondPlayerScissorsMove() {

                when(secondPlayer.getMove()).thenReturn(MoveSelection.SCISSORS);
                assertEquals(MoveResult.LOSE, judge.judgeFromFirstPlayerPointOfView(firstPlayer, secondPlayer).getMoveResult());
            }

            @Test
            void shouldWinAgainstSecondPlayerRockMove() {

                when(secondPlayer.getMove()).thenReturn(MoveSelection.ROCK);
                assertEquals(MoveResult.WIN, judge.judgeFromFirstPlayerPointOfView(firstPlayer, secondPlayer).getMoveResult());
            }

            @Test
            void shouldDrawWhenSecondPlayerAlsoMakesPaperMove() {

                when(secondPlayer.getMove()).thenReturn(MoveSelection.PAPER);
                assertEquals(MoveResult.DRAW, judge.judgeFromFirstPlayerPointOfView(firstPlayer, secondPlayer).getMoveResult());
            }
        }

        @Nested
        class FirstPlayerRockMove {

            @BeforeEach
            void setUp() {

                when(firstPlayer.getMove()).thenReturn(MoveSelection.ROCK);
            }

            @Test
            void shouldLoseToSecondPlayerPaperMove() {

                when(secondPlayer.getMove()).thenReturn(MoveSelection.PAPER);
                assertEquals(MoveResult.LOSE, judge.judgeFromFirstPlayerPointOfView(firstPlayer, secondPlayer).getMoveResult());
            }

            @Test
            void shouldWinAgainstSecondPlayerScissorsMove() {

                when(secondPlayer.getMove()).thenReturn(MoveSelection.SCISSORS);
                assertEquals(MoveResult.WIN, judge.judgeFromFirstPlayerPointOfView(firstPlayer, secondPlayer).getMoveResult());
            }

            @Test
            void shouldDrawWhenSecondPlayerAlsoMakesRockMove() {

                when(secondPlayer.getMove()).thenReturn(MoveSelection.ROCK);
                assertEquals(MoveResult.DRAW, judge.judgeFromFirstPlayerPointOfView(firstPlayer, secondPlayer).getMoveResult());
            }
        }

        @Nested
        class FirstPlayerScissorsMove {

            @BeforeEach
            void setUp() {

                when(firstPlayer.getMove()).thenReturn(MoveSelection.SCISSORS);
            }

            @Test
            void shouldLoseToSecondPlayerRockMove() {

                when(secondPlayer.getMove()).thenReturn(MoveSelection.ROCK);
                assertEquals(MoveResult.LOSE, judge.judgeFromFirstPlayerPointOfView(firstPlayer, secondPlayer).getMoveResult());
            }

            @Test
            void shouldWinAgainstSecondPlayerPaperMove() {

                when(secondPlayer.getMove()).thenReturn(MoveSelection.PAPER);
                assertEquals(MoveResult.WIN, judge.judgeFromFirstPlayerPointOfView(firstPlayer, secondPlayer).getMoveResult());
            }

            @Test
            void shouldDrawWhenSecondPlayerAlsoMakesScissorsMove() {

                when(secondPlayer.getMove()).thenReturn(MoveSelection.SCISSORS);
                assertEquals(MoveResult.DRAW, judge.judgeFromFirstPlayerPointOfView(firstPlayer, secondPlayer).getMoveResult());
            }
        }

        @Nested
        class FirstPlayerHasEmptyListOfWinsAgainstAndLosesTo {

            @Mock
            private Move firstPlayerMove;

            @Mock
            private Move secondPlayerMove;

            @BeforeEach
            void setUp() {

                when(firstPlayer.getMove()).thenReturn(firstPlayerMove);
                when(secondPlayer.getMove()).thenReturn(secondPlayerMove);
            }

            @Test
            void shouldAlwaysWinWhenFirstPlayerHasEmptyListOfLosesTo() {

                when(firstPlayerMove.winsAgainst()).thenReturn(List.of(MoveSelection.ROCK));
                when(firstPlayerMove.losesTo()).thenReturn(List.of());
                when(secondPlayerMove.winsAgainst()).thenReturn(List.of(MoveSelection.PAPER));
                when(secondPlayerMove.losesTo()).thenReturn(List.of());
                assertEquals(MoveResult.WIN, judge.judgeFromFirstPlayerPointOfView(firstPlayer, secondPlayer).getMoveResult());
            }

            @Test
            void shouldAlwaysLoseWhenFirstPlayerHasEmptyListOfWinsAgainst() {

                when(firstPlayerMove.winsAgainst()).thenReturn(List.of());
                when(firstPlayerMove.losesTo()).thenReturn(List.of(MoveSelection.SCISSORS));
                when(secondPlayerMove.winsAgainst()).thenReturn(List.of(MoveSelection.PAPER));
                when(secondPlayerMove.losesTo()).thenReturn(List.of());
                assertEquals(MoveResult.LOSE, judge.judgeFromFirstPlayerPointOfView(firstPlayer, secondPlayer).getMoveResult());
            }
        }
    }
}
