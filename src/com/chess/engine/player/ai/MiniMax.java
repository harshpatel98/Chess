package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveTransition;

public class MiniMax implements MoveStrategy{

    private final BoardEvaluator boardEvaluator;

    public MiniMax(){
        this.boardEvaluator = null;
    }

    @Override
    public String toString(){
        return "MiniMax";
    }

    @Override
    public Move execute(Board board, int depth) {

        final long StartTime = System.currentTimeMillis();

        Move bestMove = null;

        int highSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;

        System.out.println(board.currentPlayer()+" Thinking with depth = " +depth);

        int numMoves = board.currentPlayer().getLegalMoves().size();
        for(final Move move: board.currentPlayer().getLegalMoves()){

            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){

                currentValue = board.currentPlayer().getAlliance().isWhite()?
                        min(moveTransition.getTransitionBoard(), depth-1):
                        max(moveTransition.getTransitionBoard(), depth-1);
            }
        }

        return null;
    }


    public int min(final Board board, final int depth){

        if(depth == 0) {
            return this.boardEvaluator.evaluate(board, depth);
        }
        int lowestSeenValue = Integer.MAX_VALUE;
        for(final Move move: board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                final int currentValue = max(moveTransition.getTransitionBoard(), depth-1);
                if(currentValue <= lowestSeenValue){
                    lowestSeenValue = currentValue;
                }
            }
        }
        return lowestSeenValue;
    }

    public int max(final Board board, final int depth){
        if(depth == 0) {
            return this.boardEvaluator.evaluate(board, depth);
        }
        int highSeenValue = Integer.MIN_VALUE;
        for(final Move move: board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                final int currentValue = min(moveTransition.getTransitionBoard(), depth-1);
                if(currentValue >= highSeenValue){
                    highSeenValue = currentValue;
                }
            }
        }
        return highSeenValue;
    }

}