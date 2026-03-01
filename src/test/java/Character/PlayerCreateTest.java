package Character;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerCreateTest {

    @ParameterizedTest
    @EnumSource(CharacterType.class)
    void createReturnsNonNullForAllEnums(CharacterType type) {
        BasePlayer p = PlayerCreate.create(type);
        assertNotNull(p, "Factory must create a player for type: " + type);
    }

    @Test
    void createReturnsCorrectSubtype() {
        assertInstanceOf(GymBro.class, PlayerCreate.create(CharacterType.GYMBRO));
        assertInstanceOf(Nerd.class, PlayerCreate.create(CharacterType.NERD));
        assertInstanceOf(Otaku.class, PlayerCreate.create(CharacterType.OTAKU));
        assertInstanceOf(NormalGuy.class, PlayerCreate.create(CharacterType.NORMAL));
    }

    @Test
    void createWithNullTypeThrows() {
        assertThrows(NullPointerException.class, () -> PlayerCreate.create(null));
    }
}
