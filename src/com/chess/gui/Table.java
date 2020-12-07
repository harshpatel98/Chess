package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private Board chessBoard;
    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;


    private final static Dimension OUTER_FRAME_DIMENSIONS = new Dimension(600,600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);

    private static String defaultPieceImagesPath = "art/pieces/plain/";
    private final Color lightTileColor = Color.decode("#FFFFFF");
    private final Color darkTileColor = Color.decode("#000000");


    public Table() {
        this.gameFrame = new JFrame("CHESS");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar( );
        this.gameFrame.setJMenuBar(tableMenuBar );
        this.gameFrame.setSize(OUTER_FRAME_DIMENSIONS);
        this.chessBoard = Board.createStandardBoard();

        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    //create File menu
    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        //menu item openPNG
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Open PGN File");
            }
        });
        fileMenu.add(openPGN);

        //menu item to Quit Game
        final JMenuItem quitMenuItem = new JMenuItem("Quit");
        quitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(quitMenuItem);

        return fileMenu;
    }

    private class BoardPanel extends JPanel{
         final List<TilePanel> boardTiles;

        BoardPanel(){
            //8x8 grid
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();

            //create tiles in 8x8 grid
            for(int i =0; i< BoardUtils.NUM_TILES; i++){
                final TilePanel tilePanel= new TilePanel(this,i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();

        }
        public void drawBoard( final Board board){
            removeAll();
            for (final TilePanel tilePanel : boardTiles){
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }

    }

    private class TilePanel extends JPanel{

        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId){
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColour();
            assignTilePieceIcon(chessBoard);

            //if right click make moves, if left clicked cancel selection
            addMouseListener(new MouseListener() {
                @Override
                //click piece the pick it up and if a legal tile is selected the piece will be moved there
                public void mouseClicked(final MouseEvent e) {
                    //right piece unselects the piece, cancels move
                    if(isRightMouseButton(e)) {
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                    } else if (isLeftMouseButton(e)) {
                        if (sourceTile == null) {
                            sourceTile = chessBoard.getTile(tileId);
                            humanMovedPiece = sourceTile.getPiece();
                            if (humanMovedPiece == null) {
                                sourceTile = null;
                            }
                        }else {
                            //destination tile thats selected, the piece is moved there
                            destinationTile = chessBoard.getTile(tileId);
                            final Move move = Move.MoveFactory.createMove(chessBoard,
                                    sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);

                            if(transition.getMoveStatus().isDone()){
                                chessBoard = transition.getTransitionBoard();
                               // moveLog.addMove(move);
                            }
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                /*takenPiecesPanel.redo(moveLog);
                                if(gameSetup.isAIPlayer(chessBoard.currentPlayer())){
                                    Table.get().moveMadeUpdate(PlayerType.HUMAN);
                                }*/
                                boardPanel.drawBoard(chessBoard);
                            }
                        });
                    }
                }


                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });
            validate();
        }

        //drawTile,
        public void drawTile(final Board board){
            assignTileColour();
            assignTilePieceIcon(board);
            validate();
            repaint();
        }
        private void assignTilePieceIcon(final Board board){
            this.removeAll();
            if(board.getTile(this.tileId).isTileOccupied()){
                try {
                    final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath +
                            board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0,1)+
                            board.getTile(this.tileId).getPiece().toString() + ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        //assign the tile colours
        private void assignTileColour() {
            if (BoardUtils.EIGHTH_ROW[this.tileId] ||
                    BoardUtils.SIXTH_ROW[this.tileId] ||
                    BoardUtils.FOURTH_ROW[this.tileId] ||
                    BoardUtils.SECOND_ROW[this.tileId]) {
                //if you're on the 2,4,6,8 row add make tile colour light otherwise make it dark, this is to create a checkered pattern
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if (BoardUtils.SEVENTH_ROW[this.tileId] ||
                    BoardUtils.FIFTH_ROW[this.tileId] ||
                    BoardUtils.THIRD_ROW[this.tileId] ||
                    BoardUtils.FIRST_ROW[this.tileId]) {
                //if you're on the 1,3,5,7 row add make tile colour light otherwise make it dark, this is to create a checkered pattern
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }
    }
}
