
/**
 * Implements a chess game
 *
 * @author Natalie Volk
 * @author Jaleelah Ammar
 * @version June 11, 2020
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class ChessGame implements Game
{
    private ChessPiece[][] chessBoard;
    private boolean lastTurn = false;

    /**
     * Constructor for objects of class ChessGame
     */
    public ChessGame() {
        chessBoard = new ChessPiece[8][8];
        newGame();
    }

    /**
     * Implements Game's abstract method
     * Initializes game pieces in the correct spot on the game board
     */
    public void newGame() {

        //initializing white pawns
        chessBoard[1][0] = new Pawn(2,'a',true);
        chessBoard[1][1] = new Pawn(2,'b',true);
        chessBoard[1][2] = new Pawn(2,'c',true);
        chessBoard[1][3] = new Pawn(2,'d',true);
        chessBoard[1][4] = new Pawn(2,'e',true);
        chessBoard[1][5] = new Pawn(2,'f',true);
        chessBoard[1][6] = new Pawn(2,'g',true);
        chessBoard[1][7] = new Pawn(2,'h',true);

        //initializing black pawns 
        chessBoard[6][0] = new Pawn(7,'a',false);
        chessBoard[6][1] = new Pawn(7,'b',false);
        chessBoard[6][2] = new Pawn(7,'c',false);
        chessBoard[6][3] = new Pawn(7,'d',false);
        chessBoard[6][4] = new Pawn(7,'e',false);
        chessBoard[6][5] = new Pawn(7,'f',false);
        chessBoard[6][6] = new Pawn(7,'g',false);
        chessBoard[6][7] = new Pawn(7,'h',false);

        //initializing white rooks
        chessBoard[0][0] = new Rook(1,'a',true);
        chessBoard[0][7] = new Rook(1,'h',true);

        //initializing black rooks
        chessBoard[7][0] = new Rook(8,'a',false);
        chessBoard[7][7] = new Rook(8,'h',false);

        //initializing white knights
        chessBoard[0][1] = new Knight(1,'b', true);
        chessBoard[0][6] = new Knight(1,'g', true);

        //initializing black knights
        chessBoard[7][1] = new Knight(8,'b', false);
        chessBoard[7][6] = new Knight(8,'g', false);

        //initializing white bishops
        chessBoard[0][2] = new Bishop(1,'c',true);
        chessBoard[0][5] = new Bishop(1,'f',true);

        //initializing black bishops
        chessBoard[7][2] = new Bishop(8,'c',false);
        chessBoard[7][5] = new Bishop(8,'f',false);

        //initializing white queen
        chessBoard[0][3] = new Queen(1,'d', true);

        //initializing black queen
        chessBoard[7][3] = new Queen(8,'d', false);

        //initializing white king
        chessBoard[0][4] = new King (1,'e',true);

        //initializing black king
        chessBoard[7][4] = new King (8,'e',false);
    }
    
    /**
     * returns the chesspiece array
     *
     * @return    array containing locations of the chesspieces
     */
    public ChessPiece[][] getChessBoard() {
        return chessBoard;
    }
    
    /**
     * saves a game to a file
     *
     * @param  save  File -- file to write it to
     */
    public void saveGame(File save) throws FileNotFoundException
    {
        PrintWriter pr = new PrintWriter(save);
        pr.close(); // clears the save file before writing to it
        PrintWriter writer = new PrintWriter(save);
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                ChessPiece temp = chessBoard[i][j];
                // if there's no piece on the square, we indicate that
                if(temp == null)
                    writer.print("XX");
                else
                {
                    // first we save the colour of the piece
                    if(temp.getIsWhite())
                        writer.print("W");
                    else
                        writer.print("B");
                    // now we save the type of piece
                    if(temp instanceof Pawn)
                        writer.print("P");
                    else if(temp instanceof Rook)
                        writer.print("R");
                    else if(temp instanceof Knight)
                        writer.print("H"); // save K for king plus they should be called horses anyways. they look like they're just horses
                    else if(temp instanceof Bishop)
                        writer.print("B");
                    else if(temp instanceof King)
                        writer.print("K");
                    else if(temp instanceof Queen)
                        writer.print("Q");
                }
                writer.println();
            }
        }
        writer.close();
    }
    
    /**
     * restores a saved game
     *
     * @param  save File -- the file of the saved game
     */
    public void restoreGame(File save) throws FileNotFoundException
    {
        Scanner sc = new Scanner(save);
        lastTurn = false;
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                String temp = sc.nextLine();
                char colour = temp.charAt(0);
                char piece = temp.charAt(1);
                if(colour == 'X')
                    chessBoard[i][j] = null;
                else
                {
                    boolean white = true;
                    int row = i+1;
                    char column = (char) (j + 97);
                    if(colour == 'B')
                        white = false;
                    if(piece == 'P')
                        chessBoard[i][j] = new Pawn(row, column, white);
                    else if(piece == 'R')
                        chessBoard[i][j] = new Rook(row, column, white);
                    else if(piece == 'H')
                        chessBoard[i][j] = new Knight(row, column, white);
                    else if(piece == 'B')
                        chessBoard[i][j] = new Bishop(row, column, white);
                    else if(piece == 'K')
                        chessBoard[i][j] = new King(row, column, white);
                    else
                        chessBoard[i][j] = new Queen(row, column, white);
                }
            }
        }
    }

    private boolean resign = false;
    /**
     * 
     * Allows a player to make a move on their turn
     *
     * @return    true if the turn was successfully carried out; false if the turn was unsuccessful
     */
    public Boolean nextTurn(int[] from, int[] to) {

        // prints whose turn it is
        if (lastTurn ==false) {
            System.out.println("\n\nIt is white's turn.");
        }
        else {
            System.out.println("\n\nIt is black's turn.");
        }

        // instantiated for the sake of each piece's isLegalMove method
        char[] moveFromC = new char[2];
        char[] moveToC = new char[2];
        int[] moveFrom = {7 - from[0], from[1]};
        int[] moveTo = {7 - to[0], to[1]};
        moveFromC[1] = (char) (moveFrom[0] + 49);
        moveFromC[0] = (char) (moveFrom[1] + 97);
        moveToC[1] = (char) (moveTo[0] + 49);
        moveToC[0] = (char) (moveTo[1] + 97);

        // returns false if move is not legal or puts yourself in check
        if(!isLegal(from, to))
            return false;
        /**
        else if(inefficientCheckChecker(moveTo))
        {
            System.out.println("\n\nYou cannot put yourself in check.");
            return false;
        }
        **/

        // if the move is valid, the piece is moved to the new location and turn is completed
        ChessPiece temp = chessBoard[moveFrom[0]][moveFrom[1]];
        temp.move(moveTo[0] + 1, moveToC[0]);
        chessBoard[moveTo[0]][moveTo[1]] = temp;
        chessBoard[moveFrom[0]][moveFrom[1]] = null;
        
        // returns null if the move puts you in checkmate / stalemate, ending the game
        if (easyCheckmate()) {
            System.out.println("\n\nCheckmate. Game over.");
            return null;
        }
        /**
        else if(checkChecker(temp) && lastTurn)
        {
            if(!checkmate())
                System.out.println("\n\nWhite is in check.");
            else {
                System.out.println("\n\nCheckmate! Black wins!");
                return null;
            }
        }
        else if(checkChecker(temp) && !lastTurn)
        {
            if(!checkmate())
                System.out.println("\n\nBlack is now in check.");
            else{
                System.out.println("\n\nCheckmate! White wins!");
                return null;
            }
        }
        **/
        else if(stalemate())
        {
            System.out.println("\n\nStalemate. No one wins.");
            return null;
        }

        lastTurn = !lastTurn;

        return true;
    }

    /**
     * checks if a move is legal
     *
     * @param  from  int[] -- javafx coordinates of piece that's being moved
     * @param  from  int[][] -- javafx coordinates of where the piece is being moved to
     * @return    Boolean of whether the move is legal
     */
    public Boolean isLegal(int[] from, int[] to)
    {
        // coordinates are converted into characters and ints,
        //to use for individual piece's isLegalMove method
        char[] moveFromC = new char[2];
        char[] moveToC = new char[2];
        int[] moveFrom = {7 - from[0], from[1]};
        int[] moveTo = {7 - to[0], to[1]};
        moveFromC[1] = (char) (moveFrom[0] + 49);
        moveFromC[0] = (char) (moveFrom[1] + 97);
        moveToC[1] = (char) (moveTo[0] + 49);
        moveToC[0] = (char) (moveTo[1] + 97);

        // if the initial coordinates are not on the board, the turn cannot be completed
        if (moveFromC[0] > 'h' || (moveFromC[1]-48) > 8) {
            //System.out.println("MoveFrom coordinates are not on board");
            return false;
        }

        // checks if there is a piece of the right colour at the starting place inputted
        if (chessBoard[moveFrom[0]][moveFrom[1]] == null || 
        (chessBoard[moveFrom[0]][moveFrom[1]].getIsWhite() == lastTurn))
            return false;
        // allows pawn diagonal moves to capture another piece
        else if (chessBoard[moveFrom[0]][moveFrom[1]] instanceof Pawn && 
        Math.abs(moveTo[0] - moveFrom[0]) == 1 && Math.abs(moveTo[1] - moveFrom[1]) == 1
        && chessBoard[moveTo[0]][moveTo[1]] != null
        && chessBoard[moveTo[0]][moveTo[1]].getIsWhite() == lastTurn) {

        }
        // checks to the move is legal, according to each piece's isLegalMove() method
        else if (!chessBoard[moveFrom[0]][moveFrom[1]].isLegalMove(moveTo[0] + 1, moveToC[0])) {
            System.out.println("That move is not legal for that piece");
            return false;
        }
        else if (chessBoard[moveFrom[0]][moveFrom[1]] instanceof Pawn && 
        chessBoard[moveTo[0]][moveTo[1]] != null) {
            System.out.println("Pawns cannot capture in a straight line");
            return false;
        }
        // checks if there is a piece of their own colour on the inputted ending location
        else if(chessBoard[moveTo[0]][moveTo[1]] != null && 
        chessBoard[moveTo[0]][moveTo[1]].getIsWhite() != lastTurn) {
            System.out.println("You cannot capture your own piece");
            return false;
        }
        // uses each piece's route() method and checks if there are pieces blocking that move
        for (Integer[] coord : chessBoard[moveFrom[0]][moveFrom[1]].route(moveTo[0] + 1, moveToC[0])) {
            if (chessBoard[coord[0]][coord[1]] != null) {
                System.out.println("There are pieces blocking that move");
                return false;
            }
        }

        return true;
    }

    /**
     * Determines whether the king has been captured
     * 
     * @return      true if the king has been captured, false if it has not
     */
    public Boolean easyCheckmate()
    {
        boolean whiteFound = false;
        boolean blackFound = false;
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(chessBoard[i][j] instanceof King && chessBoard[i][j].getIsWhite())
                    whiteFound = true;
                else if(chessBoard[i][j] instanceof King && !chessBoard[i][j].getIsWhite())
                    blackFound = true;
                if(whiteFound && blackFound)
                    return false;
            }
        }
        lastTurn = !lastTurn;
        return true;
    }
    
    /**
     * checks if the side to move is in check
     *
     * @param  temp  ChessPiece
     * @return    Boolean -- true if side is in check, false if not
     */
    public Boolean checkChecker(ChessPiece temp)
    // checks if the side to move is in check
    {
        // first we check if the most recently moved piece threatens the king
        int[] from = {temp.getRow(), temp.getCol()}; 
        int[] to = new int[2];
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(chessBoard[i][j] instanceof King && chessBoard[i][j].getIsWhite() && lastTurn)
                {
                    to[0] = i; to[1] = j;
                    if(isLegal(from, to))
                        return true;
                    else
                        break;
                }
                else if(chessBoard[i][j] instanceof King && !chessBoard[i][j].getIsWhite() && !lastTurn)
                {
                    to[0] = i; to[1] = j;
                    if(isLegal(from, to))
                        return true;
                    else
                        break;
                }
            }
        }
        // now the far more annoying part: checking whether the move opened a path to the king from the rook, bishop, or queen
        // first we find all of the pieces listed above
        int[][] allThreats = new int[10][2];
        int nextEntry = 0; // just to make sure pieces get stored in the right line of the allThreats array
        if(temp instanceof Queen || temp instanceof Rook || temp instanceof Bishop)
        // don't need to scan for the most recently moved piece, we already have its coordinates
        {
            allThreats[0][0] = temp.getRow(); allThreats[0][1] = temp.getCol();
            nextEntry++;
        }
        
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if (chessBoard[i][j] != null) {
                    if(chessBoard[i][j].getIsWhite() && !lastTurn && (chessBoard[i][j] instanceof Queen || 
                    chessBoard[i][j] instanceof Rook || chessBoard[i][j] instanceof Bishop))
                    {
                        allThreats[nextEntry][0] = i; allThreats[nextEntry][1] = j;
                        nextEntry++;
                        if(nextEntry > 4)
                            break;
                    } 
                    else if(!chessBoard[i][j].getIsWhite() && lastTurn && (chessBoard[i][j] instanceof Queen || 
                    chessBoard[i][j] instanceof Rook || chessBoard[i][j] instanceof Bishop))
                    {
                        allThreats[nextEntry][0] = i; allThreats[nextEntry][1] = j;
                        nextEntry++;
                        if(nextEntry > 4)
                            break;
                    } 
                }
            }
        }
        
        // now we check to see if any of these pieces are now threatening the king
        for(int i = 0; i < nextEntry; i++)
        {
            from[0] = allThreats[i][0]; from[1] = allThreats[i][1];
            if(isLegal(from, to))
                return true;
        }
        return false;
    }
    
    /**
     * checks if any given move could lead to a check 
     *
     * @param  to  int[] --  coordinates of where the piece is moved to
     * @return    Boolean -- true if moving to that square would put the player in check
     */
    public Boolean inefficientCheckChecker(int[] to)
    /*
     * It is difficult to determine conclusively whether this
     * would be more efficient than checking which pieces could
     * theoretically threaten the king based on proximity before
     * performing the actual checks on whether they *can*.
     * This is definitely easier to read, though.
     */
    {
        int[] from = new int[2];
        // checking whether the move opened a path to the king from the rook, bishop, or queen
        // first we find all of the pieces listed above
        int[][] allThreats = new int[16][2];
        int nextEntry = 0; // just to make sure pieces get stored in the right line of the allThreats array
        
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if (chessBoard[i][j] != null) {
                    if(chessBoard[i][j].getIsWhite() && !lastTurn && (chessBoard[i][j] instanceof Queen || 
                    chessBoard[i][j] instanceof Rook || chessBoard[i][j] instanceof Bishop))
                    {
                        allThreats[nextEntry][0] = i; allThreats[nextEntry][1] = j;
                        nextEntry++;
                        if(nextEntry > 15)
                            break;
                    } 
                    else if(!chessBoard[i][j].getIsWhite() && lastTurn && (chessBoard[i][j] instanceof Queen || 
                    chessBoard[i][j] instanceof Rook || chessBoard[i][j] instanceof Bishop))
                    {
                        allThreats[nextEntry][0] = i; allThreats[nextEntry][1] = j;
                        nextEntry++;
                        if(nextEntry > 15)
                            break;
                    } 
                }
            }
        }
        
        // now we check to see if any of these pieces are now threatening the king
        for(int i = 0; i < nextEntry; i++)
        {
            from[0] = allThreats[i][0]; from[1] = allThreats[i][1];
            if(isLegal(from, to))
                return true;
        }
        return false;
    }
    
    /**
     * returns all the possible the king can make
     *
     * @param  kingLocation  int[] -- current coordinates of the king
     * @return    Integer[][] of the coordinates of all possible moves
     */
    public Integer[][] kingMoves(int[] kingLocation)
    {
        Integer[][] possibleMoves = new Integer[8][2];
        possibleMoves[0][0] = kingLocation[0] - 1; possibleMoves[0][1] = kingLocation[1] - 1;
        possibleMoves[1][0] = kingLocation[0] - 1; possibleMoves[1][1] = kingLocation[1];
        possibleMoves[2][0] = kingLocation[0] - 1; possibleMoves[2][1] = kingLocation[1] + 1;
        possibleMoves[3][0] = kingLocation[0]; possibleMoves[3][1] = kingLocation[1] - 1;
        possibleMoves[4][0] = kingLocation[0]; possibleMoves[4][1] = kingLocation[1] + 1;
        possibleMoves[5][0] = kingLocation[0] + 1; possibleMoves[5][1] = kingLocation[1] - 1;
        possibleMoves[6][0] = kingLocation[0] + 1; possibleMoves[6][1] = kingLocation[1];
        possibleMoves[7][0] = kingLocation[0] + 1; possibleMoves[7][1] = kingLocation[1] + 1;
        return possibleMoves;
    }
    
    /**
     * checks if checkmate has been reached
     *
     * @return    Boolean -- true if checkmate has been reached
     */
    public Boolean checkmate()
    // this will be run AFTER the checkChecker returns true
    {
        int[] kingLocation = new int[2];
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(chessBoard[i][j] instanceof King && chessBoard[i][j].getIsWhite() && lastTurn)
                {
                    kingLocation[0] = i; kingLocation[1] = j;
                    break;
                }
                else if(chessBoard[i][j] instanceof King && !chessBoard[i][j].getIsWhite() && !lastTurn)
                {
                    kingLocation[0] = i; kingLocation[1] = j;
                    break;
                }
            }
        }
        // first we see if the king has any legal moves that are not also in check
        Integer[][] possibleMoves = kingMoves(kingLocation);
        int[] temp = new int[2]; // to store each possible move in an accessible format
        for(int i = 0; i < 8; i++)
        {
            temp[0] = possibleMoves[i][0]; temp[1] = possibleMoves[i][1];
            if(isLegal(kingLocation, temp) && !inefficientCheckChecker(temp))
                return false;
        }
        // ugh. now we check if they can block the path of the threatening piece
        int[][] allThreats = new int[5][2];
        int nextEntry = 0; // this wil just be a repurposing of the checkChecker code
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(chessBoard[i][j] != null && (chessBoard[i][j].getIsWhite() && !lastTurn && 
                (chessBoard[i][j] instanceof Queen || chessBoard[i][j] instanceof Rook 
                || chessBoard[i][j] instanceof Bishop)))
                {
                    allThreats[nextEntry][0] = i; allThreats[nextEntry][1] = j;
                    nextEntry++;
                    if(nextEntry > 4)
                        break;
                } 
                else if(chessBoard[i][j] != null && (!chessBoard[i][j].getIsWhite() && lastTurn && (chessBoard[i][j] instanceof Queen || 
                chessBoard[i][j] instanceof Rook || chessBoard[i][j] instanceof Bishop)))
                {
                    allThreats[nextEntry][0] = i; allThreats[nextEntry][1] = j;
                    nextEntry++;
                    if(nextEntry > 4)
                        break;
                } 
            }
        }
        int[] from = new int[2];
        // now we check to see if any of these pieces are now threatening the king
        ArrayList<Integer[]> route = new ArrayList<Integer[]> ();
        for(int i = 0; i < nextEntry; i++)
        {
            from[0] = allThreats[i][0]; from[1] = allThreats[i][1];
            if(isLegal(from, kingLocation))
            {
                char c = (char) (kingLocation[1] + 97);
                route = chessBoard[from[0]][from[1]].route(kingLocation[0], c);
            }
        }
        
        // these loops look really bad but they will perform an absolute maximum of 384 operations
        for(int i = 0; i < route.size(); i++)
        {
            Integer[] h = route.get(i); // temporarily stores each squre we're testing
            int[] square = new int[2];
            for(int x = 0; x < 2; x++)
                square[x] = h[x];
            for(int j = 0; j < 8; j++)
            {
                for(int k = 0; k < 8; k++)
                {
                    int[] coords = {j, k};
                    if(chessBoard[j][k] != null && (chessBoard[j][k].getIsWhite() && !lastTurn ||
                    !chessBoard[j][k].getIsWhite() && lastTurn))
                    {
                        // check if the piece can move into the path of 3
                        if(isLegal(coords, square))
                        {
                            return false;
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    /**
     * checks if stalemate has been reached
     *
     * @return    Boolean -- true if stalemate has been reached
     */
    public Boolean stalemate()
    // this is the only draw checker we have included because threefold repetition and
    // going 50 moves without capture/pawn move are uncommon and jaleelah is Tired
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(!(chessBoard[i][j] instanceof King) && chessBoard[i][j] != null)
                    return false;
            }
        }
        int[] kingLocation = new int[2];
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(chessBoard[i][j] != null && chessBoard[i][j] instanceof King && 
                chessBoard[i][j].getIsWhite() && lastTurn)
                {
                    kingLocation[0] = i; kingLocation[1] = j;
                    break;
                }
                else if(chessBoard[i][j] != null && chessBoard[i][j] instanceof King 
                && !chessBoard[i][j].getIsWhite() && !lastTurn)
                {
                    kingLocation[0] = i; kingLocation[1] = j;
                    break;
                }
            }
        }
        Integer[][] possibleMoves = kingMoves(kingLocation);
        int[] temp = new int[2]; // converting possible moves to a readable format
        for(int i = 0; i < 8; i++)
        {
            temp[0] = possibleMoves[i][0]; temp[1] = possibleMoves[i][1];
            if(isLegal(kingLocation, temp) && !inefficientCheckChecker(temp))
                return false;
        }
        return true;
    }

    public String toScore() {
        return "";
    }

    @Override
    /**
     * implements Game's abstract toString method
     * 
     * @return   visual representation of the game
     */
    public String toString() {
        String string = "\n";

        // prints each piece within a chessboard-like grid
        for (int i = 7; i >= 0; i--) {
            string = string + String.valueOf(i+1) + " | ";
            for (int k = 0; k < 8; k++) {
                if (chessBoard[i][k] == null)
                    string = string + "   | ";
                else
                    string = string + chessBoard[i][k] + " | ";
            }
            string = string + "\n";
        }
        string = string + "  |  a |  b |  c |  d |  e |  f |  g |  h |";
        return string;
    }

    // returns whether the correct colour of piece was returned
    /**
     * returns whether if a piece is the player's own (i.e., it is the right colour of piece)
     * 
     * @param   moveFrom    int[] -- coordinates of piece
     * @return    true if it's the correct colour of piece; false if not
     */
    public boolean correctColour(int[] moveFrom) {
        if (chessBoard[7 - moveFrom[0]][moveFrom[1]] == null ||
        chessBoard[7 - moveFrom[0]][moveFrom[1]].getIsWhite() == lastTurn)
            return false;
        return true;
    }
    
    /**
     * declares the winner, based off of which player resigned
     *
     * @return    true if white won; false if black won
     */
    public boolean winner() {
        if (lastTurn == false)
            return false;
        else
            return true;
    }
}
