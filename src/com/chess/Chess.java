package com.chess;

import com.chess.engine.classic.board.Board;
import com.chess.gui.Table;


/**
 * Author Harsh Patel (5999784)
 * Date: December 18, 2020
 * Chess Project
 */

public class Chess {

    public static void main(String[] args) {

        Board board = Board.createStandardBoard();

        System.out.println(board);

        Table.get().show();
    }
}
