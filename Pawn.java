
/**
 * Implements the function of a Pawn chesspiece
 * 
 * @author Natalie Volk
 * @version March 12, 2020
 */
import java.util.ArrayList;
public class Pawn extends ChessPiece
{
   public Pawn(int startRow, char startCol, boolean white)
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
        // if the pawn is white, it can only move in the ascending direction of row numbers
        if (getIsWhite() == true) {
            if (getRow() == (r - 1) && getCol() == c)
                return true;
            // if the pawn is in its starting position, it can move two spaces forward
            else if (getRow() == 2 && getRow() == (r-2) && getCol() == c)
                return true;
        }
        // if the pawn is black, it can only move in the descending direction of row numbers
        else {
            if (getRow() == (r + 1) && getCol() == c)
                return true;
            // if the pawn is in its starting position, it can move two spaces forward
            else if (getRow() == 7 && getRow() == (r+2) && getCol() == c)
                return true;
        }
        return false;
    }
    
    @Override
    /**
     * implements ChessPiece's route() method
     * 
     * @param  r  row to move to
     * @param  c  column to move to
     * @return    ArrayList of coordinates that the piece travels by
     */
    public ArrayList<Integer[]> route(int r, char c) {
        ArrayList<Integer[]> squares = new ArrayList<Integer[]>();
        Integer[] temp;
        // if the pawn is in its initial position and is trying to
        //move two spaces forward
        if (getRow() == 2 && getRow() == (r-2)) {
            temp = new Integer[] {r-2, c-97};
            squares.add(temp);
        }
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
            return "WP";
        return "BP";
    }
}
