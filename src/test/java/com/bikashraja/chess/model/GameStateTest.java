package com.bikashraja.chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @Test
    void testInitial_returnsCorrectDefaultState() {
        GameState state = GameState.initial();

        assertEquals(Color.WHITE, state.getSideToMove());
        assertTrue(state.canWhiteCastleKingSide());
        assertTrue(state.canWhiteCastleQueenSide());
        assertTrue(state.canBlackCastleKingSide());
        assertTrue(state.canBlackCastleQueenSide());
        assertNull(state.getEnPassantTarget());
    }

    @Test
    void testSwitchSide_returnsStateWithOppositeSideToMove() {
        GameState state = GameState.initial();

        GameState switched = state.switchSide();

        assertEquals(Color.BLACK, switched.getSideToMove());
        assertTrue(switched.canWhiteCastleKingSide());
        assertTrue(switched.canWhiteCastleQueenSide());
        assertTrue(switched.canBlackCastleKingSide());
        assertTrue(switched.canBlackCastleQueenSide());
        assertNull(switched.getEnPassantTarget());
    }

    @Test
    void testWithEnPassantTarget_returnsStateWithUpdatedTarget() {
        GameState state = GameState.initial();
        Position target = new Position(2, 3);

        GameState updated = state.withEnPassantTarget(target);

        assertEquals(target, updated.getEnPassantTarget());
        assertEquals(state.getSideToMove(), updated.getSideToMove());
    }

    @Test
    void testWithCastlingRights_returnsStateWithUpdatedRights() {
        GameState state = GameState.initial();

        GameState updated = state.withCastlingRights(false, true, false, true);

        assertFalse(updated.canWhiteCastleKingSide());
        assertTrue(updated.canWhiteCastleQueenSide());
        assertFalse(updated.canBlackCastleKingSide());
        assertTrue(updated.canBlackCastleQueenSide());
    }

    @Test
    void testCanCastleKingSide_returnsCorrectValueForColor() {
        GameState state = new GameState(Color.WHITE, true, false, false, true, null);

        assertTrue(state.canCastleKingSide(Color.WHITE));
        assertFalse(state.canCastleKingSide(Color.BLACK));
    }

    @Test
    void testCanCastleQueenSide_returnsCorrectValueForColor() {
        GameState state = new GameState(Color.WHITE, true, false, false, true, null);

        assertFalse(state.canCastleQueenSide(Color.WHITE));
        assertTrue(state.canCastleQueenSide(Color.BLACK));
    }

    @Test
    void testEqualsAndHashCode_sameValues_shouldBeEqual() {
        GameState s1 = new GameState(Color.WHITE, true, true, true, true, null);
        GameState s2 = new GameState(Color.WHITE, true, true, true, true, null);

        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    void testEquals_differentValues_shouldNotBeEqual() {
        GameState s1 = new GameState(Color.WHITE, true, true, true, true, null);
        GameState s2 = new GameState(Color.BLACK, true, true, true, true, null);

        assertNotEquals(s1, s2);
    }

    @Test
    void testConstructor_nullSideToMove_throwsException() {
        assertThrows(NullPointerException.class, () ->
                new GameState(null, true, true, true, true, null)
        );
    }
}