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
    
    List<String> filenames = Arrays.asList(
            "otm-1dc4fc64-39fd-4575-aef6-280199870f71.json",
            "otm-1e8f1023-1977-4f98-9386-110ef623327b.json",
            "otm-2966b2c5-7c94-491f-8c9d-ed918ce13869.json",
            "otm-2a874c0e-de1b-4292-a5e4-0372397830ec.json",
            "otm-31be29f8-7c34-405a-a5fd-9646fbc4fa47.json",
            "otm-36a14850-e522-4246-8244-87f5f07f0ab9.json",
            "otm-5bb38ecf-584e-40b4-8150-1a42dc8d7473.json",
            "otm-7bbbad0a-097c-4ecd-bdfe-b419bb5a887b.json",
            "otm-9dea0dfa-9475-4f20-8212-9e8c4df718cf.json",
            "otm-ba68eb2e-7db2-4044-be84-4b47d573d1f1.json",
            "otm-bbb0137a-353b-4d70-b398-91c558e084c9.json",
            "otm-bc7d67db-a999-4fa4-9725-7fc8e7b352f4.json",
            "otm-d1e98258-622d-4fbc-9f5e-204a3100f949.json",
            "otm-dd0c337e-b2f0-45d0-9a28-d3bbe287ff64.json",
            "otm-ec562ecd-50e3-4abb-9e50-88102bfcc2de.json");

    @Override
    public void start(Stage stage) throws FileNotFoundException {

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
        
        Text mainscenetitle = new Text("Kurssit:");
        grid1.add(mainscenetitle, 0, 0, 2, 1);

        Map<String, Course> courses = readCoursesFromJsons(filenames);
        
        int i = 1;
        
        for(var course : courses.values()) {
            Text courseinfo = new Text(course.getCode() + " " + course.getName() 
                    + " " + course.getMaxCredit());
            grid1.add(courseinfo, 0, i, 2, 1);
            i++;
        }
        
        exitBtn.setOnAction((event) -> {stage.close();});
        stage.setOnCloseRequest((event) -> { Platform.exit();});


        stage.show();
    }
    
    private static Course readCourseValues(String jsonFile) 
            throws FileNotFoundException {
        
        JsonObject root = JsonParser.parseReader(new FileReader(jsonFile)).getAsJsonObject();
        String code = root.getAsJsonPrimitive("code").getAsString();
        JsonObject nameObj = root.getAsJsonObject("name");
        String name = nameObj.getAsJsonPrimitive("fi").getAsString();
        JsonObject minObj = root.getAsJsonObject("credits");
        JsonObject maxObj = root.getAsJsonObject("credits");
        int minCredit = minObj.getAsJsonPrimitive("min").getAsInt();
        int maxCredit = maxObj.getAsJsonPrimitive("max").getAsInt();
        Course newCourse = new Course(name, code, minCredit, maxCredit);
        System.out.print(newCourse.getName());
        System.out.print(newCourse.getCode());
        return newCourse;
    }
    
    public static Map<String, Course> readCoursesFromJsons(List<String> files) 
        throws FileNotFoundException {
        Map<String, Course> courseMap = new HashMap<>();
        
        for(String file : files) {
            Course course = readCourseValues(file);
            courseMap.put(course.getCode(), course);
        }
        return courseMap;
    }

    public static void main(String[] args) throws FileNotFoundException {
        launch();
    }

}