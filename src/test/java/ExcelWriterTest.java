import com.beigz.chesswinners.util.ExcelWriter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Zaheer Beig on 16/03/2019.
 */
public class ExcelWriterTest {

    @Test
    public void testWrite2Excel() throws IOException, InvalidFormatException {
        ExcelWriter writer = new ExcelWriter();
        writer.write2Excel(null, null, null);
    }
}
