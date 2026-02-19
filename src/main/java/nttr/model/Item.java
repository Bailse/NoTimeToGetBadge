package nttr.model;

/**
 * Base item type. Each item belongs to exactly one category (slot).
 */
public abstract class Item extends Thing implements BonusProvider {
    private final ItemType type;
    private final String description;

    protected Item(String name, String imagePath, ItemType type, String description) {
        super(name, imagePath);
        this.type = type;
        this.description = description;
    }

    public ItemType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
