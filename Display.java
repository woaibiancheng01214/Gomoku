import java.util.Scanner;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;

public class Display{    
    private String action;
    private final Scanner reader = new Scanner(System.in);
    private int cellSize;
    private int cellsInARow = 0;
    private int HLIMIT;
    private int WLIMIT;
    private int wMargin;
    private int hMargin;

    Display (int cellsInARow, int hlimit, int wlimit, int cellSize){
        this.cellsInARow = cellsInARow;
        this.HLIMIT = hlimit;
        this.WLIMIT = wlimit;
        this.wMargin = cellSize / 6;
        this.hMargin = cellSize / 6;
        this.cellSize = cellSize;
    }

    public void resetGrid(char[][] b, GraphicsContext g){
		g.clearRect(0, 0, WLIMIT * 100 + 40, HLIMIT * 100 + 40);
		draw(g, b);
		printText(g, 'X', 7, "player  's turn");
    }
    
    public void draw(GraphicsContext g, char[][] b) {
		g.clearRect(0, 0, WLIMIT * cellSize, HLIMIT * cellSize);
		g.setLineWidth(3);
		g.strokeRoundRect(0, 0, WLIMIT * cellSize, HLIMIT * cellSize, 20, 20);
		g.setLineWidth(1);
		drawLines(g);
		g.setLineWidth(3);
		for (int x = 0; x < HLIMIT; x++) {
			for (int y = 0; y < WLIMIT; y++) {
				char c = b[x][y];
				if (c == 'O') drawO(g, cellSize*y, cellSize*x);
				else if (c == 'X') drawX(g, cellSize*y, cellSize*x);
			}
		}
    }

    public void printText(GraphicsContext g, char player, int offset, String mainStr){
		StringBuilder str = new StringBuilder(mainStr);
		str.insert(offset, player);
		g.clearRect(0, HLIMIT * cellSize + 10, WLIMIT * cellSize, HLIMIT * cellSize + 40);
		g.setTextAlign(TextAlignment.CENTER);
		g.setLineWidth(0.9);
		g.strokeText(str.toString(), WLIMIT * 25, HLIMIT * cellSize + 20);
	}

    private void drawLines(GraphicsContext g) {
        int limit = cellsInARow * cellSize - 5;
        for(int i = 1; i < cellsInARow; i++){
            g.strokeLine(0.5 + i * cellSize, 5, 0.5 + i * cellSize, limit);
        }
        for(int j = 1; j < cellsInARow; j++){
            g.strokeLine(5, 0.5 + j * cellSize, limit, 0.5 + j * cellSize);
        }
	}
    
    private void drawO(GraphicsContext g, double x, double y) {
        g.strokeOval(wMargin + x, hMargin + y, cellSize - 2 * wMargin, cellSize - 2 * hMargin);
	}

	private void drawX(GraphicsContext g, double x, double y) {
		g.strokeLine(wMargin+x, hMargin+y, cellSize - wMargin+x, cellSize - hMargin+y);
		g.strokeLine(wMargin+x, cellSize - hMargin+y, cellSize - wMargin+x, hMargin+y);
    }
    
    public String chooseDisplayMode(){
        System.out.println("Choose a mode for this game " +
            "1 for text, 2 for graphics");
        String mode = reader.next();
        return mode;
    }

    public String playerInput(char player, boolean vaildInput)
    {
        if(player == 'X'){
            action = scanner('X', vaildInput);
            return action;
        }
        if(player == 'O'){
            action = scanner('O', vaildInput);
            return action;
        }
        action = null;
        return action;
    }

    public void errorMsg(CheckInfo msg)
    {
        if(msg == CheckInfo.valid){
            return;
        }
        else if(msg == CheckInfo.invalidLength){
            System.out.println("invalid length of input. A number and a letter required");
        }
        else if(msg == CheckInfo.invalidType){
            System.out.println("invalid type of input. A number and a letter required. Example: 1a/2b/3c");
        }
        else if(msg == CheckInfo.outOfBound){
            System.out.println("Out of chessboard (vaild field: 1~3 a~c)");
        }
        else {
            System.out.println("Cannot select a place already exist a 'X' or 'O'!");
        }
        System.out.println("Please re-enter:");
    }

    public void closeReader()
    {
        reader.close();
    }

    // print a paradigm of board which is extendable
    public void printBoard(char[][] b, int HEIGHT, int WIDTH)
    {
        System.out.printf("\nCurrent board is shown below:\n");
        System.out.printf("  ");
        for(int j = 0; j < WIDTH; j++){
            System.out.printf("%d ", j + 1);
        }
        System.out.printf("\n");
        for(int i = 0; i < HEIGHT; i++){
            System.out.printf("%c ", 'a' + i);
            for(int j = 0; j < WIDTH; j++){
                System.out.printf("%c ", b[i][j]);
            }
            System.out.printf("\n");
        }
    }

    public void printResult(Type result)
    {
        if(result == Type.player1win){
            System.out.println("Player1(X) wins!");
        }
        else if(result == Type.player2win){
            System.out.println("Player2(O) wins!");
        }
        else if(result == Type.draw){
            System.out.println("draw!");
        }
        else
            System.out.println("Fatal error: NULL result!");
    }

    private String scanner(char player, boolean vaildInput)
    {
        if(vaildInput == true){
            System.out.printf("\nPlayer(%c) turn, please " +
                "type a coordinate: \n", player == 'X' ? 'X' : 'O');
        }
        String s = reader.next();
        return s;
    }
}