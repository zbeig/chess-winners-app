package com.beigz.chesswinners.util;

import com.beigz.chesswinners.model.CategoryPrize;
import com.beigz.chesswinners.model.Player;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class ExcelWriter {

    private static final String[] columns = {"Serial No", "Name", "Rank", "Gender", "Rating", "Club", "Type", "Points", "Prize Money", "Winning Category"};

    public String write2Excel(List<Player> players, List<CategoryPrize> categoryPrizes) throws IOException, InvalidFormatException {

        Set<String> uniqueCategorySet = getUniqueCategoryList(categoryPrizes);
        // Create a Workbook
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        // CreationHelper helps us create instances for various things like DataFormat,Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("WinnersList");

        // Create a Font for styling header cells
        Font headerFont1 = workbook.createFont();
        headerFont1.setBold(true);
        headerFont1.setFontHeightInPoints((short) 14);
        headerFont1.setColor(IndexedColors.BLACK.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle1 = workbook.createCellStyle();
        headerCellStyle1.setFont(headerFont1);
        headerCellStyle1.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerCellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle1.setAlignment(HorizontalAlignment.CENTER);

        headerCellStyle1.setBorderBottom(BorderStyle.MEDIUM);
        headerCellStyle1.setBorderTop(BorderStyle.MEDIUM);
        headerCellStyle1.setBorderRight(BorderStyle.MEDIUM);
        headerCellStyle1.setBorderLeft(BorderStyle.MEDIUM);

        // Create a Font for styling header cells
        Font headerFont2 = workbook.createFont();
        headerFont2.setBold(true);
        headerFont2.setFontHeightInPoints((short) 12);
        headerFont2.setColor(IndexedColors.BLACK1.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle2 = workbook.createCellStyle();
        headerCellStyle2.setFont(headerFont2);
        headerCellStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerCellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle2.setAlignment(HorizontalAlignment.CENTER);

        headerCellStyle2.setBorderBottom(BorderStyle.MEDIUM);
        headerCellStyle2.setBorderTop(BorderStyle.MEDIUM);
        headerCellStyle2.setBorderRight(BorderStyle.MEDIUM);
        headerCellStyle2.setBorderLeft(BorderStyle.MEDIUM);

        // Create a CellStyle with the font
        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setBorderBottom(BorderStyle.THIN);
        dataCellStyle.setBorderTop(BorderStyle.THIN);
        dataCellStyle.setBorderRight(BorderStyle.THIN);
        dataCellStyle.setBorderLeft(BorderStyle.THIN);

        int rowNum = 0;
        int col = 0;
        for (String cat : uniqueCategorySet) {

            // Create a Row
            Row headerRow1 = sheet.createRow(rowNum++);

            // Creating cells
            Cell mainCell = headerRow1.createCell(col);
            mainCell.setCellValue(cat);
            mainCell.setCellStyle(headerCellStyle1);

            CellRangeAddress cellRangeAddress = new CellRangeAddress(
                    rowNum - 1, //first row (0-based)
                    rowNum - 1, //last row  (0-based)
                    0, //first column (0-based)
                    9  //last column  (0-based)
            );
            sheet.addMergedRegion(cellRangeAddress);

            RegionUtil.setBorderBottom(BorderStyle.MEDIUM, cellRangeAddress, sheet);
            RegionUtil.setBorderLeft(BorderStyle.MEDIUM, cellRangeAddress, sheet);
            RegionUtil.setBorderRight(BorderStyle.MEDIUM, cellRangeAddress, sheet);
            RegionUtil.setBorderTop(BorderStyle.MEDIUM, cellRangeAddress, sheet);

            // Create a Row
            Row headerRow2 = sheet.createRow(rowNum++);

            // Creating cells
            for (int i = 0; i < columns.length; i++) {
                Cell subMainCell = headerRow2.createCell(i);
                subMainCell.setCellValue(columns[i]);
                subMainCell.setCellStyle(headerCellStyle2);
            }

            int slNo = 0;

            for (Player player : players) {
                if (player.getWinningCategory().equalsIgnoreCase(cat)) {
                    Row row = sheet.createRow(rowNum++);

                    Cell cell1 = row.createCell(0);
                    cell1.setCellStyle(dataCellStyle);
                    cell1.setCellValue(++slNo);

                    Cell cell2 = row.createCell(1);
                    cell2.setCellStyle(dataCellStyle);
                    cell2.setCellValue(player.getName());

                    Cell cell0 = row.createCell(2);
                    cell0.setCellStyle(dataCellStyle);
                    cell0.setCellValue(player.getRank());

                    Cell cell3 = row.createCell(3);
                    cell3.setCellStyle(dataCellStyle);
                    cell3.setCellValue(player.getGender());

                    Cell cell4 = row.createCell(4);
                    cell4.setCellStyle(dataCellStyle);
                    cell4.setCellValue(player.getRating());

                    Cell cell5 = row.createCell(5);
                    cell5.setCellStyle(dataCellStyle);
                    cell5.setCellValue(player.getClub());

                    Cell cell6 = row.createCell(6);
                    cell6.setCellStyle(dataCellStyle);
                    cell6.setCellValue(player.getType());

                    Cell cell7 = row.createCell(7);
                    cell7.setCellStyle(dataCellStyle);
                    cell7.setCellValue(player.getPoints());

                    Cell cell8 = row.createCell(8);
                    cell8.setCellStyle(dataCellStyle);
                    cell8.setCellValue(player.getPrizeMoney());

                    Cell cell9 = row.createCell(9);
                    cell9.setCellStyle(dataCellStyle);
                    cell9.setCellValue(player.getWinningCategory());

                }
            }
            rowNum++;
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }


        Row row1 = sheet.createRow(rowNum++);
        Cell cell1 = row1.createCell(0);
        cell1.setCellValue("Program ChessWinners developed and copyright by Zaheer Beig");

        Row row2 = sheet.createRow(rowNum++);
        Cell cell2 = row2.createCell(0);
        cell2.setCellValue("eMail:zaheer.beig@gmail.com");

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
        Date date = new Date();
        String currDate = formatter.format(date);

        Path path = Paths.get(".." + File.separator + "Results");
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                //fail to create directory
                e.printStackTrace();
            }
        }
        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(".." + File.separator + "Results" + File.separator + "WinnerList_" + currDate + ".xlsx");
        workbook.write(fileOut);

        fileOut.close();
        System.out.println("");
        System.out.println("Output File generated : WinnerList_" + currDate + ".xlsx");
        String cwd = System.getProperty("user.dir");
        String firstPath = cwd.substring(0, cwd.lastIndexOf("\\"));
        Path secondPath = Paths.get(File.separator + "Results" + File.separator + "WinnerList_" + currDate + ".xlsx");
        return new File(firstPath + secondPath.toString()).getAbsolutePath();
    }

    private Set<String> getUniqueCategoryList(List<CategoryPrize> categoryPrizes) {
        Set<String> categorySet = new LinkedHashSet<>();
        for (CategoryPrize cat : categoryPrizes) {
            categorySet.add(cat.getCategory());
        }
        return categorySet;
    }
}
