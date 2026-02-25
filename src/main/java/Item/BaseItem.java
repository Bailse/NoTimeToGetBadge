package Item;
import jdk.jfr.Category;

public class BaseItem {
    private String name;
    private String image;
    private boolean active;
    private Category category;

    public BaseItem(String name, String image, Category category) {
        setName(name);
        setImage(image);
        setCategory(category);
        setActive(false);
    }

    // Getter/Setter (ไม่ใช้ this ตามกฎเดิม)
    public String getName() { return name; }
    public void setName(String newName) { name = newName; }

    public String getImage() { return image; }
    public void setImage(String newImg) { image = newImg; }

    public boolean isActive() { return active; }
    public void setActive(boolean newState) { active = newState; }

    public Category getCategory() { return category; }

    public void setCategory(Category category) {
        this.category = category;
    }
}