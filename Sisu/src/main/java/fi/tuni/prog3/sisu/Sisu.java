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
import javafx.scene.text.Font;
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
    
    static Map<String, Course> allStuff = new HashMap<>();

    @Override
    public void start(Stage stage) throws FileNotFoundException {

        stage.setTitle("Sisu");

        // Asetussivu
        GridPane grid = new GridPane();
        Scene firstScene = new Scene(grid, 640, 480);
        stage.setScene(firstScene);

        Text scenetitle = new Text("Opiskelijan tiedot:");
        scenetitle.setFont(Font.font ("arial", 14));
        grid.add(scenetitle, 0, 0, 2, 1);

        Text emptytitle = new Text();
        grid.add(emptytitle, 0, 1, 2, 1);

        Label nameLabel = new Label("Nimi: ");
        nameLabel.setFont(Font.font ("arial", 14));
        grid.add(nameLabel, 0, 2);

        Label numberLabel = new Label("Opiskelijanumero: ");
        numberLabel.setFont(Font.font ("arial", 14));
        grid.add(numberLabel, 0, 3);

        TextField inputName = new TextField();
        grid.add(inputName, 1, 2);

        TextField inputNum = new TextField();
        grid.add(inputNum, 1, 3);

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BASELINE_RIGHT);
        grid.add(hbox, 1, 4);
        
        Button btn = new Button("Seuraava");
        btn.setFont(Font.font ("arial", 14));
        hbox.getChildren().add(btn);

        Button exitBtn = new Button("Eiku");
        exitBtn.setFont(Font.font ("arial", 14));
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
                if(inputName.getText().length() == 0 || 
                        inputNum.getText().length() == 0) {
                    errorLabel.setText("Tietoja puuttuu!");
                }
                else {  
                    stage.setScene(mainScene);
                }
            }
        });
        
        Text mainscenetitle = new Text("Tutkintorakenne:");
        mainscenetitle.setFont(Font.font ("arial", 14));
        grid1.add(mainscenetitle, 0, 0, 2, 1);

        Map<String, Course> courses = readCoursesFromJsons(courseFileNames);
        Map<String, Module> modules = readModulesFromJsons(moduleFileNames);
               
        for(var trolli : modules.values()) {
            addCoursesAndModulesUnderUnits(trolli, moduleFileNames);
        }
        
        int i = 1;
        int j = 2;
        
        for (var module : modules.values()) {
            ArrayList<Course> moduleCourses = module.getCourses();
            Text moduleinfo = new Text(module.getName());
            moduleinfo.setFont(Font.font ("arial", 14));
            grid1.add(moduleinfo, 0, i, 2, 1);
            
            i++;
            j++;
            
            for(var course : moduleCourses) {
                if (course != null) {
                    
                Text courseinfo = new Text("   " + course.getCode() 
                        + " " + course.getName() 
                        + " " + course.getMaxCredit());
                courseinfo.setFont(Font.font ("arial", 14));
                grid1.add(courseinfo, 0, j, 2, 1);
                i+=10;
                j+=10;
            }
            }
        }
        
        exitBtn.setOnAction((event) -> {stage.close();});
        stage.setOnCloseRequest((event) -> { Platform.exit();});


        stage.show();
    }
    
    private static Course readCourseValues(String jsonFile) 
            throws FileNotFoundException {
        JsonObject root = JsonParser.parseReader
        (new FileReader("json/courseunits/"+jsonFile))
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
        

        return newCourse;
    }
    
    public static Map<String, Course> readCoursesFromJsons(List<String> files) 
        throws FileNotFoundException {
        Map<String, Course> courseMap = new HashMap<>();
        ArrayList<Course> moduleCourses = new ArrayList<>();
        
        for(String file : files) {
            Course course = readCourseValues(file);
            courseMap.put(course.getGroupId(), course);
            allStuff.put(course.getGroupId(), course);
        }
        for (var course : allStuff.values()) {
            System.out.print(course);
        }
        return courseMap;
    }
    
    // luetaan ensin kaikki moduulit
    private static Module readModuleValues(String jsonFile) 
            throws FileNotFoundException {
        ArrayList<Course> moduleCourses = new ArrayList<>();
        JsonObject root = JsonParser.parseReader
        (new FileReader("json/modules/"+jsonFile))
                .getAsJsonObject();
           
        JsonObject nameObj = root.getAsJsonObject("name");
        String name = nameObj.getAsJsonPrimitive("fi").getAsString();
            
        String code = "";
            
        if (!(root.get("code").isJsonNull())) {
            code = root.getAsJsonPrimitive("code").getAsString();
        }
            
        String groupId = root.getAsJsonPrimitive("groupId").getAsString();

        Module newModule = new Module(name, code, groupId, moduleCourses);
        
        return newModule;
    }
    
    //lisätään kurssit ja moduulit aiemmin luettujen moduulien alle
    public static void addCoursesAndModulesUnderUnits(Module module, 
            List<String> jsonFiles) 
            throws FileNotFoundException {
        
        ArrayList<Course> moduleCourses = module.getCourses();
        
        for (var jsonFile : jsonFiles) {
            JsonObject root = JsonParser.parseReader
        (new FileReader("json/modules/"+jsonFile))
                    .getAsJsonObject();

            String type = root.getAsJsonPrimitive("type").getAsString();
            
            // jokaiselle erilaiselle moduulille oma toteutus
            // tiedostojen polkujen ensimmäinen alkio          
            JsonObject rule = root.getAsJsonObject("rule");

            // polku rule->rules
            JsonArray units = rule.getAsJsonArray("rules");
            if(units != null){
            for (JsonElement unit : units) {
                JsonObject unitObj = unit.getAsJsonObject();

                String ruleType = unitObj.getAsJsonPrimitive("type").getAsString();

                if(ruleType.equals("CourseUnitRule")) {
                    String courseUnitGroupId = unitObj.
                            getAsJsonPrimitive("courseUnitGroupId")
                        .getAsString();

                    for (String course : allStuff.keySet()) {
                        if (course.equals(courseUnitGroupId)) {
                            moduleCourses.add(allStuff.get(courseUnitGroupId));
                            
                        }
                    }  
                } 
                if (ruleType.equals("ModuleRule")) {
                    String moduleGroupId = unitObj.
                            getAsJsonPrimitive("moduleGroupId")
                        .getAsString();


                    for (String course : allStuff.keySet()) {
                        if (course.equals(moduleGroupId)) {
                            moduleCourses.add(allStuff.get(course));
                            
                        }
                    }   
                }
                
                // polku rule->rules->rules
                if(unitObj.has("rules")) {
                    JsonArray deeperUnits = unitObj.getAsJsonArray("rules");

                    for(JsonElement deeperUnit : deeperUnits) {
                        JsonObject deeperUnitObj = deeperUnit.getAsJsonObject();

                        String deeperRuleType = unitObj
                                .getAsJsonPrimitive("type").getAsString();

                        if(deeperRuleType.equals("CourseUnitRule")) {
                            String courseUnitGroupId = deeperUnitObj
                                    .getAsJsonPrimitive("courseUnitGroupId")
                                .getAsString();


                            for (String course : allStuff.keySet()) {
                                if (course.equals(courseUnitGroupId)) {
                                    moduleCourses.add(allStuff.
                                            get(courseUnitGroupId));

                                }
                            }  
                        }    

                    }
                }
            }     
            }
            // polku rule->rule
            JsonObject units2 = rule.getAsJsonObject("rule");
            
            if(units2 != null){
            
            // polku rule->rule->rules
            if(units2.has("rules")) {
                JsonArray deeperUnits2 = units2.getAsJsonArray("rules");
                
                for(JsonElement deeperUnit2 : deeperUnits2) {
                    JsonObject deeperUnitObj2 = deeperUnit2.getAsJsonObject();

                    String deeperRuleType2 = deeperUnitObj2.
                            getAsJsonPrimitive("type").getAsString();
                
                    if(deeperRuleType2.equals("ModuleRule")) {
                        String moduleGroupId2 = deeperUnitObj2.
                                getAsJsonPrimitive("moduleGroupId")
                            .getAsString();

                        for (String course : allStuff.keySet()) {
                            if (course.equals(moduleGroupId2)) {
                                moduleCourses.add(allStuff.get(course));  
                            }
                        }   
                    }    
                }
            }
        }
            // tutkinto-ohjelma
            if(type.equals("DegreeProgramme")) {
                JsonObject dpRule = rule.getAsJsonObject("rule");
                JsonArray dpRulee = dpRule.getAsJsonArray("rules");

                for(JsonElement oneDpRule : dpRulee) {
                    JsonObject dpObj = oneDpRule.getAsJsonObject();
                    JsonArray dpRules = dpObj.getAsJsonArray("rules");

                    if(!dpRules.isEmpty()) {
                        for(JsonElement dpUnit : dpRules) {
                            JsonObject dpUnitObj = dpUnit.getAsJsonObject();

                            String dpModuleGroupId = 
                                    dpUnitObj.getAsJsonPrimitive("moduleGroupId")
                                            .getAsString();

                            for (String course : allStuff.keySet()) {
                                if (course.equals(dpModuleGroupId)) {
                                moduleCourses.add(allStuff.get(dpModuleGroupId));
                           
                                }
                            }  
                        }   
                    }
                } 
            }
        }
    }
    
    public static Map<String, Module> readModulesFromJsons(List<String> files) 
            throws FileNotFoundException {
        
        Map<String, Module> moduleMap = new HashMap<>();
        
        for(String file : files) {
            Module module = readModuleValues(file);
            Course course = new Course(module.getName(), module.getCode(), 0, 0, 
                    module.getGroupId());
            moduleMap.put(module.getGroupId(), module);
            allStuff.put(course.getGroupId(), course);
        }
        return moduleMap;
        
    }

    public static void main(String[] args) throws FileNotFoundException {
        launch();
    }

}