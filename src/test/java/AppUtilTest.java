import com.beigz.chesswinners.util.AppUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Zaheer Beig on 14/03/2019.
 */
public class AppUtilTest {

    @Test
    public void testExtractAgeFromType() {
        int ageFromType1 = AppUtil.extractAgeFromType("U07");
        assertEquals(7, ageFromType1);

        int ageFromType2 = AppUtil.extractAgeFromClubType("GOA_U07");
        assertEquals(7, ageFromType2);

    }

    @Test
    public void testCountNewLines() {
        int count = AppUtil.countNewLines("[\u200E06/\u200E03/\u200E2019 13:59]  Beig,Z,Zaheer,TAR7 R:  \n" +
                "Hi Manoj\n" +
                " ");
        assertEquals(2, count);
    }
}
