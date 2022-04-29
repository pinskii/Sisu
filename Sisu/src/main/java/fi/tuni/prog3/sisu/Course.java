package fi.tuni.prog3.sisu;

public class Course {
    private final String name;
    private final String code;
    private final int minCredit;
    private final int maxCredit;

    Course (String name, String code, int minCredit, int maxCredit) {
        this.name = name;
        this.code = code;
        this.minCredit = minCredit;
        this.maxCredit = maxCredit;
    }

    public String getName() {
        return name;
    }

    public int getMinCredit() {
        return minCredit;
    }

    public int getMaxCredit() {
        return maxCredit;
    }

    public String getCode() {
        return code;
    }
}
