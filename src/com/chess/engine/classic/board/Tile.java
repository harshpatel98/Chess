package com.chess.engine.classic.board;

import com.chess.engine.classic.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILE_CACHE = createAllPossibleEmptyTiles();


    //easily grab chess tiles. i.e emptyTiles get 0 will give me the first tile
    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles(){

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for(int i=0; i< BoardUtils.NUM_TILES; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile (final int tileCoordinate, final Piece piece){
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILE_CACHE.get(tileCoordinate);
    }

    //allow to create an individual tile,
    private Tile ( final int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    //check if a given tile is occupied or not
    public abstract boolean isTileOccupied();

    public int getTileCoordinate(){
        return this.tileCoordinate;
    }

    //this gets the piece of a given tile
    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile{

        private  EmptyTile( int coordinate){ super(coordinate); }

        @Override
        public String toString(){ return"-"; }

        @Override
        public boolean isTileOccupied(){ return false; }

        @Override
        public Piece getPiece(){ return null; }
    }

    public static final class OccupiedTile extends Tile{

        private final Piece pieceOnTile;

        private OccupiedTile(int tileCord, final Piece pieceOnTile){
            super(tileCord);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public String toString(){
            return getPiece().getPieceAllegiance().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
        }

        @Override
        public boolean isTileOccupied(){ return true; }

        @Override
        public Piece getPiece(){ return this.pieceOnTile; }
    }
}
