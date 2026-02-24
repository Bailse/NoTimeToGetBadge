package Character;

public class PlayerFactory {

    public static Player create(CharacterType type) {

        int stamina = 100;
        int money = 500;
        int education = 0;
        int health = 100;

        double moneyDiscount = 1.0;
        double educationMultiply = 1.0;
        int healthDecrease = 5;
        int staminaDecrease = 5;

        switch (type) {

            case HUSTLER:
                money = 700;
                staminaDecrease = 4;
                break;

            case GENIUS:
                educationMultiply = 1.25;
                money = 400;
                break;

            case ATHLETE:
                health = 130;
                healthDecrease = 3;
                break;

            case SOCIALITE:
                moneyDiscount = 0.8;
                stamina = 110;
                break;
        }

        return new Player(
                type,
                stamina,
                money,
                education,
                health,
                moneyDiscount,
                educationMultiply,
                healthDecrease,
                staminaDecrease
        );
    }
}