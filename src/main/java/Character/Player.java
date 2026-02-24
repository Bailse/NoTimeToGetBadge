
package Character;

public class Player extends BasePlayer {

    private CharacterType type;
    private String imagePath;

    public Player(
            CharacterType type,
            int stamina,
            int money,
            int education,
            int health,
            double moneyDiscount,
            double educationMultiply,
            int healthDecrease,
            int staminaDecrease
    ) {
        super(
                stamina,
                money,
                education,
                health,
                moneyDiscount,
                educationMultiply,
                healthDecrease,
                staminaDecrease
        );

        this.type = type;
    }

    public CharacterType getType() {
        return type;
    }

    public void setType(CharacterType value) {
        type = value;
    }

    public String getImagePath(){
        return imagePath;
    }

    public void setImagePath(String path){
        imagePath = path;
    }


public void clampAllStats(){
    setStamina(getStamina());
    setMoney(getMoney());
    setEducation(getEducation());
    setHealth(getHealth());
}

}