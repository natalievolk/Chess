
/**
 * Implements the function of a Knight chesspiece
 * 
 * @author Natalie Volk
 * @version March 12, 2020
 */
import java.util.ArrayList;
public class Knight extends ChessPiece
{
    public Knight(int startRow, char startCol, boolean white)
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
        //returns true if the move is L-shaped, with two spaces in one direction and one in the other
        if((getCol() == (c+2) && getRow() == (r+1)) || (getCol() == (c-2) && 
        getRow() == (r+1)) || (getCol() == (c+2) && getRow() == (r-1)) ||
        (getCol() == (c-2) && getRow() == (r-1))) {
            return true;
        }
        //returns true if the move is L-shaped, with two spaces in one direction and one in the other
        else if ((getRow() == (r+2) && getCol() == (c+1)) || (getRow() == 
        (r+2) && getCol() == (c-1)) || (getRow() == (r-2) && getCol() == (c+1))
        || (getRow() == (r-2) && getCol() == (c-1))) {
            return true;
        }
        //if not, returns false
        return false;
    }
    
    @Override
    /**
     * implements ChessPiece's route() method
     * However, knights can leap over pieces and therefore the path 
     * it travels along does not need to be documented
     * 
     * @param  r  row to move to
     * @param  c  column to move to
     * @return    ArrayList of coordinates that the piece travels by
     */
    public ArrayList<Integer[]> route(int r, char c) {
        //returns empty ArrayList
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
            return "WN";
        return "BN";
    }
}