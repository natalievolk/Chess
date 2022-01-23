 
/**
 * Tests that each piece's toString() and isLegalMove() methods work correctly
 *
 * @author Natalie Volk
 * @version March 20, 2020
 */
public class PieceTester
{
    public static void main(String[] args) {
        //tests the moves of various pieces
        
        //KNIGHT
        Knight knightOne = new Knight(4,'e',true);
        System.out.print("KNIGHT ... TESTING:  ");
        System.out.println(knightOne);
        System.out.println(knightOne.isLegalMove(2,'d'));
        System.out.println(knightOne.isLegalMove(2,'f'));
        System.out.println(knightOne.isLegalMove(3,'g'));
        System.out.println(knightOne.isLegalMove(5,'g'));
        System.out.println(knightOne.isLegalMove(6,'f'));
        System.out.println(knightOne.isLegalMove(6,'d'));
        System.out.println(knightOne.isLegalMove(5,'c'));
        System.out.println(knightOne.isLegalMove(3,'c'));
        knightOne = new Knight(4,'h',true);
        System.out.println(knightOne.isLegalMove(6,'i'));
        System.out.println();
        
        //PAWN
        Pawn pawnOne = new Pawn(2,'e',true);
        System.out.print("PAWN ... TESTING:  ");
        System.out.println(pawnOne);
        System.out.println(pawnOne.isLegalMove(3,'d'));
        System.out.println(pawnOne.isLegalMove(1,'e'));
        System.out.println(pawnOne.isLegalMove(3,'e'));
        System.out.println(pawnOne.isLegalMove(4,'e'));
        
        pawnOne = new Pawn(7,'e',false);
        System.out.println(pawnOne.isLegalMove(6,'f'));
        System.out.println(pawnOne.isLegalMove(8,'e'));
        System.out.println(pawnOne.isLegalMove(5,'e'));
        System.out.println(pawnOne.isLegalMove(6,'e'));
        
        pawnOne = new Pawn(5,'e',false);
        System.out.println(pawnOne.isLegalMove(6,'e')); //false
        System.out.println(pawnOne.isLegalMove(5,'e')); //same space
        System.out.println(pawnOne.isLegalMove(3,'e')); //false
        System.out.println(pawnOne.isLegalMove(4,'e')); // true
        
        pawnOne = new Pawn(4,'e',true);
        System.out.println(pawnOne.isLegalMove(6,'e')); //false
        System.out.println(pawnOne.isLegalMove(5,'e')); //true 
        System.out.println(pawnOne.isLegalMove(3,'e')); //false
        System.out.println();
        
        //ROOK
        Rook rookOne = new Rook(3,'c',false);
        System.out.print("ROOK ... TESTING:  ");
        System.out.println(rookOne);
        System.out.println(rookOne.isLegalMove(4,'e'));
        System.out.println(rookOne.isLegalMove(9,'c'));
        System.out.println(rookOne.isLegalMove(3,'i'));
        System.out.println(rookOne.isLegalMove(3,'e'));
        System.out.println(rookOne.isLegalMove(3,'a'));
        System.out.println(rookOne.isLegalMove(6,'c'));
        System.out.println(rookOne.isLegalMove(2,'c'));
        System.out.println();
        
        //BISHOP
        Bishop bishopOne = new Bishop(3,'c',true);
        System.out.print("BISHOP ... TESTING:  ");
        System.out.println(bishopOne);
        System.out.println(bishopOne.isLegalMove(3,'d'));
        System.out.println(bishopOne.isLegalMove(8,'i'));
        System.out.println(bishopOne.isLegalMove(2,'b'));
        System.out.println(bishopOne.isLegalMove(1,'a'));
        System.out.println(bishopOne.isLegalMove(4,'b'));
        System.out.println(bishopOne.isLegalMove(6,'f'));
        System.out.println();
        
        //QUEEN
        Queen queenOne = new Queen(3,'c',true);
        System.out.print("QUEEN ... TESTING:  ");
        System.out.println(queenOne);
        System.out.println(queenOne.isLegalMove(4,'e')); //false
        System.out.println(queenOne.isLegalMove(0,'c')); //false
        System.out.println(queenOne.isLegalMove(3,'d')); //true
        System.out.println(queenOne.isLegalMove(5,'a')); //true
        System.out.println();
        
        //KING
        King kingOne = new King(3,'c',false);
        System.out.print("KING ... TESTING:  ");
        System.out.println(kingOne);
        System.out.println(kingOne.isLegalMove(4,'e')); //false
        System.out.println(kingOne.isLegalMove(5,'e')); //false
        System.out.println(kingOne.isLegalMove(3,'d')); //true
        System.out.println(kingOne.isLegalMove(4,'b')); //true
        System.out.println(kingOne.isLegalMove(2,'d')); //true
        System.out.println(kingOne.isLegalMove(2,'c')); //true
        System.out.println();
    }
}
