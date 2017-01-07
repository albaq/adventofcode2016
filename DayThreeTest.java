import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by alvaro on 1/7/17.
 */
public class DayThreeTest {
    private DayThree dayThree;
    @Before
    public void setUp() throws Exception {
        dayThree = new DayThree();

    }

    @Test
    public void quantityOfVerticalValidTriangles() throws Exception {
        List<String> actual = Arrays.asList(
                "101 301 501",
                "102 302 502",
                "103 303 503",
                "201 401 601",
                "202 402 602",
                "203 403 603");
        assertEquals(6, dayThree.quantityOfVerticalValidTriangles(actual));

    }

    @Test
    public void quantityOfHorizontalValidTriangles() throws Exception {
        List<String> actual = Arrays.asList(
                "101 301 501",
                "102 302 502",
                "103 303 503",
                "201 401 601",
                "202 402 602",
                "203 403 603");
        assertEquals(3, dayThree.quantityOfHorizontalValidTriangles(actual));

    }

    @Test
    public void quantityOfHorizontalValidTriangles_whenLineIsBlank() throws Exception {
        List<String> actual = Arrays.asList(
                "101 301 501",
                "",
                "103 303 503",
                "201 401 601",
                "202 402 602",
                "203 403 603");
        assertEquals(3, dayThree.quantityOfHorizontalValidTriangles(actual));

    }
}