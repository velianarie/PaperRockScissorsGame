package com.game.prs.parser;

import com.game.prs.move.MoveSelection;
import com.game.prs.move.Move;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CsvFileInputParser {

    private final String absoluteFilePath;

    public CsvFileInputParser(String filePath) {
        this.absoluteFilePath = filePath;
    }

    public Move parse(String input) {

        return switch (input.toUpperCase()) {
            case "P", "PAPER" -> MoveSelection.PAPER;
            case "R", "ROCK" -> MoveSelection.ROCK;
            case "S", "SCISSORS" -> MoveSelection.SCISSORS;
            default -> null;
        };
    }

    public List<Move> readMoves() {

        return readFromCsvFile().stream()
            .map(this::parse)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private List<String> readFromCsvFile() {

        List<String> moves = new ArrayList<>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(absoluteFilePath));
            String line;
            while ((line = br.readLine()) != null) {
                moves = Arrays.stream(line.split(",")).map(String::trim).collect(Collectors.toList());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return moves;
    }
}
