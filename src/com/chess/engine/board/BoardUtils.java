package com.chess.engine.board;

import com.google.common.collect.ImmutableMap;

import java.util.*;
import java.util.Map;

public class BoardUtils {


    // rows and columns used for moves that arent possible, like going off the board
    //row/column represents tile number from 0 -64
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1) ;
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);
    //board row layout, number represents tile number. reference through wikipedia, link included in references
    /*
    row 8: 0  . . . . . .  7
    row 7: 8  . . . . . . 15
    row 6: 16 . . . . . . 23
    row 5: 24 . . . . . . 31
    row 4: 32 . . . . . . 39
    row 3: 40 . . . . . . 47
    row 2: 48 . . . . . . 55
    row 1: 56 . . . . . . 63
     */
    public static final boolean[] EIGHTH_ROW = initRow(0);
    public static final boolean[] SEVENTH_ROW = initRow(8);
    public static final boolean[] SIXTH_ROW = initRow(16);
    public static final boolean[] FIFTH_ROW = initRow(24);
    public static final boolean[] FOURTH_ROW = initRow(32);
    public static final boolean[] THIRD_ROW = initRow(40);
    public static final boolean[] SECOND_ROW = initRow(48);
    public static final boolean[] FIRST_ROW = initRow(56);
    //public static final int START_TILE_INDEX = 0;


    //number of tiles and tiles per row
    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private BoardUtils(){
        throw new RuntimeException("You cannot instantiate me!");
    }

    //check if its a valid tile for the piece to make a move, get the column
    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[NUM_TILES];
        do{
            column[columnNumber] = true;
            columnNumber +=NUM_TILES_PER_ROW;
        }while (columnNumber < NUM_TILES);
        return column;
    }
    //check if its a valid tile for the piece to make a move, get the row
    private static boolean[] initRow(int rowNumber){
        final boolean[] row = new boolean[NUM_TILES];
        do{
            row[rowNumber] = true;
            rowNumber++;

        }while (rowNumber % NUM_TILES_PER_ROW != 0);
        return row;
    }

    //check for correct tile coordinate for moe to be executed
    public static boolean isValidTileCoordinate(final int coordinate){
        return coordinate >=0 && coordinate <NUM_TILES;

    }


    // check if the king(s) is/are in check
    public static boolean isThreatenedBoardImmediate(final Board board) {
        return board.whitePlayer().isInCheck() || board.blackPlayer().isInCheck();
    }

    // check if the king(s) is/are in checkmate or stale
    public static boolean isEndGame(final Board board) {
        return board.currentPlayer().isInCheckMate() ||
                board.currentPlayer().isInStaleCheck();
    }




}
