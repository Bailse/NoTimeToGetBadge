package Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    @Test
    void vehicleProperties() {
        Vehicle v = new Vehicle();
        assertEquals("Vehicle", v.getName());
        assertEquals("Vehicle.png", v.getImage());
        assertEquals(Category.VEHICLE, v.getCategory());
    }
}
