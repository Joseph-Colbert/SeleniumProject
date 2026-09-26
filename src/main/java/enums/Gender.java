package enums;

public enum Gender {

    MALE("Male"),
    FEMALE("Female");

    private final String text;

    Gender(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}