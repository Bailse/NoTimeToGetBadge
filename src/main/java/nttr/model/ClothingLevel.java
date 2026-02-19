package nttr.model;

public enum ClothingLevel {
    CASUAL("Casual"),
    SEMI_CASUAL("Semi-Casual"),
    BUSINESS("Business");

    private final String label;

    ClothingLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public ClothingLevel next() {
        if (this == CASUAL) return SEMI_CASUAL;
        if (this == SEMI_CASUAL) return BUSINESS;
        return BUSINESS;
    }
}
