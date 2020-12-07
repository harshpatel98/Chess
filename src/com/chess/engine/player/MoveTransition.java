package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class MoveTransition {

    private final Board transitionBoard;
    private final Move move;
    private final Board toBoard;

    private final MoveStatus moveStatus;

    public MoveTransition(final Board transitionBoard, final Move move, final MoveStatus moveStatus, final Board toBoard){
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
        this.toBoard = toBoard;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
    public Board getTransitionBoard(){
        return this.transitionBoard;
    }
    public Board getToBoard() {
        return this.toBoard;
    }



}
