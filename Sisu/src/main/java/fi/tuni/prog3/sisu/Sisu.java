package fi.tuni.prog3.sisu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Sisu extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("Sisu");

        // Asetussivu
        GridPane grid = new GridPane();
        Scene firstScene = new Scene(grid, 640, 480);
        stage.setScene(firstScene);

        Text scenetitle = new Text("Opiskelijan tiedot:");
        grid.add(scenetitle, 0, 0, 2, 1);

        Text emptytitle = new Text();
        grid.add(emptytitle, 0, 1, 2, 1);

        Label nameLabel = new Label("Nimi: ");
        grid.add(nameLabel, 0, 2);

        Label numberLabel = new Label("Opiskelijanumero: ");
        grid.add(numberLabel, 0, 3);

        TextField inputName = new TextField();
        grid.add(inputName, 1, 2);

        TextField inputNum = new TextField();
        grid.add(inputNum, 1, 3);

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BASELINE_RIGHT);
        grid.add(hbox, 1, 4);

        Button btn = new Button("Seuraava");
        hbox.getChildren().add(btn);

        Button exitBtn = new Button("Eiku");
        hbox.getChildren().add(exitBtn);

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.color(1, 0, 0));
        grid.add(errorLabel,0,5,2,1);

        stage.setScene(firstScene);

        // Päänäkymä
        GridPane grid1 = new GridPane();
        Scene mainScene = new Scene(grid1,640, 480);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if(inputName.getText().length() == 0 || inputNum.getText().length() == 0) {
                    errorLabel.setText("Tietoja puuttuu!");
                }
                else {
                    stage.setScene(mainScene);
                }
            }
        });

        exitBtn.setOnAction((event) -> {stage.close();});
        stage.setOnCloseRequest((event) -> { Platform.exit();});


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}