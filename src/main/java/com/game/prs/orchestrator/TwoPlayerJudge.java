package com.game.prs.orchestrator;

import com.game.prs.player.Player;
import com.game.prs.result.MoveResult;
import com.game.prs.result.TwoPlayerResult;
import com.game.prs.move.Move;

public class TwoPlayerJudge {

    public void validateMove(Move move) {

        if (move == null) {
            throw new IllegalStateException("Cannot judge as player does not have move set");
        }

        if (move.winsAgainst().isEmpty() && move.losesTo().isEmpty()) {
            throw new IllegalStateException("Cannot judge as player cannot both wins against nothing and loses to nothing");
        }

        if (move.winsAgainst().equals(move.losesTo())) {
            throw new IllegalStateException("Cannot judge as player cannot both wins against and loses to the same move");
        }
    }

    public TwoPlayerResult judgeFromFirstPlayerPointOfView(Player firstPlayer, Player secondPlayer) {

        Move firstPlayerMove = firstPlayer.getMove();
        validateMove(firstPlayerMove);

        Move secondPlayerMove = secondPlayer.getMove();
        validateMove(secondPlayerMove);

        MoveResult moveResult = MoveResult.DRAW;
        if (firstPlayerMove.losesTo().isEmpty() || firstPlayerMove.winsAgainst().contains(secondPlayerMove)) {
            moveResult = MoveResult.WIN;
        } else if (firstPlayerMove.winsAgainst().isEmpty() || firstPlayerMove.losesTo().contains(secondPlayerMove)) {
            moveResult = MoveResult.LOSE;
        }

        return new TwoPlayerResult(firstPlayerMove, secondPlayerMove, moveResult);
    }
}
