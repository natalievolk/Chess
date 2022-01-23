
/**
 * Implements the function of a Bishop chesspiece
 * 
 * @author Natalie Volk
 * @version March 12, 2020
 */
import java.util.ArrayList;
public class Bishop extends ChessPiece
{
    public Bishop(int startRow, char startCol, boolean white)
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
        // bishop can only move on the diagonal (so change in columns is equal to change in rows)
        if (Math.abs(r-getRow()) == Math.abs(c-getCol()))
            return true;
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
        Integer[] temp = new Integer[2];
        for (int i = 1; i < Math.abs(getRow() - r); i++) {
            // if travelling across decreasing rows
            if (getRow() > r) 
                temp[0] = getRow() - i - 1;
            // if travelling across increasing rows
            else
                temp[0] = getRow() + i -1;
            // if travelling across decreasing columns
            if (getCol() > c) 
                temp[1] = getCol() - i - 97;
            // if travelling across increasing columns
            else
                temp[1] = getCol() + i -97;
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
            return "WB";
        return "BB";
    }
}
