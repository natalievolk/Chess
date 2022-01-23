/**
 * Abstract class ChessPiece - models a chess piece
 * 
 * @author Natalie Volk
 * @version March 10, 2020
 */
import java.util.ArrayList;
abstract class ChessPiece
{
    private int row;
    private char col;
    private boolean isWhite; // false = black
    
    /**
     * Constructor for objects of class ChessPiece
     */
    public ChessPiece(int startRow, char startCol, boolean white)
    {
        row = startRow;
        col = startCol;
        isWhite = white;
    }
    
    /**
     * Abstract method, which (when implemented in a child class) would see if a certain move is legal
     *
     * @param  r  int -- the row to move to
     * @param  c  char -- the column to move to
     * @return    a boolean indicating whether the move is legal or not
     */
    abstract boolean isLegalMove(int r, char c);
    
    /**
     * Abstract method, which will document all the coordinates a piece
     * travels past
     *
     * @param  r  int -- the row to move to
     * @param  c  char -- the column to move to
     * @return    an ArrayList containing the coordinates that the piece travels past
     */
    abstract ArrayList<Integer[]> route(int r, char c);
    
    /**
     * method to move the piece
     *
     * @param  r  int -- the row to move to
     * @param  c  char -- the column to move to
     */
    final void move(int r, char c)
    {
        row = r;
        col = c;
    }
    
    /**
     * returns the current row of the piece
     *
     * @return    the row of the piece
     */
    public int getRow()
    {
        return row;
    }

    /**
     * returns the current column of the piece
     *
     * @return    the column of the piece
     */
    public char getCol()
    {
        return col;
    }
    
    /**
     * indicates whether the piece is white or black
     *
     * @return    true if the piece is white; false if the piece if black
     */
    public boolean getIsWhite()
    {
        return isWhite;
    }
}

