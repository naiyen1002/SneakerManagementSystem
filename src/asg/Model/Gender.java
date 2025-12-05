package asg.Model;

/**
 * Gender Enum
 */
public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Convert string input to Gender enum
     * Handles case insensitivity input
     */
    public static Gender fromString(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Gender cannot be null");
        }
        
        for (Gender gender : Gender.values()) {
            if (gender.displayName.equalsIgnoreCase(text.trim()) || 
                gender.name().equalsIgnoreCase(text.trim())) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid gender: " + text);
    }

}
