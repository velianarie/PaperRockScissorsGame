## Tools
I'm using the following to build the solution:
* IDE: IntelliJ Community 2020.2
* Build: Gradle
* Testing/Mocking: JUnit Jupiter

## Solution

I chose to keep it simple and create the solution as a console application.

### Main
This is the entry point to run the console application.

### Player
`Computer` and `Human` for now, both implementing `Player` interface.

###### Computer Player Input
Computer player takes in a `Random` object which simply going to pick a random `Move` whenever asked.

###### Human Player Input
I created two ways to feed human player input into the game:
* `CsvFileInputParser`: reads only valid (case insensitive) user input ie. `P`, `R`, `S`, `PAPER`, `ROCK`, `SCISSORS` 
specified in a csv file and ignores the invalid ones.
* Interactive from command line: read on for more details


### Gamemaster
The purpose of having a gamemaster class is to avoid having too much code in the `Main` which is rather difficult to 
test. Although we can't really fully unit test interactive console application, we still can get some test coverage
by doing this.

For some reason, I ended up with three different kinds of gamemaster:
* `HumanVsComputerGamemaster`: at first I started with computer vs a very dumb human player whose moves are read in from
a csv file. This is what this gamemaster is doing.
* `ComputerVsComputerGamemaster`: once I got the simplest working, I started thinking since `Player` is an interface,
we can actually mix and match the players, we can even have a computer player pitches against another computer player.
This is how this gamemaster came to be. In fact, it is actually easier to implement than the human vs computer version, 
so it doesn't take a lot of time to create this. 
* `InteractiveHumanVsComputerGamemaster`: the interactive gamemaster is built in a similar way as the other two 
gamemasters. It is imho the hardest to implement as I've never had to read from console, so I had to google it a bit
and I decided to use `Scanner` for this. Note that I'm not sure how to unit test this interactive mode, but I have
enough unit tests for the other two gamemasters to be rather confident that it is working as they're all using the same
base class `ConsoleTwoPlayerGamemaster`.

### Win / Lose / Draw Logic
The win / lose logic is centralised in `MoveSelection` enum that implements `Move` interface. 

```
public interface Move {

    List<Move> winsAgainst();

    List<Move> losesTo();
}
```

I purposely make `winsAgainst()` and `losesTo()` a list to sort of model a matrix-like decision making. 
This might make things a bit complex but I think it's worth it for the extension possibility.
So for example if we introduce a new move `BLUNT_SCISSORS`, that loses to `PAPER`, `ROCK` and `SCISSORS` and wins against
nothing, then all we need to do is:
* add `BLUNT_SCISSORS` to `PAPER`, `ROCK`, `SCISSORS`'s `winsAgainst()` list
* add `PAPER`, `ROCK`, `SCISSORS` to `BLUNT_SCISSORS`'s `losesTo()` list
* leave `BLUNT_SCISSORS`'s `winsAgainst()` list empty <br />
and we're pretty much done.
 
Suppose we introduce yet another move `CRUMBLING_ROCK` and let's say it loses to `ROCK`, `SCISSORS` and `BLUNT_SCISSORS`
(all crumbles the weak rock) and it wins against `PAPER` (the paper cannot contain all the crumbles), then all we need
to do is:
* add `CRUMBLING_ROCK` to `ROCK`, `SCISSORS` and `BLUNT_SCISSORS`'s `winsAgainst()` list
* add `CRUMBLING_ROCK` to `PAPER`'s `losesTo()` list
* add `PAPER` to `CRUMBLING_ROCK`'s `winsAgainst()` list
 
I understand that it probably does not make sense for any player to choose a move with only a few items in the
`winsAgainst()` list as it reduces the chance of winning, however it might make sense if we have a new computer player 
type for example an easy level computer player.
 
I also understand that there's a room for win/lose logic inconsistency here everytime a new move is introduced as
I have not implemented a validation that every `Move` should appear in every other `Move`'s `winsAgainst()` OR
`losesTo()` list, but never in both list. Using the examples I mentioned above, a correct logic should look like this:

```
public enum MoveSelection implements Move {

    PAPER {

        @Override
        public List<Move> winsAgainst() {
            return List.of(ROCK, BLUNT_SCISSORS);
        }

        @Override
        public List<Move> losesTo() {
            return List.of(SCISSORS, CRUMBLING_ROCK);
        }
    },

    ROCK {

        @Override
        public List<Move> winsAgainst() {
            return List.of(SCISSORS, BLUNT_SCISSORS, CRUMBLING_ROCK);
        }

        @Override
        public List<Move> losesTo() {
            return List.of(PAPER);
        }
    },

    SCISSORS {

        @Override
        public List<Move> winsAgainst() {
            return List.of(PAPER, BLUNT_SCISSORS, CRUMBLING_ROCK);
        }

        @Override
        public List<Move> losesTo() {
            return List.of(ROCK);
        }
    },

    BLUNT_SCISSORS {

        @Override
        public List<Move> winsAgainst() {
            return List.of(CRUMBLING_ROCK);
        }

        @Override
        public List<Move> losesTo() {
            return List.of(PAPER, ROCK, SCISSORS);
        }
    },

    CRUMBLING_ROCK {

        @Override
        public List<Move> winsAgainst() {
            return List.of(PAPER);
        }

        @Override
        public List<Move> losesTo() {
            return List.of(ROCK, SCISSORS, BLUNT_SCISSORS);
        }
    }
}

```
Here we can see for example that `CRUMBLING_ROCK` appears in either `winsAgainst()` OR `losesTo()` list of each of the 
other enums, but never on both lists.

###### TwoPlayerJudge
The `TwoPlayerJudge` as its name says, takes in two `Player`s and will determine the winner from the first player's 
point of view. The `TwoPlayerJudge` uses the above decision matrix to decide whether the first player is winning, 
losing or it's a draw. The `TwoPlayerJudge` has some simple input validation, but as mentioned above, I have not 
implemented validation on the "matrix". Furthermore, if I do have time to implement this, I probably won't make it 
the responsibility of the judge.

