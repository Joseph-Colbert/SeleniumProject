package enums;

public enum MaritalStatus {

    SINGLE("Single"),
    MARRIED("Married"),
    OTHER("Other");

    private final String text;

    MaritalStatus(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}