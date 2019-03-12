package com.beigz.chesswinners.util;

import com.beigz.chesswinners.model.CategoryPrize;
import com.beigz.chesswinners.model.Player;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    private final String inputFile;

    public ExcelReader(String inputFile) {
        this.inputFile = inputFile;
    }

    public List<CategoryPrize> readCategoryAndPrizes() throws IOException, InvalidFormatException {

        System.out.println("\nReading Categories and their Prizes...");
        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(inputFile));

        // Getting the Sheet to read
        Sheet sheet = workbook.getSheet("sort");

        if (sheet == null) {
            System.out.println("There is no sheet with name as 'sort'");
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
        System.out.println("Number of unique categories found :" + categoryPrizes.size());

        return categoryPrizes;
    }

    private String removeTrailingDigitsAfterUnderscore(String inputStr) {
        String sanitizedCategory = null;
        if (null != inputStr && inputStr.length() > 0) {
            String charAfterLastUnderScore = inputStr.substring(inputStr.lastIndexOf('_') + 1).trim();
            if (isTwoDigitNumber(charAfterLastUnderScore)) {
                int endIndex = inputStr.lastIndexOf("_");
                if (endIndex != -1) {
                    sanitizedCategory = inputStr.substring(0, endIndex);
                }

            } else {
                sanitizedCategory = inputStr;

            }
        }

        return sanitizedCategory;
    }

    public List<Player> readFinalRankList() throws IOException, InvalidFormatException {

        System.out.println("\nReading the Ranklist and Player details...");

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(inputFile));

        // Getting the Sheet to read
        Sheet sheet = workbook.getSheet("Ranklist");

        if (sheet == null) {
            System.out.println("There is no sheet with name as 'Ranklist'");
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

        System.out.println("Total number of players found:" + players.size());

        return players;
    }

    private Boolean isNumber(String str) {

        Boolean isNumber = true;
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            isNumber = false;
        }

        return isNumber;
    }

    private Boolean isTwoDigitNumber(String str) {

        Boolean isTwoDigitNumber = true;

        if (isNumber(str)) {
            int tempNo = Integer.valueOf(str);
            if (!(tempNo > 0 && tempNo < 100)) {
                isTwoDigitNumber = false;
            }
        } else {
            isTwoDigitNumber = false;
        }

        return isTwoDigitNumber;
    }
}
