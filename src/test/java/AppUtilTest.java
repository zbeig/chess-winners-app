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
}
