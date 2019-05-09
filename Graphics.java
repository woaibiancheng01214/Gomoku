import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.event.EventHandler;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import java.util.concurrent.TimeUnit;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

public class Graphics extends Application{
	private Grid grid = new Grid();
	private GraphicsContext g;
	private Button btn = new Button("reset grid");


	public void start (Stage stage)throws Exception{
		
		Canvas canvas = new Canvas(300,380);
		g = canvas.getGraphicsContext2D();
		Group root = new Group(canvas, btn);

		btn.setLayoutX(120);
		btn.setLayoutY(350);
		btn.setAlignment(Pos.CENTER_LEFT);
		
		Scene scene = new Scene(root);
		printText('X', 7, "player  's turn");
		scene.setOnMousePressed(this::move);
		btn.setOnAction(b -> {
			reset(grid);
		});
		stage.setTitle("Cross");
		stage.setScene(scene);
		draw();
		stage.show();
	}

	private void move(MouseEvent e){
		if(grid.playable() == true){
			int x = (int) e.getSceneX() / 100;
			int y = (int) e.getSceneY() / 100;
			grid.move(x, y);
			draw();
			char c = grid.getTurn();
			if(grid.playable() == false){
				// get previous player and judge if he wins
				char player = (c == 'X' ? 'O' : 'X');
				printText(player, 7, "PLAYER  WIN!");
			}
			else if(grid.Tie() == true){
				printText(' ', 0, "Tie ");
			}
			else{
				printText(c, 7, "player  's turn");
			}
		}
	}
	

	private void reset(Grid grid){
		grid.reset();
		g.clearRect(0, 0, 340, 340);
		draw();
		char c = grid.getTurn();
		printText(c, 7, "player  's turn");
	}

	private void printText(char player, int offset, String mainStr){
		StringBuilder str = new StringBuilder(mainStr);
		str.insert(offset, player);
		g.clearRect(0, 310, 300, 340);
		g.setTextAlign(TextAlignment.CENTER);
		g.setLineWidth(0.9);
		g.strokeText(str.toString(), 150, 325);
	}

	private void draw() {
		g.clearRect(0, 0, 300, 300);
		g.setLineWidth(1);
		g.strokeRoundRect(0, 0, 300, 300, 20, 20);
		g.setLineWidth(1);
		drawLines();
		g.setLineWidth(3);
		for (int x=0; x<3; x++) {
			for (int y=0; y<3; y++) {
				char c = grid.get(x,y);
				if (c == 'O') drawO(100*x, 100*y);
				else if (c == 'X') drawX(100*x, 100*y);
			}
		}
	}

	private void drawLines() {
		g.strokeLine(100.5, 10, 100.5, 290);
		g.strokeLine(200.5, 10, 200.5, 290);
		g.strokeLine(10, 100.5, 290, 100.5);
		g.strokeLine(10, 200.5, 290, 200.5);
	}

	private void drawO(double x, double y) {
		g.strokeOval(12+x, 12+y, 75, 75);
	}

	private void drawX(double x, double y) {
		g.strokeLine(12+x, 12+y, 88+x, 88+y);
		g.strokeLine(12+x, 88+y, 88+x, 12+y);
	}
}