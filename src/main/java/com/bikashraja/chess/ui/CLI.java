package com.bikashraja.chess.ui;

import com.bikashraja.chess.game.ChessGame;
import com.bikashraja.chess.game.GameStatus;
import com.bikashraja.chess.model.Board;
import com.bikashraja.chess.model.GameState;

import java.util.Scanner;

public class CLI {

    private final BoardRenderer boardRenderer;
    private final InputParser inputParser;

    public CLI() {
        this.boardRenderer = new BoardRenderer();
        this.inputParser = new InputParser();
    }

    public void start() {
        ChessGame game = new ChessGame(Board.initial(), GameState.initial());

        Scanner scanner = new Scanner(System.in);

        while (game.getStatus() == GameStatus.ONGOING) {
            boardRenderer.render(game.getBoard());

            System.out.print(game.getGameState().getSideToMove() + " to move: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("quit")) {
                break;
            }

            try {
                game.applyMove(inputParser.parseMove(input));
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal move: " + input);
            } catch (UnsupportedOperationException e) {
                System.out.println("Input parsing is not implemented yet.");
                break;
            }
        }

        System.out.println("Game over: " + game.getStatus());
    }
}
