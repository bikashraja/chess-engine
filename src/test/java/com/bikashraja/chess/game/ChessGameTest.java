package com.bikashraja.chess.game;

import com.bikashraja.chess.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessGameTest {

    @Test
    void applyMove_switchesSideToMove() {
        Board board = boardWithKings();
        board.setPiece(new Position(6, 4), new Piece(PieceType.PAWN, Color.WHITE));

        ChessGame game = new ChessGame(board, state(Color.WHITE));

        game.applyMove(Move.normal(new Position(6, 4), new Position(5, 4)));

        assertEquals(Color.BLACK, game.getGameState().getSideToMove());
    }

    @Test
    void applyMove_setsEnPassantTargetAfterPawnDoubleStep() {
        Board board = boardWithKings();
        board.setPiece(new Position(6, 4), new Piece(PieceType.PAWN, Color.WHITE));

        ChessGame game = new ChessGame(board, state(Color.WHITE));

        game.applyMove(Move.normal(new Position(6, 4), new Position(4, 4)));

        assertEquals(new Position(5, 4), game.getGameState().getEnPassantTarget());
    }

    @Test
    void applyMove_clearsEnPassantTargetAfterNormalMove() {
        Board board = boardWithKings();
        board.setPiece(new Position(6, 4), new Piece(PieceType.PAWN, Color.WHITE));

        GameState state = new GameState(
                Color.WHITE,
                false, false,
                false, false,
                new Position(2, 3)
        );

        ChessGame game = new ChessGame(board, state);

        game.applyMove(Move.normal(new Position(6, 4), new Position(5, 4)));

        assertNull(game.getGameState().getEnPassantTarget());
    }

    @Test
    void applyMove_removesBothWhiteCastlingRightsWhenWhiteKingMoves() {
        Board board = boardWithKings();

        GameState state = new GameState(
                Color.WHITE,
                true, true,
                false, false,
                null
        );

        ChessGame game = new ChessGame(board, state);

        game.applyMove(Move.normal(new Position(7, 4), new Position(6, 4)));

        assertFalse(game.getGameState().canWhiteCastleKingSide());
        assertFalse(game.getGameState().canWhiteCastleQueenSide());
    }

    @Test
    void applyMove_removesWhiteKingsideCastlingRightWhenH1RookMoves() {
        Board board = boardWithKings();
        board.setPiece(new Position(7, 7), new Piece(PieceType.ROOK, Color.WHITE));

        GameState state = new GameState(
                Color.WHITE,
                true, true,
                false, false,
                null
        );

        ChessGame game = new ChessGame(board, state);

        game.applyMove(Move.normal(new Position(7, 7), new Position(7, 6)));

        assertFalse(game.getGameState().canWhiteCastleKingSide());
        assertTrue(game.getGameState().canWhiteCastleQueenSide());
    }

    @Test
    void applyMove_removesBlackQueensideCastlingRightWhenA8RookIsCaptured() {
        Board board = boardWithKings();
        board.setPiece(new Position(0, 0), new Piece(PieceType.ROOK, Color.BLACK));
        board.setPiece(new Position(1, 0), new Piece(PieceType.ROOK, Color.WHITE));

        GameState state = new GameState(
                Color.WHITE,
                false, false,
                true, true,
                null
        );

        ChessGame game = new ChessGame(board, state);

        game.applyMove(Move.normal(new Position(1, 0), new Position(0, 0)));

        assertFalse(game.getGameState().canBlackCastleQueenSide());
        assertTrue(game.getGameState().canBlackCastleKingSide());
    }

    @Test
    void applyMove_rejectsIllegalMove() {
        Board board = boardWithKings();
        board.setPiece(new Position(6, 4), new Piece(PieceType.PAWN, Color.WHITE));

        ChessGame game = new ChessGame(board, state(Color.WHITE));

        Move illegalMove = Move.normal(new Position(6, 4), new Position(3, 4));

        assertThrows(IllegalArgumentException.class, () -> game.applyMove(illegalMove));
    }

    private Board boardWithKings() {
        Board board = Board.empty();
        board.setPiece(new Position(7, 4), new Piece(PieceType.KING, Color.WHITE));
        board.setPiece(new Position(0, 4), new Piece(PieceType.KING, Color.BLACK));
        return board;
    }

    private GameState state(Color sideToMove) {
        return new GameState(
                sideToMove,
                false, false,
                false, false,
                null
        );
    }

    @Test
    void getStatus_returnsOngoingWhenLegalMovesExist() {
        ChessGame game = new ChessGame(Board.initial(), GameState.initial());

        assertEquals(GameStatus.ONGOING, game.getStatus());
    }
}