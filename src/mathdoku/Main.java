package mathdoku;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class Main extends Application {

    private  GridPane grid;
    private Pane pane;
    private Node[][] gridPaneNodes;
    private boolean isCorrect = true;
    @Override
    public void start(Stage primaryStage) throws Exception{
        //the root pane will be a BorderPane
        //Initializing the root
        BorderPane root = new BorderPane();

        Button quit = new Button("Quit Game"); //exit button
        setQuitEvent(quit);
        VBox right = new VBox();
        HBox top = new HBox(); //top side of the root

        Label beginQuestion = new Label("Choose between 6x6 and 8x8");
        TextField choice = new TextField();
        Button create = new Button("Create Grid");
        createGrid(create,choice,root);

        top.setAlignment(Pos.BASELINE_CENTER); //sets the alignment
        top.getChildren().addAll(beginQuestion,choice,create);

        //bottom child of the root will be an HBox containing all the buttons for actions
        HBox bottom = new HBox(); // created and instantiated the HBox

        Button undo = new Button("Undo"); //undo button
        Button clear = new Button("Clear"); //clear button
        Button loadFile  = new Button("Load File"); //loading a file button
        Button loadGame = new Button("Load Game"); //loading a game button
        Button correct = new Button("Correct"); //shows mistakes button
        TextArea check = new TextArea();
        check.setPrefSize(50,50);
        bottom.setAlignment(Pos.BASELINE_CENTER); //sets the alignment of the HBox
        checkGrid(correct,check);
        bottom.getChildren().addAll(undo,loadFile,loadGame);//adds all the buttons to the pane

        right.getChildren().addAll(correct,clear,check);
        root.setRight(right);
        root.setBottom(bottom); //assigns the HBox to the bottom of the root
        root.setLeft(quit); //adds child to left side of the root
        root.setTop(top); //adds child to top side of the root

        Scene scene = new Scene(root,600,600); //creates a scene with the existing root

        primaryStage.setScene(scene); //assigns the scene to the stage
        primaryStage.setTitle("MathDoku"); //sets the title of the stage

        primaryStage.show();
    }

    //terminates the application
    public void setQuitEvent(Button button){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });
    }

    //creates the grid
    public void createGrid(Button button, TextField tf, BorderPane bpane){

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int n = Integer.parseInt(tf.getText());
                pane = new Pane();
                grid = new GridPane();
                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        TextArea ta = new TextArea();
                        ta.setPrefSize(50,50);
                        grid.add(ta, i, j ,1, 1);
                    }
                }
                pane.getChildren().add(grid);

                bpane.setCenter(pane);
            }
        });
    }

    public void gridMatrice(GridPane gridPane){
        gridPaneNodes = new Node[gridPane.getRowCount()][gridPane.getColumnCount()] ;
        for (Node child : gridPane.getChildren()) {
            int column = GridPane.getColumnIndex(child);
            int row = GridPane.getRowIndex(child);
            gridPaneNodes[row][column] = child;

        }
    }

    public void checkGrid(Button button, TextArea textArea){

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gridMatrice(grid);
                int row = grid.getRowCount();
                int col = grid.getColumnCount();

                int value;
                int i  = 0;
                int j = 0;
                for(i = 0 ; i < row; i++){
                    value = Integer.parseInt(gridPaneNodes[i][j].getId());
                    for(j = i + 1; j < col; j++){
                        if(value == Integer.parseInt(gridPaneNodes[i][j].getAccessibleText())){
                            isCorrect = false;
                        }
                    }
                }
                if(isCorrect){
                    textArea.setText("CORRECT");
                }
                else {
                    textArea.setText("WRONG");
                }
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
