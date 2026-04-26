# Chess Engine (CLI)

![Java](https://img.shields.io/badge/Java-21-orange)
![Build](https://img.shields.io/badge/Build-Maven-blue)
![Tests](https://img.shields.io/badge/Tests-JUnit%205-green)
![Status](https://img.shields.io/badge/Status-In%20Progress-yellow)

A command-line chess engine written in Java with a focus on clean architecture, rule-correct move generation, and upcoming AI search using Minimax with alpha-beta pruning.

---

## Project Goals

The project is a hands-on way to practice software engineering through a complex problem domain involving state transitions, move generation, testing, and search algorithms.

- Object-oriented design
- Clean architecture
- State management
- Testing with JUnit
- Algorithmic problem-solving
- Search / game AI fundamentals

---

## Current Progress

### Implemented

- [x] Modular Maven project structure
- [x] Core domain model (`Piece`, `Move`, `Board`, `GameState`, etc.)
- [x] Immutable move and piece objects
- [x] Board copying and move application
- [x] Legal move generation
- [x] Check detection
- [x] Special moves:
    - [x] Castling
    - [x] Promotion
    - [x] En Passant
- [x] Unit tests for core model and board logic
- [x] `ChessGame` orchestration class (foundation)

### In Progress

- [ ] Full game state transitions after moves
- [ ] Checkmate / stalemate detection
- [ ] CLI gameplay loop
- [ ] Position evaluation
- [ ] Minimax search
- [ ] Alpha-beta pruning

### Planned / Optional

- [ ] Move ordering
- [ ] Transposition table
- [ ] FEN parser / custom positions
- [ ] Better evaluation heuristics

### Project Structure
```
src/main/java/com/bikashraja/chess/
├── model/   # Chess data structures
├── engine/  # Move generation, evaluation, search
├── game/    # Game orchestration
├── ui/      # CLI interface
└── io/      # FEN / notation parsing
```

---

## Tech Stack

- Java 21
- Maven
- JUnit 5

---

## Build & Run

```bash
mvn clean install
```