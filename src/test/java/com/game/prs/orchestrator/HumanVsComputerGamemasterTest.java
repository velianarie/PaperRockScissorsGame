package com.game.prs.orchestrator;

import com.game.prs.move.MoveSelection;
import com.game.prs.move.Move;
import com.game.prs.player.Computer;
import com.game.prs.player.Human;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HumanVsComputerGamemasterTest {

    @Mock
    private Human human;

    @Mock
    private Computer computer;

    @Test
    void startGameShouldReturnRoundAndGameSummary() {

        when(human.getName()).thenReturn("AAA");
        List<Move> humanMoves = List.of(
            MoveSelection.ROCK,
            MoveSelection.ROCK,
            MoveSelection.PAPER,
            MoveSelection.SCISSORS
        );
        when(human.getMove())
            .thenReturn(humanMoves.get(0))
            .thenReturn(humanMoves.get(1))
            .thenReturn(humanMoves.get(2))
            .thenReturn(humanMoves.get(3));

        when(computer.getName()).thenReturn("Computer");
        when(computer.getMove())
            .thenReturn(MoveSelection.PAPER)
            .thenReturn(MoveSelection.PAPER)
            .thenReturn(MoveSelection.PAPER)
            .thenReturn(MoveSelection.PAPER);

        HumanVsComputerGamemaster gamemaster = new HumanVsComputerGamemaster(human, humanMoves, computer, new TwoPlayerJudge());

        StringBuilder expected = new StringBuilder();
        String newLine = System.lineSeparator();
        expected.append("* Round: 1").append(newLine);
        expected.append("AAA's move: ROCK").append(newLine);
        expected.append("Computer's move: PAPER").append(newLine);
        expected.append("AAA LOSE").append(newLine);
        expected.append("* Round: 2").append(newLine);
        expected.append("AAA's move: ROCK").append(newLine);
        expected.append("Computer's move: PAPER").append(newLine);
        expected.append("AAA LOSE").append(newLine);
        expected.append("* Round: 3").append(newLine);
        expected.append("AAA's move: PAPER").append(newLine);
        expected.append("Computer's move: PAPER").append(newLine);
        expected.append("It's a DRAW").append(newLine);
        expected.append("* Round: 4").append(newLine);
        expected.append("AAA's move: SCISSORS").append(newLine);
        expected.append("Computer's move: PAPER").append(newLine);
        expected.append("AAA WIN").append(newLine);
        expected.append("================").append(newLine);
        expected.append("  GAME SUMMARY").append(newLine);
        expected.append("================").append(newLine);
        expected.append("AAA").append(newLine);
        expected.append("* WIN: 1").append(newLine);
        expected.append("* LOSE: 2").append(newLine);
        expected.append("* DRAW: 1").append(newLine);
        expected.append("Computer").append(newLine);
        expected.append("* WIN: 2").append(newLine);
        expected.append("* LOSE: 1").append(newLine);
        expected.append("* DRAW: 1").append(newLine);
        expected.append("Computer WIN!").append(newLine);

        assertEquals(expected.toString(), gamemaster.startGame());
        verify(human, times(4)).setMove(any());
    }
}
