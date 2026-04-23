package com.bikashraja.chess.game;

import com.bikashraja.chess.engine.MoveGenerator;
import com.bikashraja.chess.model.Board;
import com.bikashraja.chess.model.GameState;

import java.util.Objects;

public class ChessGame {

    private Board board;
    private GameState gameState;
    private final MoveGenerator moveGenerator;

    public ChessGame(Board board, GameState gameState) {
        this(board, gameState, new MoveGenerator());
    }

    public ChessGame(Board board, GameState gameState, MoveGenerator moveGenerator) {
        this.board = Objects.requireNonNull(board, "board must not be null");
        this.gameState = Objects.requireNonNull(gameState, "gameState must not be null");
        this.moveGenerator = Objects.requireNonNull(moveGenerator, "moveGenerator must not be null");
    }

    public Board getBoard() {
        return board;
    }

    public GameState getGameState() {
        return gameState;
    }
}