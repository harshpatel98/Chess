package com.chess.main.player.ai;

import com.chess.main.board.Board;
import com.chess.main.board.Move;

public interface MoveStrategy {

    Move execute(Board board);

}
