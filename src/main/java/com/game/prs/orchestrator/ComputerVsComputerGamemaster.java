package com.game.prs.orchestrator;

import com.game.prs.player.Computer;
import com.game.prs.result.TwoPlayerResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ComputerVsComputerGamemaster extends ConsoleTwoPlayerGamemaster {

    private final Computer computer1;
    private final Computer computer2;
    private final TwoPlayerJudge judge;
    private final int numberOfRounds;

    public ComputerVsComputerGamemaster(Computer computer1, Computer computer2, TwoPlayerJudge judge, int numberOfRounds) {

        this.computer1 = computer1;
        this.computer2 = computer2;
        this.judge = judge;
        this.numberOfRounds = numberOfRounds;
    }

    @Override
    public String startGame() {

        StringBuilder roundAndGameSummary = new StringBuilder();

        List<TwoPlayerResult> gameResults = IntStream.range(0, numberOfRounds)
            .mapToObj(i -> {
                TwoPlayerResult roundResult = judge.judgeFromFirstPlayerPointOfView(computer1, computer2);
                String roundSummary = buildRoundSummary(i + 1, computer1.getName(), computer2.getName(), roundResult);
                roundAndGameSummary.append(roundSummary);
                return roundResult;
            })
            .collect(Collectors.toList());

        String gameSummary = buildGameSummary(computer1.getName(), computer2.getName(), gameResults);
        roundAndGameSummary.append(gameSummary);

        return roundAndGameSummary.toString();
    }
}
