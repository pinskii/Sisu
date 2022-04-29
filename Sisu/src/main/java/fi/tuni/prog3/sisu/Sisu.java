package fi.tuni.prog3.sisu;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
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

        Label name = new Label("Nimi: ");
        grid.add(name, 0, 2);

        Label number = new Label("Opiskelijanumero: ");
        grid.add(number, 0, 3);

        TextField inputName = new TextField();
        grid.add(inputName, 1, 2);

        TextField inputNum = new TextField();
        grid.add(inputNum, 1, 3);

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BASELINE_RIGHT);
        grid.add(hbBtn, 1, 4);

        Button btn = new Button("Seuraava");
        hbBtn.getChildren().add(btn);

        Button exitBtn = new Button("Eiku");
        hbBtn.getChildren().add(exitBtn);

        Label error = new Label();
        grid.add(error,0,5,2,1);

        stage.setScene(firstScene);

        // Päänäkymä
        GridPane grid1 = new GridPane();
        Scene mainScene = new Scene(grid1,640, 480);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if(inputName.getText().length() == 0 || inputNum.getText().length() == 0) {
                    error.setText("Tietoja puuttuu!");
                    error.setTextFill(Color.color(1, 0, 0));
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
    
    private static Course readCourseValues(String jsonFile) 
            throws FileNotFoundException {
        
        JsonObject root = JsonParser.parseReader(new FileReader(jsonFile)).getAsJsonObject();
        String code = root.getAsJsonObject("code").getAsString();
        String name = root.getAsJsonObject("name").getAsJsonObject("fi").getAsString();
        int minCredit = root.getAsJsonObject("credits").getAsJsonObject("min").getAsInt();
        int maxCredit = root.getAsJsonObject("credits").getAsJsonObject("max").getAsInt();
        Course newCourse = new Course(name, code, minCredit, maxCredit);
        System.out.print(newCourse.getName());
        System.out.print(newCourse.getCode());
        return newCourse;
    }

    public static void main(String[] args) throws FileNotFoundException {
        launch();
    }

}