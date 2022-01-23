 
/**
 * Creates a playable ChessGame object and GUI
 *
 * @author Natalie Volk
 * @author Jaleelah Ammar
 * @version June 11, 2020
 */
import javafx.application.Application; 

import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import java.io.File;

import javafx.scene.Group; 
import javafx.scene.paint.Color; 
import javafx.scene.shape.*;
import javafx.geometry.Insets; 
import javafx.geometry.HPos; 
import javafx.geometry.VPos; 
import javafx.geometry.Pos; 
import javafx.scene.Scene; 
import javafx.scene.control.Button; 
import javafx.scene.text.*; 
import javafx.scene.control.TextField; 
import javafx.stage.Stage; 
import javafx.scene.layout.*;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.animation.TranslateTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChessGameTester extends Application
{
    private boolean gameOver = false;
    private ChessGame game = new ChessGame();
    
    /**
     * implements the graphics of the chessgame
     *
     * @param  stage Stage
     */
    @Override
    public void start(Stage stage) throws Exception {

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(300, 400);

        Rectangle[][] checkerboard = new Rectangle[8][8]; // to hold coloured squares making the chessboard
        ImageView[][] chesspieceImages = new ImageView[8][8]; // to hold the chesspiece images
        int[] moveFrom = new int[2]; // to hold coordinates for moving the pieces
        int[] moveTo = new int[2];

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(createChessBoard(gridPane, checkerboard, chesspieceImages));
        
        HBox bottomMenu = new HBox(5);
        bottomMenu.setAlignment(Pos.BASELINE_CENTER);
        bottomMenu.setPadding(new Insets(15, 12, 15, 12));

        // button so that players can resign
        Button resignButton = new Button("Resign");
        bottomMenu.getChildren().add(resignButton);

        // button so that players can save
        Button saveButton = new Button("Save");
        bottomMenu.getChildren().add(saveButton);

        // button to restore game
        Button restoreButton = new Button("Restore");
        bottomMenu.getChildren().add(restoreButton);
        
        // button to "cheat" and go straight to checkmate
        Button cheatButton = new Button("Cheat!");
        bottomMenu.getChildren().add(cheatButton);

        borderPane.setBottom(bottomMenu);
        
        ImageView[] selectedPiece = new ImageView[1];
        
        // eventhandler that allows nodes to be selected for moving pieces
        EventHandler<MouseEvent> pieceSelectedEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               Glow glow = new Glow(); // to make pieces glow when selected
               glow.setLevel(0.9);
               
               // if a piece HASN'T already been selected (i.e., it is the first time a square has
               //been clicked on this turn, meaning that it's the "moveFrom" square)
               if (selectedPiece[0] == null && gameOver == false) {
                    // set ChessGame's moveFrom coordinates
                    // if the player clicks on the piece
                    if (event.getSource() instanceof ImageView ) {
                        ImageView moveFromSquare = (ImageView) event.getSource();
                        moveFrom[0] = gridPane.getRowIndex(moveFromSquare);
                        moveFrom[1] = gridPane.getColumnIndex(moveFromSquare);
                    }
                    // if the player clicks on the square
                    else {
                        Rectangle moveFromSquare = (Rectangle) event.getSource();
                        moveFrom[0] = gridPane.getRowIndex(moveFromSquare);
                        moveFrom[1] = gridPane.getColumnIndex(moveFromSquare);
                    }
                    
                    // if there is a piece on the selected square, a temp variable is set to that piece
                    if (chesspieceImages[moveFrom[1]][moveFrom[0]] != null && 
                            game.correctColour(moveFrom)) {
                        selectedPiece[0] = (ImageView) chesspieceImages[moveFrom[1]][moveFrom[0]];
                        selectedPiece[0].setEffect(glow);
                    }
                    
               }
               // if a piece HAS already been selected (i.e., it's the "moveTo" square)
               else if (gameOver == false) {
                    // set ChessGame's moveTo coordinates
                    if (event.getSource() instanceof ImageView ) {
                        ImageView moveToSquare = (ImageView) event.getSource();
                        moveTo[0] = gridPane.getRowIndex(moveToSquare);
                        moveTo[1] = gridPane.getColumnIndex(moveToSquare);
                    }
                    else {
                        Rectangle moveToSquare = (Rectangle) event.getSource();
                        moveTo[0] = gridPane.getRowIndex(moveToSquare);
                        moveTo[1] = gridPane.getColumnIndex(moveToSquare);
                    }
                    
                    selectedPiece[0].setEffect(null);
                    
                    Boolean turnResult = game.nextTurn(moveFrom, moveTo);
                    
                    // if nextTurn returns null, it means checkmate / stalemate has been achieved
                    if (turnResult == null) {
                        winner(bottomMenu, resignButton, saveButton, restoreButton, cheatButton);
                    }
                    // if the move is allowed, using ChessGame's nextTurn() method, the move is made
                    else if (turnResult) {
                        // if there is already a piece in the new location (i.e., a piece would be
                        //captured), that piece is removed
                        if (chesspieceImages[moveTo[1]][moveTo[0]] != null) {
                            gridPane.getChildren().remove(chesspieceImages[moveTo[1]][moveTo[0]]);
                        }
                        
                        // the moving piece is removed from its original spot and added to its new spot
                        gridPane.getChildren().remove(selectedPiece[0]);
                        gridPane.add(selectedPiece[0], moveTo[1], moveTo[0]);
                        chesspieceImages[moveTo[1]][moveTo[0]] = selectedPiece[0];
                        chesspieceImages[moveFrom[1]][moveFrom[0]] = null;
                        selectedPiece[0] = null;
                    }
                    // if the move wasn't allowed
                    else if (!turnResult)
                        selectedPiece[0] = null;
               }
            }
        };
        
        // event handler added to all pieces / squares to allow them to move
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                if (chesspieceImages[i][k] != null)
                    chesspieceImages[i][k].addEventFilter(MouseEvent.MOUSE_CLICKED, pieceSelectedEvent);
                checkerboard[i][k].addEventFilter(MouseEvent.MOUSE_CLICKED, pieceSelectedEvent);
            }
        }

        // if a player resigns, a winner is declared
        resignButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                winner(bottomMenu, resignButton, saveButton, restoreButton, cheatButton);
            }
        });

        // save button writes to save file
        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    Text saveMessage = new Text();
                    saveMessage.setText("Game saved.");
                    saveMessage.setFont(Font.font("Futura", FontWeight.BOLD, FontPosture.REGULAR, 25));
                    File save = new File("save.txt");
                    try
                    {
                        game.saveGame(save);
                    }
                    catch(FileNotFoundException e)
                    {
                        System.out.println("Error: " + e + "\nMake sure the file save.txt has not been moved");
                    }
                }
            });

        // restore button moves pieces back to where they were when the game was last saved
        restoreButton.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    Text restoreMessage = new Text();
                    restoreMessage.setText("Game restored.");
                    restoreMessage.setFont(Font.font("Futura", FontWeight.BOLD, FontPosture.REGULAR, 25));
                    File save = new File("save.txt");
                    try
                    {
                        game.restoreGame(save);
                        // chessboard recreated
                        borderPane.setCenter(createChessBoard(gridPane, checkerboard, chesspieceImages));
                        // event handler added to all pieces / squares to let them move
                        for (int i = 0; i < 8; i++) {
                            for (int k = 0; k < 8; k++) {
                                if (chesspieceImages[i][k] != null)
                                    chesspieceImages[i][k].addEventFilter(MouseEvent.MOUSE_CLICKED, pieceSelectedEvent);
                                checkerboard[i][k].addEventFilter(MouseEvent.MOUSE_CLICKED, pieceSelectedEvent);
                            }
                        }
                    }
                    catch(FileNotFoundException e)
                    {
                        System.out.println("Error: " + e + "\nMake sure the file save.txt has not been moved");
                    }   
                }
            });
            
        // cheat button sets up checkmate situation
        cheatButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                Text restoreMessage = new Text();
                restoreMessage.setText("Game restored.");
                restoreMessage.setFont(Font.font("Futura", FontWeight.BOLD, FontPosture.REGULAR, 25));
                File cheat = new File("cheat.txt");
                try
                {
                    game.restoreGame(cheat);
                    // chessboard recreated
                    borderPane.setCenter(createChessBoard(gridPane, checkerboard, chesspieceImages));
                    // event handler added to all pieces / squares to let them move
                    for (int i = 0; i < 8; i++) {
                        for (int k = 0; k < 8; k++) {
                            if (chesspieceImages[i][k] != null)
                                chesspieceImages[i][k].addEventFilter(MouseEvent.MOUSE_CLICKED, pieceSelectedEvent);
                            checkerboard[i][k].addEventFilter(MouseEvent.MOUSE_CLICKED, pieceSelectedEvent);
                        }
                    }
                }
                catch(FileNotFoundException e)
                {
                    System.out.println("Error: " + e + "\nMake sure the file cheat.txt has not been moved");
                }   
            }
        });

        Scene scene = new Scene(borderPane, 1000, 800);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * this is method is called when a player resigns or checkmate / stalemate is achieved,
     * it declares the winner
     *
     * @param  bottomMenu HBox -- bottom panel of chess game graphic
     * @param  resignButton Button
     * @param  saveButton Button
     * @param  restoreButton Button
     */
    private void winner(HBox bottomMenu, Button resignButton, Button saveButton, Button restoreButton, Button cheatButton) {
        // text is added declaring the result of the game
        Text winner = new Text();
        if (game.stalemate() == true)
            winner.setText("Stalemate! No one wins.");
        else if (game.winner() == true) 
            winner.setText("White won!");
        else 
            winner.setText("Black won!");
        winner.setFont(Font.font("Futura",FontWeight.BOLD, FontPosture.REGULAR, 25));
        bottomMenu.getChildren().remove(resignButton);
        bottomMenu.getChildren().remove(saveButton);
        bottomMenu.getChildren().remove(restoreButton);
        bottomMenu.getChildren().remove(cheatButton);
        bottomMenu.getChildren().add(winner);
        // game over is set to true, preventing more moves from taking place
        gameOver = true;
    }
    
    /**
     * makes the chessboard grid and adds chesspiece images
     *
     * @param  gridPane  GridPane
     * @param  checkerboard  Rectangle[][] -- array forming checkerboard with coloured rectangles
     * @param   chesspieceImages ImageView[][] -- array holding chesspiece images
     * @return    GridPane containing chesspiece images and coloured squares
     */
    private GridPane createChessBoard(GridPane gridPane, Rectangle[][] checkerboard, 
    ImageView[][] chesspieceImages) throws FileNotFoundException {
        
        ChessPiece[][] chessBoard = game.getChessBoard();
        
        boolean alternate = true;

        // creates the chessboard checkers, using B&W rectangles
        for (int i = 0; i < 8; i++) {
            alternate = !alternate;
            for (int k = 0; k < 8; k++) {

                checkerboard[i][k] = new Rectangle();
                checkerboard[i][k].heightProperty().bind(gridPane.heightProperty().divide(8));
                checkerboard[i][k].widthProperty().bind(gridPane.widthProperty().divide(8));

                // makes checkered chessboard
                if (alternate) {
                    if (k % 2 == 0)
                        checkerboard[i][k].setFill(Color.DARKSLATEGRAY);
                    else
                        checkerboard[i][k].setFill(Color.WHITE);
                }
                else {
                    if (k % 2 == 1)
                        checkerboard[i][k].setFill(Color.DARKSLATEGRAY);
                    else
                        checkerboard[i][k].setFill(Color.WHITE);
                }

                gridPane.add(checkerboard[i][k], i, k);

                // adds the appropriate chesspiece to their starting positions
                if (chessBoard[7-k][i] instanceof Rook) {
                    if (!chessBoard[7-k][i].getIsWhite()) {
                        Image blackRook = new Image(new FileInputStream("BlackRook.jpg"));
                        chesspieceImages[i][k] = new ImageView(blackRook);
                        chesspieceImages[i][k].setPreserveRatio(true);
                        chesspieceImages[i][k].fitHeightProperty().bind(checkerboard[i][k].heightProperty().subtract(15));
                        gridPane.add(chesspieceImages[i][k], i, k);
                        gridPane.setHalignment(chesspieceImages[i][k], HPos.CENTER);
                    }
                    else {
                        Image whiteRook = new Image(new FileInputStream("WhiteRook.jpg"));
                        chesspieceImages[i][k] = new ImageView(whiteRook);
                        chesspieceImages[i][k].setPreserveRatio(true);
                        chesspieceImages[i][k].fitHeightProperty().bind(checkerboard[i][k].heightProperty().subtract(15));
                        gridPane.add(chesspieceImages[i][k], i, k);
                        gridPane.setHalignment(chesspieceImages[i][k], HPos.CENTER);
                    }
                }
                else if (chessBoard[7-k][i] instanceof Knight) { 
                    if (!chessBoard[7-k][i].getIsWhite()) {
                        Image blackKnight = new Image(new FileInputStream("BlackKnight.png"));
                        chesspieceImages[i][k] = new ImageView(blackKnight);
                        chesspieceImages[i][k].setPreserveRatio(true);
                        chesspieceImages[i][k].fitHeightProperty().bind(checkerboard[i][k].heightProperty().subtract(15));
                        gridPane.add(chesspieceImages[i][k], i, k);
                        gridPane.setHalignment(chesspieceImages[i][k], HPos.CENTER);
                    }
                    else {
                        Image whiteKnight = new Image(new FileInputStream("WhiteKnight.png"));
                        chesspieceImages[i][k] = new ImageView(whiteKnight);
                        chesspieceImages[i][k].setPreserveRatio(true);
                        chesspieceImages[i][k].fitHeightProperty().bind(checkerboard[i][k].heightProperty().subtract(15));
                        gridPane.add(chesspieceImages[i][k], i, k);
                        gridPane.setHalignment(chesspieceImages[i][k], HPos.CENTER);
                    }
                }
                else if (chessBoard[7-k][i] instanceof Bishop) {
                    if (!chessBoard[7-k][i].getIsWhite()) {
                        Image blackBishop = new Image(new FileInputStream("BlackBishop.png"));
                        chesspieceImages[i][k] = new ImageView(blackBishop);
                        chesspieceImages[i][k].setPreserveRatio(true);
                        chesspieceImages[i][k].fitHeightProperty().bind(checkerboard[i][k].heightProperty().subtract(15));
                        gridPane.add(chesspieceImages[i][k], i, k);
                        gridPane.setHalignment(chesspieceImages[i][k], HPos.CENTER);
                    }
                    else {
                        Image whiteBishop = new Image(new FileInputStream("WhiteBishop.png"));
                        chesspieceImages[i][k] = new ImageView(whiteBishop);
                        chesspieceImages[i][k].setPreserveRatio(true);
                        chesspieceImages[i][k].fitHeightProperty().bind(checkerboard[i][k].heightProperty().subtract(15));
                        gridPane.add(chesspieceImages[i][k], i, k);
                        gridPane.setHalignment(chesspieceImages[i][k], HPos.CENTER);
                    }
                }
                else if (chessBoard[7-k][i] instanceof Queen) {
                    if (!chessBoard[7-k][i].getIsWhite()) {
                        Image blackQueen = new Image(new FileInputStream("BlackQueen.png"));
                        chesspieceImages[i][k] = new ImageView(blackQueen);
                        chesspieceImages[i][k].setPreserveRatio(true);
                        chesspieceImages[i][k].fitHeightProperty().bind(checkerboard[i][k].heightProperty().subtract(15));
                        gridPane.add(chesspieceImages[i][k], i, k);
                        gridPane.setHalignment(chesspieceImages[i][k], HPos.CENTER);
                    }
                    else {
                        Image whiteQueen = new Image(new FileInputStream("WhiteQueen.png"));
                        chesspieceImages[i][k] = new ImageView(whiteQueen);
                        chesspieceImages[i][k].setPreserveRatio(true);
                        chesspieceImages[i][k].fitHeightProperty().bind(checkerboard[i][k].heightProperty().subtract(15));
                        gridPane.add(chesspieceImages[i][k], i, k);
                        gridPane.setHalignment(chesspieceImages[i][k], HPos.CENTER);
                    }
                }
                else if (chessBoard[7-k][i] instanceof King) {
                    if (!chessBoard[7-k][i].getIsWhite()) {
                        Image blackKing = new Image(new FileInputStream("BlackKing.png"));
                        chesspieceImages[i][k] = new ImageView(blackKing);
                        chesspieceImages[i][k].setPreserveRatio(true);
                        chesspieceImages[i][k].fitHeightProperty().bind(checkerboard[i][k].heightProperty().subtract(15));
                        gridPane.add(chesspieceImages[i][k], i, k);
                        gridPane.setHalignment(chesspieceImages[i][k], HPos.CENTER);
                    }
                    else {
                        Image whiteKing = new Image(new FileInputStream("WhiteKing.png"));
                        chesspieceImages[i][k] = new ImageView(whiteKing);
                        chesspieceImages[i][k].setPreserveRatio(true);
                        chesspieceImages[i][k].fitHeightProperty().bind(checkerboard[i][k].heightProperty().subtract(15));
                        gridPane.add(chesspieceImages[i][k], i, k);
                        gridPane.setHalignment(chesspieceImages[i][k], HPos.CENTER);
                    }
                }

                else if (chessBoard[7-k][i] instanceof Pawn) {
                    if (!chessBoard[7-k][i].getIsWhite()) {
                        Image blackPawn = new Image(new FileInputStream("BlackPawn.png"));
                        chesspieceImages[i][k] = new ImageView(blackPawn);
                        chesspieceImages[i][k].setPreserveRatio(true);
                        chesspieceImages[i][k].fitHeightProperty().bind(checkerboard[i][k].heightProperty().subtract(25));
                        gridPane.add(chesspieceImages[i][k], i, k);
                        gridPane.setHalignment(chesspieceImages[i][k], HPos.CENTER);
                    }
                    else {
                        Image whitePawn = new Image(new FileInputStream("WhitePawn.png"));
                        chesspieceImages[i][k] = new ImageView(whitePawn);
                        chesspieceImages[i][k].setPreserveRatio(true);
                        chesspieceImages[i][k].fitHeightProperty().bind(checkerboard[i][k].heightProperty().subtract(25));
                        gridPane.add(chesspieceImages[i][k], i, k);
                        gridPane.setHalignment(chesspieceImages[i][k], HPos.CENTER);
                    }
                }
            }
        }
        return gridPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
