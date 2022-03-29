## Knight Moves

### Prompt

Please provide documentation on how to compile and execute it.

Minimum steps to reach the target by a Knight

Given a source (A, B) to destination (X, Y) points on a chessboard, we need to find the
minimal number of steps a Knight needs to move to the destination. If it is possible,
please print each of the steps it took and the number of steps. If it is not possible,
please print -1.

The program should allow users to enter the size of the chessboard and the
source/destination points.

For example:

```
./knight_move --board_size 10,10 --source 4,5 --dest 3,2
>> (4, 5) -> (5, 3) -> (3, 2)
>> 3
```

Bonus: Support infinite chessboard

### Instructions

To run on an infinite chess board, just leave off the `--board_size` parameter.

The easiest way to run is using gradle:

```
gradle run --args='--board_size 10,10 --source 0,0 --dest 9,9'
```

Tested using:

```
Gradle        7.4
Kotlin:       1.5.31
JVM:          17.0.2 (Homebrew 17.0.2+0)
OS:           Mac OS X 12.1 x86_64
```

Editing the Run Configuration in IntelliJ is also a good option.

### Solution

I implemented this using [A* Search](https://en.wikipedia.org/wiki/A*_search_algorithm), and
a heuristic to approximate the number of moves it will take a Knight. See my comments in
[KnightPathHeuristic.kt](./src/main/kotlin/KnightPathHeuristic.kt) for an explanation of how
the heuristic works.

I tried several heuristics against a consistent test set, and this one produced
the fewest number of node visits during A* search, by far.

![TestedHeuristics](https://user-images.githubusercontent.com/4519249/160671271-e31634b2-f782-418f-a84b-5663e2c022b2.png)

Here are some examples showing which squares the algorithm explores as it tries to find the best
path from `0` to `E`. `X` means a node which was explored but did not end up in the final
path:

```
--board_size 10,10 --source 0,0 --dest 9,9

| 0  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  ·  1  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  ·  2  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  ·  3  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  4  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  5  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  E |
```

```
--board_size 20,20 --source 1,1 --dest 19,9

| ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  0  ·  ·  ·  ·  3  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  1  ·  ·  ·  ·  X  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  2  X  4  ·  ·  X  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  X  5  ·  ·  X  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  X  ·  ·  X  6  ·  ·  X  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  X  ·  ·  X  7  ·  ·  X  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  X  ·  ·  X  8  ·  ·  X  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  X  ·  ·  X  9  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  X  ·  ·  ·  E |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  X  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
| ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  · |
```