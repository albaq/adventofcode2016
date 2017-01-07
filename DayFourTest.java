import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 1/7/17.
 */
public class DayFourTest {
    private DayFour dayFour;
    @Before
    public void setUp() throws Exception {
        String line="aaaaa-bbb-z-y-x-123[abxyz]";
        dayFour = new DayFour(line);
    }

    @Test
    public void getGivenChecksum() {
        String expected = "abxyz";

        Optional<String> answer = this.dayFour.getGivenChecksum();

        assertTrue(answer.isPresent());
        assertEquals(expected, answer.get());
    }

    @Test
    public void extractGivenCode() {
        Integer expected = 123;

        Optional<Integer> answer = this.dayFour.extractCode();

        assertTrue(answer.isPresent());
        assertEquals(expected, answer.get());
    }

    @Test
    public void extractGivenCode_whenNoCodeExists() {
        DayFour aux = new DayFour("aaaaa-bbb-z-y-x");

        Optional<Integer> answer = aux.extractCode();

        assertFalse(answer.isPresent());
    }

    @Test
    public void getGivenChecksum_whenNoChecksumGiven() {
        DayFour aux = new DayFour("aaaaa-bbb-z-y-x-123");

        Optional<String> answer = aux.getGivenChecksum();

        assertFalse(answer.isPresent());
    }

    @Test
    public void computeLineChecksum() {
        String expected = "abxyz";

        assertEquals(expected, this.dayFour.computeChecksum());
    }

    @Test
    public void removeGivenChecksum() {
        String expected = "aaaaa-bbb-z-y-x-123";

        assertEquals(expected, this.dayFour.removeGivenChecksum());
    }

    @Test
    public void removeDashes() {
        String expected = "aaaaabbbzyx123";

        assertEquals(expected, this.dayFour.removeDashes("aaaaa-bbb-z-y-x-123"));
    }


}