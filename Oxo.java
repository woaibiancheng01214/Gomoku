import javafx.scene.canvas.Canvas;
import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.MouseEvent;

public class Oxo extends Application{
    private final int HEIGHT = 14;
    private final int WIDTH = 14;
    private final int cellSize = 36;
    private State state = new State(HEIGHT, WIDTH);
    private Display dis = new Display(HEIGHT, HEIGHT, WIDTH, cellSize);
    private char player = 'X';
    
    JFXPanel fxPanel = new JFXPanel();
    GraphicsContext g;
    Button btn = new Button();

    public static void main(String args[]){
        Oxo program = new Oxo();
        program.run();
    }

    private void run(){
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception{
        Canvas canvas = new Canvas(HEIGHT * cellSize, HEIGHT * cellSize + 80);
		g = canvas.getGraphicsContext2D();
        Group root = new Group(canvas, btn);
        Scene scene = new Scene(root);

        state.initBoard();
        btn.setLayoutX(WIDTH * 22);
		btn.setLayoutY(HEIGHT * cellSize + 40);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setText("reset grid");
        
        // set two listener to listen to the mouse click and button click
		scene.setOnMousePressed(this::handleMouseEvent);
        btn.setOnAction(b -> {
            state.initBoard();
            dis.resetGrid(state.getBoard(), g);
            state.state = Type.XTurn;
        });
        
		stage.setTitle("Gomoku");
        stage.setScene(scene);
        dis.draw(g, state.getBoard());
        dis.printText(g, 'X', 7, "player  's turn");
        stage.show();
    }

    private void handleMouseEvent(MouseEvent e){
        if(state.currentState() == Type.XTurn || state.currentState() == Type.OTurn){
            int x = (int) e.getSceneX() / cellSize;
            int y = (int) e.getSceneY() / cellSize;
            
            // get coordinate and draw to the graphicscontent
            state.setCoor(x, y);
            if(state.checkExistAndOutOfBoard() == CheckInfo.valid){
                state.setChesspiece();
            }
            dis.draw(g, state.getBoard());
            dis.printBoard(state.getBoard(), HEIGHT, WIDTH);
            
            // check the game is finish or not and print corresponding info
            state.nextState();
            if(state.currentState() == Type.XTurn)
                player = 'X';
            else if(state.currentState() == Type.OTurn)
                player = 'O';
            else;

            if(state.currentState() == Type.XTurn || state.currentState() == Type.OTurn){
                dis.printText(g, player, 7, "player  's turn");
            }
            else if(state.currentState() == Type.draw){
                dis.printText(g, ' ', 0, "Draw");
            }
            else{
                dis.printText(g, player, 7, "PLAYER  WIN!");
            }
        }
    }
}