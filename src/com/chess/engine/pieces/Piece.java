package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract  class Piece {

    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;
    private final int cashedHashCode;

    Piece(final PieceType pieceType, final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove ){
        this.pieceType = pieceType;
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        this.isFirstMove = isFirstMove;
        this.cashedHashCode = computeHashCode();

    }

    private int computeHashCode(){
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1: 0);
        return result;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public Alliance getPieceAllegiance() {
        return this.pieceAlliance;
    }
    @Override
    public boolean equals(final Object other){
        if( this == other){
            return true;
        }

        if(!(other instanceof  Piece)){
            return false;
        }
        final  Piece otherPiece = (Piece) other;

        return piecePosition == otherPiece.getPiecePosition()&& pieceType == otherPiece.getPieceType() &&
                pieceAlliance == otherPiece.getPieceAlliance() && isFirstMove == otherPiece.isFirstMove();

    }

    @Override
    public int hashCode(){
        return this.cashedHashCode;
    }

    public int getPiecePosition(){
        return this.piecePosition;
    }

    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public abstract Piece movePiece(Move move);

    //return a list of legal moves
    public abstract Collection<Move> calculateLegalMoves (final Board board);

    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }


    public enum PieceType{

        BISHOP(300,"B") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },

        KING(10000,"K") {
            @Override
            public boolean isKing() {

                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },

        KNIGHT(300,"N") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        PAWN(100,"P") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        QUEEN(900,"Q") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK(500,"R") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        };

        private int pieceValue;
        private int piece;
        private String pieceName;
        PieceType(final int pieceValue, final String pieceName){
            this.pieceName = pieceName;
            this.pieceValue = pieceValue;
        }

        @Override
        public String toString(){

            return this.pieceName;
        }

        public int getPieceValue(){
            return this.pieceValue;
        }
        public abstract boolean isKing();

        public abstract boolean isRook();

    }

}