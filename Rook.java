
/**
 * Implements the function of a Rook chesspiece
 * 
 * @author Natalie Volk
 * @version March 12, 2020
 */
import java.util.ArrayList;
public class Rook extends ChessPiece
{
    public Rook(int startRow, char startCol, boolean white) {
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
        // returns true if the move is only horizontal or only vertical
        if (getRow() == r ^ getCol() == c)
            return true;
        // if not, returns false
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
        // if the rook travels along a row
        if (getRow() == r) {
            // if the rook is travelling to decreasing columns
            if (getCol() > c) {
                for (int i = getCol() - 98; i > c-97; i--) {
                    temp = new Integer[] {r-1,i};
                    squares.add(temp);
                }
            }
            // if the rook is travelling along increasing columns
            else {
                for (int i = getCol() - 96; i < c -97; i++) {
                    temp = new Integer[] {r-1,i};
                    squares.add(temp);
                }
            }
        }
        // if the rook travels along a column
        if (getCol() == c) {
            // if the rook is travelling along decreasing row numbers
            if (getRow() > r) {
                for (int i = getRow() - 2; i > r-1; i--) {
                    temp = new Integer[] {i,c-97};
                    squares.add(temp);
                }
            }
            // if the rook is travelling along increasing row numbers
            else {
                for (int i = getRow(); i < r -1; i++) {
                    temp = new Integer[] {i,c-97};
                    squares.add(temp);
                }
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
            return "WR";
        return "BR";
    }
}
