
/**
 * Implements the function of a Queen chesspiece
 * 
 * @author Natalie Volk
 * @version March 12, 2020
 */
import java.util.ArrayList;
public class Queen extends ChessPiece
{
    public Queen(int startRow, char startCol, boolean white)
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
        // the queen can move horizontally or vertically (like a rook)
        if (getRow() == r ^ getCol() == c)
            return true;
        // or the queen can move on a diagonal (like a bishop)
        else if (Math.abs(r-getRow()) == Math.abs(c-getCol()))
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
        // if moving like a rook
        if (getRow() == r) {
            if (getCol() > c) {
                for (int i = getCol() - 98; i > c-97; i--) {
                    temp = new Integer[] {r,i};
                    squares.add(temp);
                }
            }
            else {
                for (int i = getCol() - 96; i < c -97; i++) {
                    temp = new Integer[] {r,i};
                    squares.add(temp);
                }
            }
        }
        else if (getCol() == c) {
            if (getRow() > r) {
                for (int i = getRow() - 2; i > r-1; i--) {
                    temp = new Integer[] {i,c-97};
                    squares.add(temp);
                }
            }
            else {
                for (int i = getRow(); i < r -1; i++) {
                    temp = new Integer[] {i,c-97};
                    squares.add(temp);
                }
            }
        }
        // if moving like a bishop
        else {
            for (int i = 1; i < Math.abs(getRow() - r); i++) {
                if (getRow() > r) 
                    temp[0] = getRow() - i - 1;
                else
                    temp[0] = getRow() + i -1;
                if (getCol() > c)
                    temp[1] = getCol() - i - 97;
                else 
                    temp[1] = getCol() + i -97;
                squares.add(temp);
            }
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
            return "WQ";
        return "BQ";
    }
}
