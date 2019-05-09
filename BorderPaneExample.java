import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;

public class BorderPaneExample extends Application {
    BorderPane root;
    final int SIZE = 60;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new BorderPane();

        root.setTop(getTopLabel());
        root.setBottom(getBottomLabel());
        root.setLeft(getLeftLabel());
        root.setRight(getRightLabel());
        root.setCenter(getCenterLabel());

        Scene scene = new Scene(root, 500, 580);

        primaryStage.setTitle("Border Pane");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label getTopLabel() {
        Label lbl = new Label("Top Label");
        lbl.prefWidthProperty().bind(root.widthProperty());
        lbl.setPrefHeight(SIZE);
        lbl.setStyle("-fx-border-style: dotted; -fx-border-width: 0 0 1 0; -fx-font-weight:bold");
        lbl.setAlignment(Pos.BASELINE_CENTER);
        
        return lbl;
    }

    private Label getBottomLabel() {
        Label lbl = new Label("Bottom Label");
        lbl.prefWidthProperty().bind(root.widthProperty());
        lbl.setPrefHeight(SIZE);
        lbl.setStyle("-fx-border-style: dotted; -fx-border-width: 1 0 0 0; -fx-font-weight:bold");
        lbl.setAlignment(Pos.BASELINE_CENTER);
        
        return lbl;
    }

    private Label getLeftLabel() {
        Label lbl = new Label("Left Label");
        lbl.setPrefWidth(SIZE);
        lbl.prefHeightProperty().bind(root.heightProperty().subtract(SIZE * 2));
        lbl.setStyle("-fx-border-style: dotted; -fx-border-width: 0 1 0 0; -fx-font-weight:bold");
        lbl.setAlignment(Pos.BASELINE_CENTER);
        
        return lbl;
    }

    private Label getRightLabel() {
        Label lbl = new Label("Right Label");
        lbl.setPrefWidth(SIZE);
        lbl.prefHeightProperty().bind(root.heightProperty().subtract(SIZE * 2));
        lbl.setStyle("-fx-border-style: dotted; -fx-border-width: 0 0 0 1; -fx-font-weight:bold");
        lbl.setAlignment(Pos.BASELINE_CENTER);
        
        return lbl;
    }

    private Label getCenterLabel() {
        Label lbl = new Label("Center Label");
        lbl.setStyle("-fx-font-weight:bold");
        lbl.setAlignment(Pos.BASELINE_CENTER);
        
        return lbl;
    }

    public static void main(String[] args) {
        launch();
    }
}