public abstract class Tile {


    int tileCoordinate;

    Tile(int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile{
        EmptyTile(int coordinate){
            super(coordinate);
        }

        @Override
        public boolean isTileOccupied(){
            //Tile is empty
            return false;
        }

        @Override
        public Piece getPiece(){
            //there is no piece on empty tile
            return null;
        }
    }
}
