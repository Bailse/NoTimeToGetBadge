package Item;

public class Thing {

    private String name;
    private String image;

    public Thing(String name, String image) {
        setName(name);
        setImage(image);
    }

    public String getName() {
        return name;
    }

    public void setName(String nameValue) {
        name = nameValue;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String imageValue) {
        image = imageValue;
    }
}
