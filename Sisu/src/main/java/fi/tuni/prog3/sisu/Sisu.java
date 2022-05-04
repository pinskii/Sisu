package fi.tuni.prog3.sisu;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Sisu extends Application {
    
    static List<String> courseFileNames = Arrays.asList(
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
    
    static List<String> moduleFileNames = Arrays.asList(
            "otm-6b575bfa-e488-4ee0-a8d9-877608ce64e9.json",
            "otm-010acb27-0e5a-47d1-89dc-0f19a43a5dca.json",
            "otm-11eeddac-ac46-4b72-973d-928b8cdc922c.json",
            "otm-73c44ab7-259c-4ba3-9247-27597af07443.json",
            "otm-91c6f8f4-5662-46a0-9adc-cb84aac38293.json",
            "otm-3990be25-c9fd-4dae-904c-547ac11e8302.json",
            "otm-4535973c-4aa6-4f3f-b560-38e3cbc3baaf.json",
            "otm-dfcd98b1-4f28-4095-874f-178413c11869.json",
            "otm-f0f2f2fd-6e2f-4975-a4b3-2c46532d34a1.json"
            );

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
        
        Text mainscenetitle = new Text("Tutkintorakenne:");
        grid1.add(mainscenetitle, 0, 0, 2, 1);

        Map<String, Course> courses = readCoursesFromJsons(courseFileNames);
        Map<String, Module> modules = readModulesFromJsons(moduleFileNames);
        
        int i = 1;
        
        for (var module : modules.values()) {
            ArrayList<Course> moduleCourses = module.getCourses();
            Text moduleinfo = new Text(module.getName());
            grid1.add(moduleinfo, 0, i, 2, 1);
            int j = 2;
            for(var course : moduleCourses) {
            
                Text courseinfo = new Text("   " + course.getCode() + " " + course.getName() 
                        + " " + course.getMaxCredit());
                grid1.add(courseinfo, 0, j, 2, 1);
                i++;
                j++;
            }
        }
        
        exitBtn.setOnAction((event) -> {stage.close();});
        stage.setOnCloseRequest((event) -> { Platform.exit();});


        stage.show();
    }
    
    private static Course readCourseValues(String jsonFile) 
            throws FileNotFoundException {
        JsonObject root = JsonParser.parseReader(new FileReader("json/courseunits/"+jsonFile))
                .getAsJsonObject();
        String code = root.getAsJsonPrimitive("code").getAsString();
        JsonObject nameObj = root.getAsJsonObject("name");
        String name = nameObj.getAsJsonPrimitive("fi").getAsString();
        JsonObject minObj = root.getAsJsonObject("credits");
        JsonObject maxObj = root.getAsJsonObject("credits");
        int minCredit = minObj.getAsJsonPrimitive("min").getAsInt();
        int maxCredit = maxObj.getAsJsonPrimitive("max").getAsInt();
        String groupId = root.getAsJsonPrimitive("groupId").getAsString();
        Course newCourse = new Course(name, code, minCredit, maxCredit, groupId);
        System.out.print(newCourse.getName());
        System.out.print(newCourse.getCode());

        return newCourse;
    }
    
    public static Map<String, Course> readCoursesFromJsons(List<String> files) 
        throws FileNotFoundException {
        Map<String, Course> courseMap = new HashMap<>();
        
        for(String file : files) {
            Course course = readCourseValues(file);
            courseMap.put(course.getGroupId(), course);
        }
        return courseMap;
    }
    
    private static Module readModuleValues(String jsonFile) 
            throws FileNotFoundException {
        Map<String, Course> courses = readCoursesFromJsons(courseFileNames);
        ArrayList<Course> moduleCourses = new ArrayList<>();
        JsonObject root = JsonParser.parseReader(new FileReader("json/modules/"+jsonFile))
                .getAsJsonObject();
        String type = root.getAsJsonPrimitive("type").getAsString();
        if (!(type.equals("DegreeProgramme"))) {
            
            JsonObject nameObj = root.getAsJsonObject("name");
            String name = nameObj.getAsJsonPrimitive("fi").getAsString();
            
            String code = "";
            
            if (!(root.get("code").isJsonNull())) {
                code = root.getAsJsonPrimitive("code").getAsString();
            }
            
            
            String groupId = root.getAsJsonPrimitive("groupId").getAsString();
            // jokaiselle erilaiselle moduulille oma toteutus
            // jos yksi taso
            
            JsonObject rule = root.getAsJsonObject("rule");
            JsonArray courseUnitGroupIds = rule.getAsJsonArray("rules");
            // tässä tapahtuu ongelma :D
            if (courseUnitGroupIds != null) {
            for (JsonElement groupID : courseUnitGroupIds) {
                JsonObject groupIDObj = groupID.getAsJsonObject();
                String groupIdString = groupIDObj.getAsJsonPrimitive("courseUnitGroupId")
                        .getAsString();
                for (String course : courses.keySet()) {
                    if (course.equals(groupIdString)) {
                        moduleCourses.add(courses.get(course));
                    }
                }
            }
        }
        
            // jos kaksi tasoa
            // varmaan siis tässäkin
            if (courseUnitGroupIds != null) {
                for (JsonElement rulesInRules : courseUnitGroupIds) {
                    JsonArray rules = rulesInRules.getAsJsonObject()
                    .getAsJsonArray("rules");
                    if (rules != null) {
                        for (JsonElement groupID : rules) {

                            JsonObject groupIDObj = groupID.getAsJsonObject();
                            String groupIDString = groupIDObj.getAsJsonPrimitive("courseUnitGroupId")
                            .getAsString();
                            for (String course : courses.keySet()) {
                                if (course.equals(groupIDString)) {
                                   moduleCourses.add(courses.get(course));
                                }
                            }

                        }
                    }
            
                }
            }
         Module newModule = new Module(name, code, groupId, moduleCourses);
         return newModule;
        }
        
       Module newModule = new Module("name", "code", "groupId", moduleCourses);
       return newModule;
    }
    
    public static Map<String, Module> readModulesFromJsons(List<String> files) throws FileNotFoundException {
        
        Map<String, Module> moduleMap = new HashMap<>();
        
        for(String file : files) {
            Module module = readModuleValues(file);
            moduleMap.put(module.getGroupId(), module);
        }
        return moduleMap;
        
    }

    public static void main(String[] args) throws FileNotFoundException {
        launch();
    }

}