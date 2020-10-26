package com.game.prs.orchestrator;

import com.game.prs.player.Human;
import com.game.prs.result.TwoPlayerResult;
import com.game.prs.move.Move;
import com.game.prs.player.Computer;

import java.util.List;
import java.util.stream.Collectors;

public class HumanVsComputerGamemaster extends ConsoleTwoPlayerGamemaster {

    private final Human human;
    private final Computer computer;
    private final TwoPlayerJudge judge;
    private final List<Move> humanMoves;

    public HumanVsComputerGamemaster(Human human, List<Move> humanMoves, Computer computer, TwoPlayerJudge judge) {

        this.human = human;
        this.computer = computer;
        this.judge = judge;
        this.humanMoves = humanMoves;
    }

    @Override
    public String startGame() {

        StringBuilder roundAndGameSummary = new StringBuilder();

        List<TwoPlayerResult> gameResults = humanMoves.stream()
            .map(humanMove -> {
                human.setMove(humanMove);
                return judge.judgeFromFirstPlayerPointOfView(human, computer);
            })
            .collect(Collectors.toList());

        for (int i = 0; i < gameResults.size(); i++) {
            int round = i + 1;
            TwoPlayerResult roundResult = gameResults.get(i);
            String roundSummary = buildRoundSummary(round, human.getName(), computer.getName(), roundResult);
            roundAndGameSummary.append(roundSummary);
        }

        String gameSummary = buildGameSummary(human.getName(), computer.getName(), gameResults);
        roundAndGameSummary.append(gameSummary);
        return roundAndGameSummary.toString();
    }
}
