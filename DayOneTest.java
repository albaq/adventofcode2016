import org.junit.Test;
import static org.junit.Assert.*;

public class DayOneTest {

    @Test
    public void whenWalkingRightAndLeft_get_5() throws Exception {
        DayOne d = new DayOne("R2, L3");

        assertEquals(5, d.execute());
    }

    @Test
    public void whenWalkingRightThreeTimes_get_2() throws Exception {
        DayOne d = new DayOne("R2, R2, R2");

        assertEquals(2, d.execute());
    }

    @Test
    public void whenWalkingRightLeftAndRight2Times_get_12() throws Exception {
        DayOne d = new DayOne("R5, L5, R5, R3");

        assertEquals(12, d.execute());
    }

    @Test
    public void whenWalkingRight_IntersectionIs4() throws Exception {
        DayOne d = new DayOne("R8, R4, R4, R8");

        assertEquals(8, d.execute());
        assertTrue(d.isIntersectionFound());
        assertEquals(4, d.getDistanceToIntersection());
    }
}