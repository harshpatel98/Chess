package com.chess.main.player.ai;

import com.chess.main.board.Board;

public interface BoardEvaluator {

    int evaluate(Board board, int depth);

}
