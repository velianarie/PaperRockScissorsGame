package com.game.prs.player;

import com.game.prs.move.MoveSelection;
import com.game.prs.move.Move;

import java.util.Random;

public class Computer implements Player {

    private final String name;
    private final Random random;

    public Computer(String name, Random random) {

        this.name = name;
        this.random = random;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Move getMove() {

        MoveSelection[] moveSelections = MoveSelection.values();
        return moveSelections[random.nextInt(moveSelections.length)];
    }
}
