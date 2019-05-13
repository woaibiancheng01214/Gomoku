import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;

public class Display{    
    private int cellSize;
    private int halfCellSize;
    private int HLIMIT;
    private int WLIMIT;
    private int wMargin;
    private int hMargin;
    private int borderSizeHeight = 60;
    private int borderSizeWidth = 250;
    private BorderPane root;
    private GraphicsContext gcCenter;
    private Canvas canvas;

    Display (int hlimit, int wlimit, int cellSize, BorderPane bp){
        this.HLIMIT = hlimit;
        this.WLIMIT = wlimit;
        this.wMargin = cellSize / 6;
        this.hMargin = cellSize / 6;
        this.cellSize = cellSize;
        this.halfCellSize = cellSize / 2;
        this.root = bp;
    }

    public Canvas getCanvas(){
        return canvas;
    }
    public Label getTopPane() {
        Label lbl = new Label("GOMOKU");
        lbl.prefWidthProperty().bind(root.widthProperty());
        lbl.setPrefHeight(borderSizeHeight);
        lbl.setStyle("-fx-border-style: dotted; -fx-border-width: 0 0 1 0; -fx-font-weight:bold");
        lbl.setFont(new Font("Kalam", 30));
        lbl.setAlignment(Pos.BASELINE_CENTER);
        
        return lbl;
    }

    public HBox getBottom(Button newGame, Button stopOrResumeGame) {
        HBox hbox = new HBox();
        hbox.prefWidthProperty().bind(root.widthProperty());
        hbox.setPrefHeight(borderSizeHeight);
        hbox.setStyle("-fx-border-style: dotted; -fx-border-width: 1 0 0 0; -fx-font-weight:bold");
        hbox.getChildren().addAll(newGame, stopOrResumeGame);
        hbox.setAlignment(Pos.CENTER);
        
        modifyButton(newGame);
        modifyButton(stopOrResumeGame);
        return hbox;
    }

    private void modifyButton(Button btn) {
        btn.setPrefHeight(Double.MAX_VALUE);
        btn.setPrefWidth(borderSizeWidth);
        btn.setFont(new Font("Charcoal", 25));
        setButtonStyle(btn, 0.7);
        
        btn.addEventHandler (MouseEvent.MOUSE_ENTERED, (MouseEvent e) ->{
            btn.setEffect(new DropShadow());
        });
        btn.addEventHandler (MouseEvent.MOUSE_EXITED, (MouseEvent e) ->{
            btn.setEffect(null);
        });
        btn.addEventHandler (MouseEvent.MOUSE_PRESSED, (MouseEvent e) ->{
            setButtonStyle(btn, 0.3);
        });
    }

    public void setButtonStyle(Button btn, double opacity) {
        btn.setStyle("-fx-background-color: rgba(233, 158, 64," + opacity + " ); -fx-background-radius: 10; -fx-background-insets: 5");
    }

    public VBox getLeftPane(String player) {
        boolean myTurn;
        if(player.equals("black")) 
            myTurn = true;
        else
            myTurn = false;
        VBox vbox = new VBox();
        vbox.setStyle("-fx-border-style: dotted; -fx-border-width: 0 1 0 0");

        ImageView imageview = new ImageView(new Image("levi.jpg"));
        // source: https://www.crunchyroll.com/anime-news/2018/10/09-1/danmachi-memoria-freese-x-attack-on-titan-collaboration-coming-soon
        imageview.setFitWidth(borderSizeWidth);
        imageview.setFitHeight(300);
        imageview.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            imageview.setStyle("-fx-image: url(levi.gif);");
        });
        imageview.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            imageview.setStyle("-fx-image: url(levi.jpg);");
        });

        Canvas canvas = new Canvas();
        GraphicsContext gcleft = canvas.getGraphicsContext2D();
        String name = "Levi (black)";
        drawSideCanvas(gcleft, canvas, name, myTurn);
        vbox.getChildren().addAll(imageview, canvas);
        vbox.setAlignment(Pos.TOP_CENTER);
        return vbox;
    }

    public VBox getRightPane(String player) {
        boolean myTurn;
        if(player.equals("white") )
            myTurn = true;
        else
            myTurn = false;
        VBox vbox = new VBox();
        vbox.setStyle("-fx-border-style: dotted; -fx-border-width: 0 0 0 1");

        ImageView imageview = new ImageView(new Image("mikasa.jpg"));
        // source: https://www.crunchyroll.com/anime-news/2018/10/09-1/danmachi-memoria-freese-x-attack-on-titan-collaboration-coming-soon
        imageview.setFitWidth(borderSizeWidth);
        imageview.setFitHeight(300);

        Canvas canvas = new Canvas();
        GraphicsContext gcleft = canvas.getGraphicsContext2D();
        String name = "Mikasa (white)";
        drawSideCanvas(gcleft, canvas, name, myTurn);
        vbox.getChildren().addAll(imageview, canvas);
        vbox.setAlignment(Pos.TOP_CENTER);

        return vbox;
    }

    public Pane getCenterPane() {
        // Create a wrapper Pane first
        Pane wrapperPane = new Pane();
        // Put canvas in the center of the window
        canvas = new Canvas((WLIMIT + 1) * cellSize, (HLIMIT + 1) * cellSize);
        gcCenter = canvas.getGraphicsContext2D();
        wrapperPane.getChildren().add(canvas);
        return wrapperPane;
    }

    public void drawRect(int x, int y) {
        gcCenter.strokeRoundRect(8 + x * cellSize, 8 + y * cellSize, cellSize * 2 / 3, cellSize * 2 / 3, 10, 10);
    }

    private void drawSideCanvas(GraphicsContext gc, Canvas canvas, String name, boolean myTurn) {
        int canvasHeight = 300;
        canvas.setWidth(borderSizeWidth);
        canvas.setHeight(canvasHeight);
        gc.clearRect(0, 0, borderSizeWidth, canvasHeight);
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setLineWidth(0.5);
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(16));
        gc.fillText(name, 50, canvasHeight / 5);
        gc.fillText("Total Match: 30", 50, canvasHeight * 2 / 5);
        gc.fillText("Win Rate: 52%", 50, canvasHeight * 3 / 5);
        gc.strokeText(name, 50, canvasHeight / 5);
        gc.strokeText("Total Match: 30", 50, canvasHeight * 2 / 5);
        gc.strokeText("Win Rate: 52%", 50, canvasHeight * 3 / 5);
        if(myTurn){
            gc.setFont(new Font(20));
            gc.fillText("It's My Turn!", 50, canvasHeight * 4 / 5);
            gc.strokeText("It's My Turn!", 50, canvasHeight * 4 / 5);
        }
    }

    public void gameOverInfo(String s, String color) {
        gcCenter.setTextAlign(TextAlignment.CENTER);
        gcCenter.setFont(new Font("Merienda", 36));
        gcCenter.setFill(Color.rgb(0, 0, 0));
        if(color.equals("white")) {
            gcCenter.setFill(Color.rgb(255, 255, 255));
        }
        gcCenter.strokeText(s, (WLIMIT + 1) * cellSize / 2, borderSizeHeight / 2);
        gcCenter.fillText(s, (WLIMIT + 1) * cellSize / 2, borderSizeHeight / 2);
    }

    public void printPause() {
        gcCenter.setTextAlign(TextAlignment.CENTER);
        gcCenter.setFont(new Font("Merienda", 60));
        gcCenter.setFill(Color.rgb(0, 0, 0, 0.5));
        gcCenter.strokeText("GAME PAUSE", (WLIMIT + 1) * cellSize / 2, HLIMIT * cellSize / 2);
        gcCenter.fillText("GAME PAUSE", (WLIMIT + 1) * cellSize / 2, HLIMIT * cellSize / 2);
    }

    public void resetCenterGrid(char[][] b){
		gcCenter.clearRect(0, 0, (WLIMIT + 1) * cellSize, (HLIMIT + 1) * cellSize);
		drawCenter(b, 0.7);
    }
    
    public void drawCenter(char[][] b, double bgopacity) {
        gcCenter.clearRect(0, 0, (WLIMIT + 1) * cellSize, (HLIMIT + 1) * cellSize);
        gcCenter.setFill(Color.rgb(233, 158, 64, bgopacity));
        gcCenter.fillRect(0, 0, (WLIMIT + 1) * cellSize, (HLIMIT + 1) * cellSize);
		gcCenter.setLineWidth(3);
        gcCenter.setStroke(Color.rgb(0, 0, 0, 0.7));
		gcCenter.strokeRoundRect(halfCellSize, halfCellSize, WLIMIT * cellSize, HLIMIT * cellSize, 0, 0);
		gcCenter.setLineWidth(1);
		drawLines(gcCenter);
		gcCenter.setLineWidth(3);
		for (int x = 0; x <= HLIMIT; x++) {
			for (int y = 0; y <= WLIMIT; y++) {
				char c = b[x][y];
				if (c == 'O') drawWhite(gcCenter, cellSize*y, cellSize*x);
				else if (c == 'X') drawBlack(gcCenter, cellSize*y, cellSize*x);
			}
		}
    }

    private void drawLines(GraphicsContext g) {
        int wLineLength = WLIMIT * cellSize;
        int hLineLength = HLIMIT * cellSize;
        // vertical lines
        for(int i = 1; i < WLIMIT; i++){
            g.strokeLine(i * cellSize + halfCellSize, halfCellSize, i * cellSize + halfCellSize, hLineLength + halfCellSize);
        }
        // horizontal lines
        for(int j = 1; j < HLIMIT; j++){
            g.strokeLine(halfCellSize, j * cellSize + halfCellSize, wLineLength + halfCellSize, j * cellSize + halfCellSize);
        }
	}
    
    private void drawWhite(GraphicsContext g, double x, double y) {
        g.setStroke(Color.rgb(0, 0, 0));
        g.strokeOval(wMargin + x, hMargin + y, cellSize - 2 * wMargin, cellSize - 2 * hMargin);
        g.setFill(Color.rgb(255, 255, 255));
        g.fillOval(wMargin + x, hMargin + y, cellSize - 2 * wMargin, cellSize - 2 * hMargin);
	}

	private void drawBlack(GraphicsContext g, double x, double y) {
        g.setStroke(Color.rgb(0, 0, 0));
        g.strokeOval(wMargin + x, hMargin + y, cellSize - 2 * wMargin, cellSize - 2 * hMargin);
        g.setFill(Color.rgb(0, 0, 0));
        g.fillOval(wMargin + x, hMargin + y, cellSize - 2 * wMargin, cellSize - 2 * hMargin);
    }

    // print a paradigm of board which is extendable
    public void printBoard(char[][] b, int HEIGHT, int WIDTH)
    {
        System.out.printf("\nCurrent board is shown below:\n");
        System.out.printf("    ");
        for(int j = 0; j <= WIDTH; j++){
            System.out.printf("%3d ", j + 1);
        }
        System.out.printf("\n");
        for(int i = 0; i <= HEIGHT; i++){
            System.out.printf("%3c ", 'a' + i);
            for(int j = 0; j <= WIDTH; j++){
                System.out.printf("%3c ", b[i][j]);
            }
            System.out.printf("\n");
        }
    }
}