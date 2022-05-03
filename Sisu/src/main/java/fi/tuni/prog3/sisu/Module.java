package fi.tuni.prog3.sisu;

import java.util.ArrayList;

public class Module {
    private final String name;
    private final String code;
    private final String groupId;
    private final ArrayList<Course> courses;

    Module (String name, String code, String groupId, 
            ArrayList<Course> courses) {
        this.name = name;
        this.code = code;
        this.groupId = groupId;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
    
    public String getGroupId() {
        return groupId;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}
