import com.beigz.chesswinners.WinningListProcessor;
import com.beigz.chesswinners.model.CategoryPrize;
import com.beigz.chesswinners.model.Player;
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

    // Run once, e.g. Database connection, connection pool
    @BeforeClass
    public static void runOnceBeforeClass() {
        System.out.println("@BeforeClass - runOnceBeforeClass");
        ExcelReader reader = new ExcelReader("src/test/resources/test.xlsx");
        try {
            categoryPrizes = reader.readCategoryAndPrizes();
            players = reader.readFinalRankList();
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    // Run once, e.g close connection, cleanup
    @AfterClass
    public static void runOnceAfterClass() {
        System.out.println("@AfterClass - runOnceAfterClass");
    }

    // Should rename to @BeforeTestMethod
    // e.g. Creating an similar object and share for all @Test
    @Before
    public void runBeforeTestMethod() {
        System.out.println("@Before - runBeforeTestMethod");
    }

    // Should rename to @AfterTestMethod
    @After
    public void runAfterTestMethod() {
        System.out.println("@After - runAfterTestMethod");
    }

    @Test
    public void test_mode_1() {
        System.out.println("@Test - test_mode_1");
        WinningListProcessor processor = new WinningListProcessor("Mode 1");

    }

    @Test
    public void test_mode_2() {
        System.out.println("@Test - test_mode_2");
        try {
            WinningListProcessor processor = new WinningListProcessor("Mode 2");
            processor.processWinnersList(categoryPrizes, players);
            writer.write2Excel(players, categoryPrizes);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }
}
