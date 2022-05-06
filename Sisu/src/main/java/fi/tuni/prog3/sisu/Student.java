package fi.tuni.prog3.sisu;

import java.util.ArrayList;

public class Student {
    private String name;
    private String number;
    private String studyProgram;
    private ArrayList<String> courses;

    Student(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setStudyProgram(String studyProgram) {
        this.studyProgram = studyProgram;
    }

    public void addCourse(Course course) {
        courses.add(course.getCode());
    }
}
