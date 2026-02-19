package nttr.model;

/**
 * Base abstract class for any "thing" in the game.
 * Requirement: getters/setters must exist, and setters must not use 'this.'
 */
public abstract class Thing {
    private String name;
    private String imagePath;

    protected Thing(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String newPath) {
        imagePath = newPath;
    }
}
