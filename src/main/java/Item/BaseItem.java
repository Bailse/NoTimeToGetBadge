package Item;
import jdk.jfr.Category;

/**
 * BaseItem for other item to extends this class
 */

public class BaseItem {
    private String name;
    private String image;
    private boolean active;
    private Category category;

    /**
     * constructor that initialize value for everyItem by using BaseItem
     */

    public BaseItem(String name, String image, Category category) {
        setName(name);
        setImage(image);
        setCategory(category);
        setActive(false);
    }

    /**
     * get item name
     */

    public String getName() { return name; }

    /**
     * set item name
     */

    public void setName(String newName) { name = newName; }

    /**
     * get image item
     */
    public String getImage() { return image; }
    /**
     * set image item
     */
    public void setImage(String newImg) { image = newImg; }

    /**
     * check is item is active or not
     */
    public boolean isActive() { return active; }
    /**
     * set this item is active
     */
    public void setActive(boolean newState) { active = newState; }
    /**
     * get item Category
     */
    public Category getCategory() { return category; }
    /**
     * set item Category
     */
    public void setCategory(Category category) {
        this.category = category;
    }
}