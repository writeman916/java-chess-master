package chess;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JComponent implements MouseListener {

	private enum GameStatus {
		Idle, Error, Started, Checkmate, Stalemate
	};

	GameStatus status = GameStatus.Idle;
	boolean imagesLoaded = false;
	
	Random rand = new Random();

	public Board gameBoard;

	// piece selected by the user
	Piece selectedPiece = null;
	// invalid piece selected by user
	Piece invalidPiece = null;
	// a list of the possible moves for the selected piece
	List<Move> okMoves;
	
	// list weight for Pieces
	List<int[]> listW = new ArrayList<int[]>();
	
	// defines color variables for different elements of the game
	final Color invalidColor = new Color(255, 0, 0, 127);
	final Color selectionColor = new Color(255, 255, 0, 127);
	final Color validMoveColor = new Color(0, 255, 0, 127);
	final Color checkColor = new Color(127, 0, 255, 127);
	final Color lastMovedColor = new Color(0, 255, 255, 75);
	final Color lightColor = new Color(255, 255, 255, 255);
	final Color darkColor = new Color(0, 0, 0, 255);


	public GamePanel(int w, int h) {
		// set the size of the component
		this.setSize(w, h);
		// loads piece images from file
		loadImages();
		// inititalizes the game board for a 2-player game
		newGame();
		// adds a listener for mouse events
		
		
		for(int i=0;i<25;i++)
		{
			listW.add(RandArray(5));
		}
		
		this.addMouseListener(this);
	}


	public void newGame() {
		// creates a new board
		gameBoard = new Board(true);
		status = GameStatus.Started;

		// resets variables
		selectedPiece = null;
		invalidPiece = null;

		// draws the newly created board
		this.repaint();
	}

	
	public void new2AiGame() {
		System.out.println("2 AI fight");
		int aiDepth = 3;
		Piece.Color aiColor1 = Piece.Color.White;
		Piece.Color aiColor2 = Piece.Color.Black;

		// creates a new game
		newGame();
		// then sets the ai for the board
		int test1 =  rand.nextInt(listW.size());
		int test2  = -1;
		while(test2 == -1)
		{
			int value = rand.nextInt(listW.size());
			if( value != test1)
			{
				test2 =  value;
			}
		}
		
		
		int[] weight1 = listW.get(test1);
		int[] weight2 = listW.get(test2);
		
		
		
		gameBoard.set2Ai(new Ai(aiColor1, aiDepth), new Ai(aiColor2, aiDepth));
		
		gameBoard.setWeight(weight1, aiColor1);
		gameBoard.setWeight(weight2, aiColor2);
	
		System.out.println(listW.size());
		for(int i=0;i<listW.size();i++)
		{
			System.out.println(listW.get(i)[0]+","+listW.get(i)[1]+","+listW.get(i)[2]+","+listW.get(i)[3]+","+listW.get(i)[4]);
		}
		
		System.out.println("1-Pawn Knight Bishop Rook Queen");
		System.out.println(weight1[0] + "      " + " " + weight1[1] + "    " + weight1[2] + "      " + weight1[3] + "\t" + weight1[4]);
	
		
		System.out.println("2-Pawn Knight Bishop Rook Queen");
		System.out.println(weight2[0] + "      " + " " + weight2[1] + "    " + weight2[2] + "      " + weight2[3] + "\t" + weight2[4]);
	
		while (!gameBoard.gameOver()) {
			twoplayerAI();
		}
		
		
		
	

		
		
		if(gameBoard.gameOver())
		{
			if(gameBoard.AILoser())
			{
				if(listW.size() > 1)
					listW.remove(weight1);
				System.out.println("Xoa weight 1");
			}
			else
			{
				if(listW.size() > 1)
					listW.remove(weight2);
				System.out.println("Xoa weight 2");
			}
		}
		
		
		System.out.println(listW.size());
		for(int i=0;i<listW.size();i++)
		{
			System.out.println(listW.get(i)[0]+","+listW.get(i)[1]+","+listW.get(i)[2]+","+listW.get(i)[3]+","+listW.get(i)[4]);
		}

	}

	public void newAiGame() {

		int aiDepth;
		Piece.Color aiColor;


		aiDepth = 3;

		aiColor = Piece.Color.Black;

		// creates a new game
		newGame();
		
		int testWeight = rand.nextInt(listW.size());
		int[] weight = listW.get(testWeight);
		

		gameBoard.setAi(new Ai(aiColor, aiDepth));
		gameBoard.setWeight(weight, aiColor);
			
			

		System.out.println("Pawn Knight Bishop Rook Queen");
		System.out.println(weight[0] + "      " + " " + weight[1] + "    " + weight[2] + "      " + weight[3] + "\t" + weight[4]);

		if(gameBoard.gameOver())
		{
			if(gameBoard.AILoser())
			{
				listW.remove(testWeight);
			}
		}
			
		
//		// simulates a click event to prompt the ai to make the first move
//		if (aiColor == Piece.Color.White)
//			mousePressed(null);

	}

	public void undo() {
		// resets variables for helper circles
		selectedPiece = null;
		invalidPiece = null;
		okMoves = null;

		// if a two player game
		if (gameBoard.getAi() == null)
			// skip back one move
			gameBoard = gameBoard.getPreviousState();
		else
		// skip back to the last move by the player
		if (gameBoard.getTurn() != gameBoard.getAi().getColor())
			gameBoard = gameBoard.getPreviousState().getPreviousState();
		else
			gameBoard = gameBoard.getPreviousState();

		// set the game status to started
		status = GameStatus.Started;

		this.repaint();
	}


	private void loadImages() {
		try {
			// initialize two arrays of bufferedImages
			BufferedImage[] whiteImages = new BufferedImage[6];
			BufferedImage[] blackImages = new BufferedImage[6];

			// if the PIECES folder doesn't exist, create it
			File directory = new File("PIECES");
			if (!directory.exists()) {
				if (directory.mkdir()) {
					// the directory will be empty, so throw an exception
					throw new Exception("The PIECES directory did not exist. "
							+ "It has been created. Ensure that it contains the following files: \n"
							+ "WHITE_PAWN.PNG\n" + "WHITE_KNIGHT.PNG\n" + "WHITE_BISHOP.PNG\n" + "WHITE_ROOK.PNG\n"
							+ "WHITE_QUEEN.PNG\n" + "WHITE_KING.PNG\n" + "BLACK_PAWN.PNG\n" + "BLACK_KNIGHT.PNG\n"
							+ "BLACK_BISHOP.PNG\n" + "BLACK_ROOK.PNG\n" + "BLACK_QUEEN.PNG\n" + "BLACK_KING.PNG");
				}
			}

			// load all white images
			whiteImages[0] = ImageIO.read(new File("PIECES/WHITE_PAWN.PNG"));
			whiteImages[1] = ImageIO.read(new File("PIECES/WHITE_KNIGHT.PNG"));
			whiteImages[2] = ImageIO.read(new File("PIECES/WHITE_BISHOP.PNG"));
			whiteImages[3] = ImageIO.read(new File("PIECES/WHITE_ROOK.PNG"));
			whiteImages[4] = ImageIO.read(new File("PIECES/WHITE_QUEEN.PNG"));
			whiteImages[5] = ImageIO.read(new File("PIECES/WHITE_KING.PNG"));

			// load all black images
			blackImages[0] = ImageIO.read(new File("PIECES/BLACK_PAWN.PNG"));
			blackImages[1] = ImageIO.read(new File("PIECES/BLACK_KNIGHT.PNG"));
			blackImages[2] = ImageIO.read(new File("PIECES/BLACK_BISHOP.PNG"));
			blackImages[3] = ImageIO.read(new File("PIECES/BLACK_ROOK.PNG"));
			blackImages[4] = ImageIO.read(new File("PIECES/BLACK_QUEEN.PNG"));
			blackImages[5] = ImageIO.read(new File("PIECES/BLACK_KING.PNG"));

			// set the white and black images in the Piece class
			Piece.setBlackImages(blackImages);
			Piece.setWhiteImages(whiteImages);

			// images loaded without errors
			imagesLoaded = true;
		} catch (Exception e) {
			status = GameStatus.Error;
			// create a message to inform the use about the error
			String message = "Could not load piece images. " + "Check that all 12 images exist in the PIECES folder "
					+ "and are accessible to the program.\n"
					+ "The program will not function properly until this is resolved.\n\n" + "Error details: "
					+ e.getMessage();
			// display the message
			JOptionPane.showMessageDialog(this, message, "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}


	public void twoplayerAI() {
		if (status == GameStatus.Started) {
			invalidPiece = null;
			// get board width and height
			int w = getWidth();
			int h = getHeight();

			// process two ai move
			if (gameBoard.getAi() != null && gameBoard.getAi2() != null) {

				// repaint board immediately, to show the
				// player's last move
				this.paintImmediately(0, 0, this.getWidth(), this.getHeight());

				// get a move from the board's ai
				Move computerMove = gameBoard.getAi().getMove(gameBoard);

				Move computerMove2 = gameBoard.getAi2().getMove(gameBoard);

				if (computerMove != null) {
					// if a move was returned, make move
					gameBoard.doMove(computerMove, false);
				}

				if (computerMove2 != null) {
					// if a move was returned, make move
					gameBoard.doMove(computerMove2, false);
				}

			}

			// if a side cannot make any valid moves
			if (gameBoard.gameOver()) {
				// repaint board immediately, before JOptionPane is shown.
				this.paintImmediately(0, 0, this.getWidth(), this.getHeight());

				// if a king not currently in check, stalemate
				if (gameBoard.getPieceInCheck() == null) {
					status = GameStatus.Stalemate;
					JOptionPane.showMessageDialog(this, "Stalemate!", "", JOptionPane.INFORMATION_MESSAGE);
				} else {
					// if a king is in check, checkmate
					status = GameStatus.Checkmate;
					JOptionPane.showMessageDialog(this, "Checkmate!", "", JOptionPane.INFORMATION_MESSAGE);
				}

			}
			this.repaint(); // calls paintComponent
		}
	}

	public void mousePressed(MouseEvent e) {
		if (status == GameStatus.Started) {
			invalidPiece = null;
			// get board width and height
			int w = getWidth();
			int h = getHeight();

			// respond to player action
			if (gameBoard.getAi() == null || gameBoard.getAi().getColor() != gameBoard.getTurn()) {

				// turn mouse click coordinates into board coordinates
				Point boardPt = new Point(e.getPoint().x / (w / 8), e.getPoint().y / (h / 8));

				// if no piece has been selected yet
				if (selectedPiece == null) {
					// select the piece that was clicked
					selectedPiece = gameBoard.getPieceAt(boardPt);
					if (selectedPiece != null) {
						// get the available moves for the piece
						okMoves = selectedPiece.getValidMoves(gameBoard, true);
						// if the piece is of the wrong color, mark as invalid
						if (selectedPiece.getColor() != gameBoard.getTurn()) {
							okMoves = null;
							invalidPiece = selectedPiece;
							selectedPiece = null;
						}
					}
				} else {

					// check if the player clicked on the destination of a move
					Move playerMove = moveWithDestination(boardPt);

					// if so, perform that move
					if (playerMove != null) {
						gameBoard.doMove(playerMove, true);
						selectedPiece = null;
						okMoves = null;
					} else {
						// otherwise, ignore click and reset variables
						selectedPiece = null;
						okMoves = null;
					}
				}
			}
			// process an ai move
			if (gameBoard.getAi() != null && gameBoard.getAi().getColor() == gameBoard.getTurn()) {


				// repaint board immediately, to show the
				// player's last move
				this.paintImmediately(0, 0, this.getWidth(), this.getHeight());

				// get a move from the board's ai
				Move computerMove = gameBoard.getAi().getMove(gameBoard);

				if (computerMove != null) {
					// if a move was returned, make move
					gameBoard.doMove(computerMove, false);
				}
			}

			
			// if a side cannot make any valid moves
			if (gameBoard.gameOver()) {
				// repaint board immediately, before JOptionPane is shown.
				this.paintImmediately(0, 0, this.getWidth(), this.getHeight());

				// if a king not currently in check, stalemate
				if (gameBoard.getPieceInCheck() == null) {
					status = GameStatus.Stalemate;
					JOptionPane.showMessageDialog(this, "Stalemate!", "", JOptionPane.INFORMATION_MESSAGE);
				} else {
					// if a king is in check, checkmate
					status = GameStatus.Checkmate;
					JOptionPane.showMessageDialog(this, "Checkmate!", "", JOptionPane.INFORMATION_MESSAGE);
				}

			}

			this.repaint(); // calls paintComponent
		}
	}


	
	protected int[] RandArray(int num)
	{
		int[] result = { -1,-1,-1,-1,-1};
		int count = 0;
		int value;
		boolean flag;
		
		while(count<num)
		{
			flag = false;
			value = rand.nextInt(5);
		
			for(int i=0;i<num;i++)
			{
				if(result[i] == value)
				{
					flag = true;
					break;
				}
			}
			if(flag == false)
			{
				result[count] = value;
				count++;
			}
		}
		
		return result;
	}
	@Override
	protected void paintComponent(Graphics gr) {
		int w = getWidth();
		int h = getHeight();

		// square height and width
		int sW = w / 8;
		int sH = h / 8;

		// create an off-screen buffer
		Image buffer = createImage(w, h);

		// get buffer's graphics context
		Graphics g = buffer.getGraphics();

		// draw the board to the buffer
		drawBoard(g, sW, sH);

		drawHelperCircles(g, sW, sH);

		// if images have been loaded, draw them
		if (imagesLoaded)
			drawPieces(g, sW, sH);

		// draw the contents of the buffer to the panel
		gr.drawImage(buffer, 0, 0, this);
	}


	private void drawHelperCircles(Graphics g, int sW, int sH) {
		// if a piece is selected
		if (selectedPiece != null) {
			// draw circle for that piece
			Point p = selectedPiece.getLocation();
			g.setColor(selectionColor);
			g.fillOval(p.x * sW, p.y * sH, sW, sH);

			// and all of it's valid moves
			g.setColor(validMoveColor);
			for (Move m : okMoves) {
				Point pt = m.getMoveTo();
				g.fillOval(pt.x * sW, pt.y * sH, sW, sH);
			}
		}
		// if the user clicked on a piece of the wrong color
		if (invalidPiece != null) {
			// draw circle for that piece
			Point p = invalidPiece.getLocation();
			g.setColor(invalidColor);
			g.fillOval(p.x * sW, p.y * sH, sW, sH);
		}
		// if a king is in check
		if (gameBoard.getPieceInCheck() != null) {
			// draw circle for the king
			Point p = gameBoard.getPieceInCheck().getLocation();
			g.setColor(checkColor);
			g.fillOval(p.x * sW, p.y * sH, sW, sH);
		}
		// if a piece has been moved
		if (gameBoard.getLastMovedPiece() != null) {
			// draw circle to indicate last moved piece
			Point p = gameBoard.getLastMovedPiece().getLocation();
			g.setColor(lastMovedColor);
			g.fillOval(p.x * sW, p.y * sH, sW, sH);
		}
	}

	
	private void drawPieces(Graphics g, int sW, int sH) {
		// for each piece on the board
		for (Piece pc : gameBoard.getPieces()) {
			// if piece is white
			if (pc.getColor() == Piece.Color.White) {
				// draw its white image
				g.drawImage(pc.getWhiteImage(), pc.getLocation().x * sW, pc.getLocation().y * sH, sW, sH, null);
			} else {
				// draw its black image
				g.drawImage(pc.getBlackImage(), pc.getLocation().x * sW, pc.getLocation().y * sH, sW, sH, null);
			}
		}
	}


	private void drawBoard(Graphics g, int sW, int sH) {
		// draw a light background
		g.setColor(lightColor);
		g.fillRect(0, 0, sW * 8, sH * 8);

		boolean dark = false;
		g.setColor(darkColor);
		// draw black squares
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (dark) {
					g.fillRect(x * sW, y * sH, sW, sH);
				}
				dark = !dark;
			}
			dark = !dark;
		}
	}

	
	private Move moveWithDestination(Point pt) {
		for (Move m : okMoves)
			if (m.getMoveTo().equals(pt))
				return m;
		return null;
	}


	public void mouseExited(MouseEvent e) {
	}


	public void mouseEntered(MouseEvent e) {
	}


	public void mouseReleased(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}
}
