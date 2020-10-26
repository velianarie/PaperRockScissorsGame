package com.game.prs.orchestrator;

import com.game.prs.move.MoveSelection;
import com.game.prs.player.Computer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComputerVsComputerGamemasterTest {

    @Mock
    private Computer computer1;

    @Mock
    private Computer computer2;

    @Test
    void startGameShouldReturnRoundAndGameSummary() {

        when(computer1.getName()).thenReturn("Computer 1");
        when(computer1.getMove())
            .thenReturn(MoveSelection.PAPER)
            .thenReturn(MoveSelection.ROCK)
            .thenReturn(MoveSelection.SCISSORS);

        when(computer2.getName()).thenReturn("Computer 2");
        when(computer2.getMove())
            .thenReturn(MoveSelection.SCISSORS)
            .thenReturn(MoveSelection.ROCK)
            .thenReturn(MoveSelection.PAPER);

        ComputerVsComputerGamemaster gamemaster = new ComputerVsComputerGamemaster(computer1, computer2, new TwoPlayerJudge(), 3);

        StringBuilder expected = new StringBuilder();
        String newLine = System.lineSeparator();
        expected.append("* Round: 1").append(newLine);
        expected.append("Computer 1's move: PAPER").append(newLine);
        expected.append("Computer 2's move: SCISSORS").append(newLine);
        expected.append("Computer 1 LOSE").append(newLine);
        expected.append("* Round: 2").append(newLine);
        expected.append("Computer 1's move: ROCK").append(newLine);
        expected.append("Computer 2's move: ROCK").append(newLine);
        expected.append("It's a DRAW").append(newLine);
        expected.append("* Round: 3").append(newLine);
        expected.append("Computer 1's move: SCISSORS").append(newLine);
        expected.append("Computer 2's move: PAPER").append(newLine);
        expected.append("Computer 1 WIN").append(newLine);
        expected.append("================").append(newLine);
        expected.append("  GAME SUMMARY").append(newLine);
        expected.append("================").append(newLine);
        expected.append("Computer 1").append(newLine);
        expected.append("* WIN: 1").append(newLine);
        expected.append("* LOSE: 1").append(newLine);
        expected.append("* DRAW: 1").append(newLine);
        expected.append("Computer 2").append(newLine);
        expected.append("* WIN: 1").append(newLine);
        expected.append("* LOSE: 1").append(newLine);
        expected.append("* DRAW: 1").append(newLine);
        expected.append("It's a DRAW!").append(newLine);

        assertEquals(expected.toString(), gamemaster.startGame());
    }
}
