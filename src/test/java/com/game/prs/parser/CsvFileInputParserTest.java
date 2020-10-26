package com.game.prs.parser;

import com.game.prs.move.MoveSelection;
import com.game.prs.move.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvFileInputParserTest {

    private CsvFileInputParser parser;

    @BeforeEach
    void setUp() {

        String resourceName = "testInput.csv";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());
        String absoluteFilePath = file.getAbsolutePath();
        assertTrue(absoluteFilePath.endsWith(resourceName), "Cannot find test csv file " + resourceName);

        parser = new CsvFileInputParser(absoluteFilePath);
    }

    @Nested
    class Parse {

        @Test
        void inputIsPaperOrPCaseInsensitive() {

            assertEquals(MoveSelection.PAPER, parser.parse("P"));
            assertEquals(MoveSelection.PAPER, parser.parse("p"));
            assertEquals(MoveSelection.PAPER, parser.parse("pApEr"));
        }

        @Test
        void inputIsRockOrRCaseInsensitive() {

            assertEquals(MoveSelection.ROCK, parser.parse("R"));
            assertEquals(MoveSelection.ROCK, parser.parse("r"));
            assertEquals(MoveSelection.ROCK, parser.parse("rOcK"));
        }

        @Test
        void inputIsScissorsOrSCaseInsensitive() {

            assertEquals(MoveSelection.SCISSORS, parser.parse("S"));
            assertEquals(MoveSelection.SCISSORS, parser.parse("s"));
            assertEquals(MoveSelection.SCISSORS, parser.parse("sCiSSors"));
        }

        @Test
        void unrecognisedInputShouldReturnNull() {

            assertNull(parser.parse("meow"));
        }
    }

    @Nested
    class ReadMoves {

        @Test
        void shouldReadMovesFromTestCsvFile() {

            List<Move> moves = parser.readMoves();
            assertEquals(4, moves.stream().filter(m -> m == MoveSelection.PAPER).count());
            assertEquals(4, moves.stream().filter(m -> m == MoveSelection.ROCK).count());
            assertEquals(4, moves.stream().filter(m -> m == MoveSelection.SCISSORS).count());
        }
    }
}
