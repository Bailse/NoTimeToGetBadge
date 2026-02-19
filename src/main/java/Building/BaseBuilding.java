package Building;

import Character.BasePlayer;

public abstract class BaseBuilding {

    private String name;

    public BaseBuilding(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //interact
    public abstract void interact(BasePlayer character);
}
