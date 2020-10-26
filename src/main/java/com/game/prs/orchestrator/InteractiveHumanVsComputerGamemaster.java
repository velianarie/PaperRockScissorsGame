package com.game.prs.orchestrator;

import com.game.prs.move.MoveSelection;
import com.game.prs.player.Computer;
import com.game.prs.player.Human;
import com.game.prs.result.TwoPlayerResult;
import com.game.prs.move.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InteractiveHumanVsComputerGamemaster extends ConsoleTwoPlayerGamemaster {

    private final Human human;
    private final Computer computer;
    private final TwoPlayerJudge judge;

    private final List<TwoPlayerResult> gameResults;

    public InteractiveHumanVsComputerGamemaster(Human human, Computer computer, TwoPlayerJudge judge) {

        this.human = human;
        this.computer = computer;
        this.judge = judge;

        this.gameResults = new ArrayList<>();
    }

    @Override
    public String startGame() {

        StringBuilder roundAndGameSummary = new StringBuilder();

        Scanner scanner = new Scanner(System.in);
        String pattern = "^P$|^p$|^R$|^r$|^S$|^s$";
        boolean isTerminated = false;

        int round = 1;
        while (!isTerminated) {
            System.out.print("Please enter [P]aper, [R]ock or [S]cissors or type FINISH to finish entering move: ");

            String line = scanner.nextLine();
            if (line.matches(pattern)) {
                Move validMove = parse(line);
                human.setMove(validMove);

                TwoPlayerResult roundResult = judge.judgeFromFirstPlayerPointOfView(human, computer);
                gameResults.add(roundResult);

                String roundSummary = buildRoundSummary(round, human.getName(), computer.getName(), roundResult);
                roundAndGameSummary.append(roundSummary);

                System.out.println(roundSummary);

                round++;
            } else if (line.toUpperCase().matches("FINISH")) {
                isTerminated = true;
                System.out.println("Thanks for playing. See game summary below:");
            } else {
                System.out.println("'" + line + "' is invalid.");
            }
        }

        String gameSummary = buildGameSummary(human.getName(), computer.getName(), gameResults);
        roundAndGameSummary.append(gameSummary);

        System.out.println(gameSummary);

        return roundAndGameSummary.toString();
    }

    public Move parse(String input) {

        return switch (input.toUpperCase()) {
            case "P" -> MoveSelection.PAPER;
            case "R" -> MoveSelection.ROCK;
            case "S" -> MoveSelection.SCISSORS;
            default -> throw new IllegalStateException("Cannot parse '" + input + "' to a valid Move");
        };
    }
}
