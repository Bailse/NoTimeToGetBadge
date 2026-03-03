package Item;
/**
 * this Class initialize HealthThing like WheyProtein
 */
public class HealthThing extends BaseItem {
    /**
     * constructor that initialize HealthThing
     */
    public HealthThing(String name, String imagePath) {
        super(name, imagePath, Category.HEALTH);
    }

}
