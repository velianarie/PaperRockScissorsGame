package com.game.prs.orchestrator;

import com.game.prs.result.MoveResult;
import com.game.prs.result.TwoPlayerResult;

import java.util.List;

public abstract class ConsoleTwoPlayerGamemaster {

    private final String newLine = System.lineSeparator();

    public abstract String startGame();

    protected String buildRoundSummary(int round, String firstPlayerName, String secondPlayerName, TwoPlayerResult result) {

        StringBuilder sb = new StringBuilder();
        sb.append("* Round: ").append(round).append(newLine);
        sb.append(firstPlayerName).append("'s move: ").append(result.getFirstPlayerMove()).append(newLine);
        sb.append(secondPlayerName).append("'s move: ").append(result.getSecondPlayerMove()).append(newLine);

        MoveResult moveResult = result.getMoveResult();
        if (moveResult == MoveResult.DRAW) {
            sb.append("It's a DRAW").append(newLine);
        } else {
            sb.append(firstPlayerName).append(" ").append(result.getMoveResult()).append(newLine);
        }

        return sb.toString();
    }

    protected String buildGameSummary(String firstPlayerName, String secondPlayerName, List<TwoPlayerResult> gameResults) {

        long firstPlayerTotalWins = gameResults.stream().filter(r -> r.getMoveResult() == MoveResult.WIN).count();
        long firstPlayerTotalLoses = gameResults.stream().filter(r -> r.getMoveResult() == MoveResult.LOSE).count();
        long totalDraws = gameResults.stream().filter(r -> r.getMoveResult() == MoveResult.DRAW).count();

        StringBuilder sb = new StringBuilder();
        sb.append("================").append(newLine);
        sb.append("  GAME SUMMARY").append(newLine);
        sb.append("================").append(newLine);
        sb.append(firstPlayerName).append(newLine);
        sb.append("* WIN: ").append(firstPlayerTotalWins).append(newLine);
        sb.append("* LOSE: ").append(firstPlayerTotalLoses).append(newLine);
        sb.append("* DRAW: ").append(totalDraws).append(newLine);

        sb.append(secondPlayerName).append(newLine);
        sb.append("* WIN: ").append(firstPlayerTotalLoses).append(newLine);
        sb.append("* LOSE: ").append(firstPlayerTotalWins).append(newLine);
        sb.append("* DRAW: ").append(totalDraws).append(newLine);

        if (firstPlayerTotalWins == firstPlayerTotalLoses) {
            sb.append("It's a DRAW!").append(newLine);
        } else if (firstPlayerTotalWins > firstPlayerTotalLoses) {
            sb.append(firstPlayerName).append(" WIN!").append(newLine);
        } else {
            sb.append(secondPlayerName).append(" WIN!").append(newLine);
        }
        return sb.toString();
    }
}
