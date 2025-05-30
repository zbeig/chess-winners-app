package com.beigz.chesswinners.util;

import com.beigz.chesswinners.model.CategoryPrize;
import com.beigz.chesswinners.model.Player;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.beigz.chesswinners.util.AppUtil.isNumber;
import static com.beigz.chesswinners.util.AppUtil.removeTrailingDigitsAfterUnderscore;

public class ExcelReader {

    private final String inputFile;

    public ExcelReader(String inputFile) {
        this.inputFile = inputFile;
    }

    public List<CategoryPrize> readCategoryAndPrizes() throws IOException, InvalidFormatException {

        AppUtil.log("Reading Categories and their Prizes...");
        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(inputFile));

        // Getting the Sheet to read
        Sheet sheet = workbook.getSheet("sort");

        if (sheet == null) {
            AppUtil.log("There is no sheet with name as 'sort'");
            throw new InvalidFormatException("There is no sheet with name as 'sort'");
        }

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        // you can use Java 8 forEach loop with lambda
        List<CategoryPrize> categoryPrizes = new ArrayList<>();
        sheet.forEach(row -> {
            String cellValue0 = dataFormatter.formatCellValue(row.getCell(0));
            String cellValue1 = dataFormatter.formatCellValue(row.getCell(1));
            if (!(cellValue0.equalsIgnoreCase("Prize") && cellValue1.equalsIgnoreCase("Prize Money"))) {
                if (!cellValue0.trim().isEmpty()) {
                    String sanitizedCategory = removeTrailingDigitsAfterUnderscore(cellValue0);
                    categoryPrizes.add(new CategoryPrize(sanitizedCategory, cellValue1.replaceAll(",", "")));
                }
            }
        });

        // Closing the workbook
        workbook.close();
        AppUtil.log("Number of unique categories found :" + categoryPrizes.size());

        return categoryPrizes;
    }

    public List<Player> readFinalRankList() throws IOException, InvalidFormatException {

        AppUtil.log("Reading the Ranklist and Player details...");

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(inputFile));

        // Getting the Sheet to read
        Sheet sheet = workbook.getSheet("Ranklist");

        if (sheet == null) {
            AppUtil.log("There is no sheet with name as 'Ranklist'");
            throw new InvalidFormatException("There is no sheet with name as 'Ranklist'");
        }

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        List<Player> players = new ArrayList<>();
        sheet.forEach(row -> {
            if (isNumber(dataFormatter.formatCellValue(row.getCell(0)))) {
                Integer rank = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(0)));
                String serialNo = dataFormatter.formatCellValue(row.getCell(1));
                String name = dataFormatter.formatCellValue(row.getCell(3));
                String gender = dataFormatter.formatCellValue(row.getCell(4));
                if (!gender.equalsIgnoreCase("F")) {
                    gender = "M";
                }
                Integer rating = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(5)));
                String club = dataFormatter.formatCellValue(row.getCell(6));
                Integer age = 0;
                if (isNumber(dataFormatter.formatCellValue(row.getCell(7)))) {
                    age = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(7)));
                }
                String type = dataFormatter.formatCellValue(row.getCell(8));
                String disability = dataFormatter.formatCellValue(row.getCell(9));
                String points = dataFormatter.formatCellValue(row.getCell(10));

                players.add(new Player(rank, serialNo, name, gender, rating, club, type, "0", "", points, disability, age));

            }
        });

        // Closing the workbook
        workbook.close();

        AppUtil.log("Total number of players found:" + players.size());

        return players;
    }

    public String readTournamentTitle() throws IOException, InvalidFormatException {

        AppUtil.log("Reading Title of the Tournament...");
        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(inputFile));

        // Getting the Sheet to read
        Sheet firstSheet = workbook.getSheetAt(0);

        // if the first sheet name is sort or ranklist, then throw error
        if (firstSheet == null || firstSheet.getSheetName().equalsIgnoreCase("sort") || firstSheet.getSheetName().equalsIgnoreCase("ranklist")) {
            AppUtil.log("There is no sheet which has tournament name");
            return null;
        }

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();
        StringBuilder title = new StringBuilder();
        Boolean stopFlag = true;
        for (Row row : firstSheet) {
            if (stopFlag) {
                for (Cell cell : row) {
                    String cellValue = dataFormatter.formatCellValue(cell);
                    if (cellValue.contains("main") || cellValue.contains("Main") || cellValue.contains("MAIN")) {
                        stopFlag = false;
                    } else {
                        title.append(dataFormatter.formatCellValue(cell));
                    }
                }
                title.append("\n");
            }
        }
        // Closing the workbook
        workbook.close();
        AppUtil.log("Title of the tournament is: " + title);

        return title.toString().trim();
    }
}
