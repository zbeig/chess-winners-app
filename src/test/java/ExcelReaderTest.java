import com.beigz.chesswinners.util.ExcelReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.fail;

/**
 * Created by Zaheer Beig on 15/03/2019.
 */
public class ExcelReaderTest {

    String testFilePath = "C:\\Users\\609239429\\Desktop\\Harlequinn\\workspace\\chess-winners-app\\src\\test\\resources\\test.xlsx";

    @Test
    public void testReadTournamentTitle() {
        ExcelReader excelReader = new ExcelReader(testFilePath);
        try {
            String title = excelReader.readTournamentTitle();
        } catch (InvalidFormatException e) {
            fail();
        } catch (IOException e) {
            fail();
        } catch (Exception e) {
            fail();
        }

    }
}
