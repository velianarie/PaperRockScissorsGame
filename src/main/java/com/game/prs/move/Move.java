package com.game.prs.move;

import java.util.List;

public interface Move {

    List<Move> winsAgainst();

    List<Move> losesTo();
}
