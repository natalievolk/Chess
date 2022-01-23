
/**
 * Models a game
 *
 * @author Natalie Volk
 * @version March 25, 2020
 */
public interface Game
{
   //starts a new game
   void newGame();
   
   //plays out the next turn, returns true if successful, false if move was invalid
   Boolean nextTurn(int[] moveFrom, int[] moveTo);
   
   // checks if the selected move is possible (used in nextTurn and checkChecker)
   Boolean isLegal(int[] moveFrom, int[] moveTo);
   
   // checks if the most recent move has put a side in check
   Boolean checkChecker(ChessPiece temp);
   
   // longer version of checkChecker that determines whether a prospective move would put the king in check
   Boolean inefficientCheckChecker(int[] to);
   
   // determines all the king's legal moves. Used in both checkmate and stalemate methods.
   Integer[][] kingMoves(int[] kingLocation);
   
   // checks if there is a stalemate
   Boolean stalemate();
   
   // checks if a side is in checkmate
   Boolean checkmate();
   
   //returns a string representing the current score of the game
   String toScore();
   
   //returns a string with a visual representation of the current state of the game
   String toString();
   
   //returns true if the game has been won by someone
   boolean winner();
}
