package com.beigz.chesswinners.util;

import com.beigz.chesswinners.model.CategoryPrize;
import com.beigz.chesswinners.model.Player;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Zaheer Beig on 23/02/2019.
 */
public class AppUtil {

    public static void log(String msg) {
        System.out.println(getCurrentDateTime() + " - " + msg);
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static String extractGender(String genderAgeInCat) {
        String gender = "F";
        if (genderAgeInCat.matches(".*U.*")) gender = "All";
        return gender;
    }

    // input examples M02, F11, U15 ; returns just digits
    public static Integer extractAgeFromType(String inputStr) {
        String age = "0";
        if (null != inputStr && inputStr.length() > 0) {
            age = inputStr.substring(1);
            if (isNumber(age)) {
                return Integer.valueOf(age);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    // input examples M02, F11, U15 ; returns just digits
    public static Integer extractAgeFromClubType(String inputStr) {
        String age = "0";
        if (null != inputStr && inputStr.length() > 0 && inputStr.contains("_")) {
            String[] arr = inputStr.split("_");
            return extractAgeFromType(arr[1]);
        } else {
            return 0;
        }
    }

    public static boolean isWomenCategory(CategoryPrize categoryPrize, Player player) {
        return categoryPrize.getCategory().equalsIgnoreCase("W") && player.getGender().equalsIgnoreCase("F") && !(player.getType().contains("F") || player.getType().contains("f"));
    }

    public static boolean isClubAndGenderCategory(CategoryPrize categoryPrize) {
        return categoryPrize.getCategory().contains("_M") || categoryPrize.getCategory().contains("_F") || categoryPrize.getCategory().contains("_m") || categoryPrize.getCategory().contains("_f") || categoryPrize.getCategory().contains("_U") || categoryPrize.getCategory().contains("_u");
    }

    public static Integer findClosestRating(Integer playerRating, List<Integer> ratingList) {
        Integer closestVal = null;
        for (Integer ele : ratingList) {
            if (playerRating < ele) {
                closestVal = ele;
            }

        }
        return closestVal;
    }

    public static Boolean isNumber(String str) {

        Boolean isNumber = true;
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            isNumber = false;
        }

        return isNumber;
    }

    public static List setRatingsOfCategoryPrize(List<CategoryPrize> categoryPrizes) {
        Set<Integer> ratingSet = new HashSet<>();

        for (CategoryPrize categoryPrize : categoryPrizes) {
            if (categoryPrize.getCategory().contains("Below")) {
                Integer rating = Integer.parseInt(categoryPrize.getCategory().replaceAll("[^0-9]", ""));
                ratingSet.add(rating);
            }
        }

        List ratingList = new ArrayList<>(ratingSet);
        Collections.sort(ratingList, Collections.reverseOrder());
        return ratingList;
    }

    public static Boolean isAgeType(String categoryType) {
        return categoryType.matches("^[u,m,f,U,M,F]\\d{1,2}$");
    }

    public static String removeTrailingDigitsAfterUnderscore(String inputStr) {
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

    public static Boolean isTwoDigitNumber(String str) {

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

    public static int countNewLines(String inputStr) {
        int start = -1;
        int count = 0;
        while ((start = inputStr.indexOf("\n", start + 1)) != -1)
            count++;
        return count;
    }

    public static boolean isNullOrEmpty(String str) {
        if (str != null && !str.isEmpty())
            return false;
        return true;
    }
}