import com.beigz.chesswinners.WinningListProcessor;
import com.beigz.chesswinners.model.CategoryPrize;
import com.beigz.chesswinners.model.Player;
import com.beigz.chesswinners.util.AppUtil;
import com.beigz.chesswinners.util.ExcelReader;
import com.beigz.chesswinners.util.ExcelWriter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zaheer Beig on 21/10/2018.
 */
public class TestWinningListProcessor {
    static List<CategoryPrize> categoryPrizes = new ArrayList<>();
    static List<Player> players = new ArrayList<>();
    static ExcelWriter writer = new ExcelWriter();
    static String title = new String();

    // Run once, e.g. Database connection, connection pool
    @BeforeClass
    public static void runOnceBeforeClass() {
        AppUtil.log("@BeforeClass - runOnceBeforeClass");
        ExcelReader reader = new ExcelReader("src/test/resources/test.xlsx");
        try {
            categoryPrizes = reader.readCategoryAndPrizes();
            players = reader.readFinalRankList();
            title = reader.readTournamentTitle();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // Run once, e.g close connection, cleanup
    @AfterClass
    public static void runOnceAfterClass() {
        AppUtil.log("@AfterClass - runOnceAfterClass");
    }

    // Should rename to @BeforeTestMethod
    // e.g. Creating an similar object and share for all @Test
    @Before
    public void runBeforeTestMethod() {
        AppUtil.log("@Before - runBeforeTestMethod");
    }

    // Should rename to @AfterTestMethod
    @After
    public void runAfterTestMethod() {
        AppUtil.log("@After - runAfterTestMethod");
    }

    @Test
    public void test_mode_1() {
        AppUtil.log("@Test - test_mode_1");
        try {
            WinningListProcessor processor = new WinningListProcessor("Mode 1");
            processor.processWinnersList(categoryPrizes, players);
            writer.write2Excel(title, players, categoryPrizes);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AppUtil.log("Done");
    }

    @Test
    public void test_mode_2() {
        AppUtil.log("@Test - test_mode_2");
        try {
            WinningListProcessor processor = new WinningListProcessor("Mode 2");
            processor.processWinnersList(categoryPrizes, players);
            writer.write2Excel(title, players, categoryPrizes);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AppUtil.log("Done");
    }
}
