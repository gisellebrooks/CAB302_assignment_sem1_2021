package marketplace;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class UnitTesting {

    @BeforeEach
    /*
     * Create a fresh database
     */
    public void create() throws Exception {
        //planner = new JourneyPlanner(3);  // <-- change this for different planners
    }

    @Test
    /* A typical case - passed by both versions */
    public void normalCase() {
        //planner.setOrigin(0, 1);
        //planner.setDestination(3, 4);
        //assertEquals(planner.journeyTime(), 18);
    }


}
