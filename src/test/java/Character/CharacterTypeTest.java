package Character;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTypeTest {

    @Test
    void enumHasExpectedValues() {
        CharacterType[] values = CharacterType.values();
        assertEquals(4, values.length);
        assertNotNull(CharacterType.GYMBRO);
        assertNotNull(CharacterType.NERD);
        assertNotNull(CharacterType.OTAKU);
        assertNotNull(CharacterType.NORMAL);
    }
}
