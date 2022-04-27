package fi.tuni.prog3.sisu;

import java.util.ArrayList;

public class Orientation {
    private final String name;
    private final int credits;
    private final ArrayList<Course> courses;

    Orientation (String name, int credits, ArrayList<Course> courses) {
        this.name = name;
        this.credits = credits;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}
