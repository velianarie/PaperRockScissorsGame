package com.game.prs.result;

import com.game.prs.move.Move;

import java.util.Objects;

public class TwoPlayerResult {

    private final Move firstPlayerMove;
    private final Move secondPlayerMove;
    private final MoveResult moveResult;

    public TwoPlayerResult(Move firstPlayerMove, Move secondPlayerMove, MoveResult moveResult) {

        this.firstPlayerMove = firstPlayerMove;
        this.secondPlayerMove = secondPlayerMove;
        this.moveResult = moveResult;
    }

    public Move getFirstPlayerMove() {
        return firstPlayerMove;
    }

    public Move getSecondPlayerMove() {
        return secondPlayerMove;
    }

    public MoveResult getMoveResult() {
        return moveResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TwoPlayerResult that = (TwoPlayerResult) o;
        return Objects.equals(firstPlayerMove, that.firstPlayerMove) &&
            Objects.equals(secondPlayerMove, that.secondPlayerMove) &&
            moveResult == that.moveResult;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstPlayerMove, secondPlayerMove, moveResult);
    }

    @Override
    public String toString() {
        return "TwoPlayerResult{" +
            "firstPlayerMove=" + firstPlayerMove +
            ", secondPlayerMove=" + secondPlayerMove +
            ", moveResult=" + moveResult +
            '}';
    }
}
