// EducationThing.java
package Item;

/**
 * this Class initialize EducationThing like Book
 */
public class EducationThing extends BaseItem {

    /**
     * constructor that initialize EductionThing
     */

    public EducationThing(String name, String imagePath) {
        super(name, imagePath, Category.EDUCATION);
    }
}

