package fi.tuni.prog3.sisu;

public class Course {
    private final String name;
    private final String code;
    private final int credits;
    private final String description;

    Course (String name, String code, int credits, String description) {
        this.name = name;
        this.code = code;
        this.credits = credits;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
