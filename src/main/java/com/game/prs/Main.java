package com.game.prs;

import com.game.prs.orchestrator.ComputerVsComputerGamemaster;
import com.game.prs.orchestrator.ConsoleTwoPlayerGamemaster;
import com.game.prs.orchestrator.HumanVsComputerGamemaster;
import com.game.prs.orchestrator.InteractiveHumanVsComputerGamemaster;
import com.game.prs.orchestrator.TwoPlayerJudge;
import com.game.prs.parser.CsvFileInputParser;
import com.game.prs.player.Computer;
import com.game.prs.player.Human;

import java.util.Objects;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Human human = new Human("Hooman");
        Random random = new Random();
        Computer computer = new Computer("Robo", random);
        TwoPlayerJudge judge = new TwoPlayerJudge();

        // I created 3 kinds of gamemaster, simply uncomment the one you want to run
        // 1) Human vs Computer: human input is read from a csv file under resources
        //runHumanVsComputer(human, computer, judge);

        // 2) Computer vs Computer: enter number of rounds and let them fight each other
        //runComputerVsComputer(random, computer, judge, 10);

        // 3) Interactive Human vs Computer: human input is read from the console
        runInteractiveHumanVsComputer(human, computer, judge);
    }

    private static void runHumanVsComputer(Human human, Computer computer, TwoPlayerJudge judge) {

        String filePath = Objects.requireNonNull(Main.class.getClassLoader().getResource("HumanMoves.csv")).getFile();
        CsvFileInputParser csvFileParser = new CsvFileInputParser(filePath);
        ConsoleTwoPlayerGamemaster gamemaster = new HumanVsComputerGamemaster(human, csvFileParser.readMoves(), computer, judge);
        System.out.println(gamemaster.startGame());
    }

    private static void runComputerVsComputer(Random random, Computer computer1, TwoPlayerJudge judge, int numberOfRounds) {

        Computer computer2 = new Computer("Robo Wannabe", random);
        ConsoleTwoPlayerGamemaster gamemaster = new ComputerVsComputerGamemaster(computer1, computer2, judge, numberOfRounds);
        System.out.println(gamemaster.startGame());
    }

    private static void runInteractiveHumanVsComputer(Human human, Computer computer, TwoPlayerJudge judge) {

        ConsoleTwoPlayerGamemaster gamemaster = new InteractiveHumanVsComputerGamemaster(human, computer, judge);
        gamemaster.startGame();
    }
}
