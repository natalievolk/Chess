
/**
 * Implements the function of a King chesspiece
 * 
 * @author Natalie Volk
 * @version March 12, 2020
 */
import java.util.ArrayList;
public class King extends ChessPiece
{
    public King(int startRow, char startCol, boolean white)
    {
        super(startRow, startCol, white);
    }
    
    @Override
    /**
     * implements ChessPiece's isLegalMove method
     * 
     * @param  r  row to move to
     * @param  c  column to move to
     * @return    true if the move is legal; false if it is not
     */
    public boolean isLegalMove(int r, char c) {
        // returns false if player tries to move off the game board
        if (!(r <= 8 && r >0 && c <= 'h'))
            return false;
        // the king can move diagonally, vertically, or horizontally, but only one space at a time
        if ((Math.abs(getCol() - c) == 0 || Math.abs(getCol() - c) == 1) &&
        (Math.abs(getRow() - r) == 0 || Math.abs(getRow() - r) == 1))
            return true;
        return false;
    }
    
    @Override
    /**
     * implements ChessPiece's route() method
     * However, the King never travels by any coordinates that it doesn't land on,
     * as a King can only move one space at a time.
     * 
     * @param  r  row to move to
     * @param  c  column to move to
     * @return    ArrayList of coordinates that the piece travels by (always an empty
     *              ArrayList)
     */
    public ArrayList<Integer[]> route(int r, char c) {
        // always returns empty ArrayList
        ArrayList<Integer[]> squares = new ArrayList<Integer[]>();
        return squares;
    }
    
    @Override
    /**
     * overrides object's toString method
     * 
     * @return   string representation of the piece
     */
    public String toString() {
        if (getIsWhite() == true)
            return "WK";
        return "BK";
    }
}
