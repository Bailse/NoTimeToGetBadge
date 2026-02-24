package Character;

public class Player extends BasePlayer {

    private CharacterType type;

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

        setType(type);
    }

    public CharacterType getType() {
        return type;
    }

    public void setType(CharacterType value) {
        type = value;
    }
}