import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;

public class BorderPaneExample extends Application {
    private BorderPane root;
    private final int borderSizeHeight = 60;
    private final int borderSizeWidth = 250;
    private GraphicsContext g;

    private char player = 'X';
    private boolean gameStarted = false;
    private long playerXusedTime = 0;
    private long playerOusedTime = 0;
    private long startTime = 0;
    private long endTime = 0;
    private final int HEIGHT = 14;
    private final int WIDTH = 14;
    private final int cellSize = 40;
    private State state = new State(HEIGHT + 1, WIDTH + 1);
    private Display dis = new Display(HEIGHT, HEIGHT, WIDTH, cellSize);

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new BorderPane();

         // Create a wrapper Pane first
        Pane wrapperPane = new Pane();
        // Put canvas in the center of the window
        Canvas canvas = new Canvas((WIDTH + 1) * cellSize, (HEIGHT + 1) * cellSize);
		g = canvas.getGraphicsContext2D();
        wrapperPane.getChildren().add(canvas);
        state.initBoard();

        root.setTop(getTopLabel());
        root.setBottom(getBottomLabel());
        root.setLeft(getLeftPane());
        root.setRight(getRightPane());
        root.setCenter(wrapperPane);

        canvas.setOnMouseClicked(this::handleMouseEvent);

        dis.draw(g, state.getBoard());
        Scene scene = new Scene(root);

        primaryStage.setTitle("Gomoku");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void handleMouseEvent(MouseEvent e){
        int x = (int) (e.getSceneX() - borderSizeWidth) / cellSize;
        int y = (int) (e.getSceneY() -borderSizeHeight) / cellSize;
        if((state.currentState() == Type.XTurn || state.currentState() == Type.OTurn) && gameStarted){
            // get coordinate and draw to the graphicscontent
            state.setCoor(x, y);
            if(state.checkExistAndOutOfBoard() == CheckInfo.valid){
                state.setChesspiece();
            }
            dis.draw(g, state.getBoard());
            
            // check the game is finish or not and print corresponding info
            state.nextState();
            if(state.currentState() == Type.XTurn)
                player = 'X';
            else if(state.currentState() == Type.OTurn)
                player = 'O';
            else;

            endTime = System.currentTimeMillis();
            if(player == 'O'){
                playerXusedTime += (int) (endTime - startTime) / 1000;
                System.out.println(playerXusedTime);
            }
            if(player == 'X'){
                playerOusedTime += (int) (endTime - startTime) / 1000;
                System.out.println(playerOusedTime);
            }
            startTime = System.currentTimeMillis();
            if(state.currentState() == Type.XTurn || state.currentState() == Type.OTurn){
                root.setLeft(getLeftPane());
                root.setRight(getRightPane());
            }
            else if(state.currentState() == Type.draw){
                String s = "Draw";
                gameOverInfo(s);
            }
            else{
                String s = "Player " + player + " Win";
                gameOverInfo(s);
            }
        }
    }
    
    private void gameOverInfo(String s) {
        g.setTextAlign(TextAlignment.CENTER);
        g.setFont(new Font("Merienda", 40));
        g.strokeText(s, WIDTH * (cellSize + 1) / 2, HEIGHT * cellSize * 9 / 10);
        g.fillText(s, WIDTH * (cellSize + 1) / 2, HEIGHT * cellSize * 9 / 10);
        System.out.println("executed here");

    }
    private Label getTopLabel() {
        Label lbl = new Label("GOMOKU");
        lbl.prefWidthProperty().bind(root.widthProperty());
        lbl.setPrefHeight(borderSizeHeight);
        lbl.setStyle("-fx-border-style: dotted; -fx-border-width: 0 0 1 0; -fx-font-weight:bold");
        lbl.setFont(new Font("Kalam", 30));
        lbl.setAlignment(Pos.BASELINE_CENTER);
        
        return lbl;
    }

    private HBox getBottomLabel() {
        Button newGame = new Button("New Game");
        Button stopGame = new Button("Stop Game");
        Button surrender = new Button("Surrender");

        HBox hbox = new HBox();
        hbox.prefWidthProperty().bind(root.widthProperty());
        hbox.setPrefHeight(borderSizeHeight);
        hbox.setStyle("-fx-border-style: dotted; -fx-border-width: 1 0 0 0; -fx-font-weight:bold");
        hbox.getChildren().addAll(newGame, stopGame, surrender);
        hbox.setMargin(newGame, new Insets(0, 10, 0, 10));
        hbox.setMargin(stopGame, new Insets(0, 10, 0, 10));
        hbox.setMargin(surrender, new Insets(0, 10, 0, 10));
        hbox.setAlignment(Pos.CENTER);

        newGame.setOnAction(b -> {
            gameStarted = true;
            startTime = System.currentTimeMillis();
            state.initBoard();
            dis.clearGrid(state.getBoard(), g);
            state.state = Type.XTurn;
            player = 'X';
            root.setLeft(getLeftPane());
            root.setRight(getRightPane());
        });
        stopGame.setOnAction(b -> {
            gameStarted = false;
        });
        return hbox;
    }

    private Label getLeftLabel() {
        Label lbl = new Label("Left Label");
        lbl.setPrefWidth(borderSizeWidth);
        lbl.prefHeightProperty().bind(root.heightProperty().subtract(borderSizeHeight * 2));
        lbl.setStyle("-fx-border-style: dotted; -fx-border-width: 0 1 0 0; -fx-font-weight:bold");
        lbl.setAlignment(Pos.BASELINE_CENTER);
        
        return lbl;
    }

    private VBox getLeftPane() {
        boolean myTurn;
        if(player == 'X') 
            myTurn = true;
        else
            myTurn = false;
        VBox vbox = new VBox();
        vbox.setStyle("-fx-border-style: dotted; -fx-border-width: 0 1 0 0");

        ImageView imageview = new ImageView(new Image("levi.jpg"));
        // source: https://www.crunchyroll.com/anime-news/2018/10/09-1/danmachi-memoria-freese-x-attack-on-titan-collaboration-coming-soon
        imageview.setFitWidth(borderSizeWidth);
        imageview.setFitHeight(300);

        Canvas canvas = new Canvas();
        GraphicsContext gcleft = canvas.getGraphicsContext2D();
        String name = "Levi (black)";
        drawCanvas(gcleft, canvas, name, myTurn);
        vbox.getChildren().addAll(imageview, canvas);
        vbox.setAlignment(Pos.TOP_CENTER);
        return vbox;
    }

    private VBox getRightPane() {
        boolean myTurn;
        if(player == 'O') 
            myTurn = true;
        else
            myTurn = false;
        VBox vbox = new VBox();
        vbox.setStyle("-fx-border-style: dotted; -fx-border-width: 0 0 0 1");

        ImageView imageview = new ImageView(new Image("mikasa.jpg"));
        imageview.setFitWidth(borderSizeWidth);
        imageview.setFitHeight(300);

        Canvas canvas = new Canvas();
        GraphicsContext gcleft = canvas.getGraphicsContext2D();
        String name = "Mikasa (white)";
        drawCanvas(gcleft, canvas, name, myTurn);
        vbox.getChildren().addAll(imageview, canvas);
        vbox.setAlignment(Pos.TOP_CENTER);

        return vbox;
    }

    private void drawCanvas(GraphicsContext gc, Canvas canvas, String name, boolean myTurn) {
        int canvasHeight = 200;
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
        gc.fillText("Total Time: 10:00", 50, canvasHeight * 2 / 5);
        gc.fillText("Time   Left: 09:10", 50, canvasHeight * 3 / 5);
        gc.strokeText(name, 50, canvasHeight / 5);
        gc.strokeText("Total Time: 10:00", 50, canvasHeight * 2 / 5);
        gc.strokeText("Time   Left: 09:10", 50, canvasHeight * 3 / 5);
        if(myTurn){
            gc.setFont(new Font(20));
            gc.fillText("It's My Turn!", 50, canvasHeight * 4 / 5);
            gc.strokeText("It's My Turn!", 50, canvasHeight * 4 / 5);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}