package com.game.prs.move;

import java.util.List;

public enum MoveSelection implements Move {

    PAPER {
        @Override
        public List<Move> winsAgainst() {
            return List.of(ROCK);
        }

        @Override
        public List<Move> losesTo() {
            return List.of(SCISSORS);
        }
    },

    ROCK {
        @Override
        public List<Move> winsAgainst() {
            return List.of(SCISSORS);
        }

        @Override
        public List<Move> losesTo() {
            return List.of(PAPER);
        }
    },

    SCISSORS {
        @Override
        public List<Move> winsAgainst() {
            return List.of(PAPER);
        }

        @Override
        public List<Move> losesTo() {
            return List.of(ROCK);
        }
    }
}

