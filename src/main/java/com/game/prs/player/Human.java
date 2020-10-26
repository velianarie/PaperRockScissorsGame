package com.game.prs.player;

import com.game.prs.move.Move;

public class Human implements Player {

    private final String name;
    private Move move;

    public Human(String name) {

        this.name = name;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Move getMove() {
        return move;
    }
}
