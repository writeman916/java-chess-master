package chess;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class Knight extends Piece{
       
    private int imageNumber = 2;
    
   
    public Knight(Point location, Color color) {
        numMoves = 0;
        this.color = color;
        this.location = location;
    }

   
    private Knight(Point location, Color color, int moves) {
        this.numMoves = moves;
        this.color = color;
        this.location = location;
    }
    
   
    
    
    
    public String getKind()
    {
    	return "Knight";
    }
    
    
    public void setImageNumber(int in)
    {
    	this.imageNumber = in;
    }
    
    
    public int getImageNumber() {
        return imageNumber;
    }

  
    public BufferedImage getWhiteImage() {
        return whiteImages[1];
    }
    
   
    public BufferedImage getBlackImage() {
        return blackImages[1];
    }
    
   
    public Piece clone() {
        return new Knight(new Point(this.location.x, this.location.y),
                this.color, this.numMoves);
    }
    
  
    public List<Move> getValidMoves(Board board, boolean checkKing) {       
        int x = location.x;
        int y = location.y;

        List<Move> moves = new ArrayList<Move>();

        // if no board given, return empty list
        if (board == null)
            return moves;
        
        // check L-shapes
        addIfValid(board, moves, new Point(x + 1, y + 2));
        addIfValid(board, moves, new Point(x - 1, y + 2));
        addIfValid(board, moves, new Point(x + 1, y - 2));
        addIfValid(board, moves, new Point(x - 1, y - 2));
        addIfValid(board, moves, new Point(x + 2, y - 1));
        addIfValid(board, moves, new Point(x + 2, y + 1));
        addIfValid(board, moves, new Point(x - 2, y - 1));
        addIfValid(board, moves, new Point(x - 2, y + 1));    

        // check that move doesn't put own king in check
        if (checkKing)
            for(int i = 0; i < moves.size(); i++)
                if (board.movePutsKingInCheck(moves.get(i), this.color)) {
                    // if move would put king it check, it is invalid and
                    // is removed from the list
                    moves.remove(moves.get(i));
                    // iterator is decremented due to the size of the list
                    // decreasing.
                    i--;
                }
        return moves;
    }
    
 
    private void addIfValid(Board board, List<Move> list, Point pt) {
        if(board.validLocation(pt)) {
            Piece pc = board.getPieceAt(pt);
            if(pc == null || pc.getColor() != this.color) {
                list.add(new Move(this, pt, pc));
            }
        }
    }
}
