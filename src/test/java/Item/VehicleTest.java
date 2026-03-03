package Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test Class for checking Vehicle
 */
public class VehicleTest {

    @Test
    void vehicleProperties() {
        Vehicle v = new Vehicle();
        assertEquals("Vehicle", v.getName());
        assertEquals("Item/Vehicle.png", v.getImage());
        assertEquals(Category.VEHICLE, v.getCategory());
    }
}
