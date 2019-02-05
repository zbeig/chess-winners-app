package com.beigz.chesswinners;

import com.beigz.chesswinners.model.CategoryPrize;
import com.beigz.chesswinners.model.Player;

import java.util.*;

/**
 * Created by Zaheer Beig on 07/03/2018.
 */
@SuppressWarnings("Duplicates")
public class WinningListProcessor {

    private String evaluationMode;
    private List<Integer> ratingList = null;

    public WinningListProcessor(String evaluationMode) {
        this.evaluationMode = evaluationMode;
    }

    public String getEvaluationMode() {
        return evaluationMode;
    }

    public void setEvaluationMode(String evaluationMode) {
        this.evaluationMode = evaluationMode;
    }

    public void processWinnersList(List<CategoryPrize> categoryPrizes, List<Player> players) {

        System.out.println("\nProcessing the winner's list in " + evaluationMode);

        // set the ratings for the prize category of ratings
        setRatingsOfCategoryPrize(categoryPrizes);

        // iterate over the prizes
        for (CategoryPrize categoryPrize : categoryPrizes) {
            for (Player player : players) {

                // check if the prize money of the player is less than the category prize and if it is cash prize
                if (isNumber(categoryPrize.getPrize())) {
                    if (Integer.valueOf(player.getPrizeMoney()) < Integer.valueOf(categoryPrize.getPrize())) {
                        // if the category is main, then assign to the players in sequence
                        if (categoryPrize.getCategory().contains("Main")) {
                            player.setWinningCategory("MAIN");
                            player.setPrizeMoney(categoryPrize.getPrize());
                            break;
                        }

                        if (categoryPrize.getCategory().contains("Below")) {
                            Integer rating = Integer.parseInt(categoryPrize.getCategory().replaceAll("[^0-9]", ""));
                            // for mode 1 - range is : min=closest val
                            if (evaluationMode.equalsIgnoreCase(AppConstants.DEFAULT_MODE) && player.getRating() != 0) {
                                Integer closestVal = findClosestRating(player.getRating());
                                if (player.getRating() < rating && rating.equals(closestVal)) {
                                    player.setWinningCategory(categoryPrize.getCategory());
                                    player.setPrizeMoney(categoryPrize.getPrize());
                                    break;
                                }
                            }
                            // for mode 2 - range is : min=0
                            else {
                                if (player.getRating() < rating) {
                                    player.setWinningCategory(categoryPrize.getCategory());
                                    player.setPrizeMoney(categoryPrize.getPrize());
                                    break;
                                }
                            }
                        }

                        /*if (categoryPrize.getCategory().contains("Below") && player.getRating() != 0) {
                            Integer rating = Integer.parseInt(categoryPrize.getCategory().replaceAll("[^0-9]", ""));
                            Integer closestVal = findClosestRating(player.getRating());

                            if (evaluationMode.equalsIgnoreCase(AppConstants.DEFAULT_MODE)) {
                                if (player.getRating() < rating && rating.equals(closestVal)) {
                                    player.setWinningCategory(categoryPrize.getCategory());
                                    player.setPrizeMoney(categoryPrize.getPrize());
                                    break;
                                }
                            } else {
                                if (player.getRating() < rating) {
                                    player.setWinningCategory(categoryPrize.getCategory());
                                    player.setPrizeMoney(categoryPrize.getPrize());
                                    break;
                                }
                            }

                        }*/
                        if (categoryPrize.getCategory().contains("Un")) {
                            if (player.getRating().equals(0)) {
                                player.setWinningCategory(categoryPrize.getCategory());
                                player.setPrizeMoney(categoryPrize.getPrize());
                                break;
                            }
                        }
                        if (categoryPrize.getCategory().equalsIgnoreCase(player.getClub())) {
                            player.setWinningCategory(categoryPrize.getCategory());
                            player.setPrizeMoney(categoryPrize.getPrize());
                            break;
                        }
                        if (categoryPrize.getCategory().equalsIgnoreCase(player.getType())) {
                            player.setWinningCategory(categoryPrize.getCategory());
                            player.setPrizeMoney(categoryPrize.getPrize());
                            break;
                        }
                        if (categoryPrize.getCategory().equalsIgnoreCase(player.getDisability())) {
                            player.setWinningCategory(categoryPrize.getCategory());
                            player.setPrizeMoney(categoryPrize.getPrize());
                            break;
                        }
                        if (isWomenCategory(categoryPrize, player)) {
                            player.setWinningCategory(categoryPrize.getCategory());
                            player.setPrizeMoney(categoryPrize.getPrize());
                            break;
                        }
                        // club + gender category handling
                        if (isClubAndGenderCategory(categoryPrize)) {
                            int lastIndexOf = categoryPrize.getCategory().lastIndexOf("_");
                            String club = categoryPrize.getCategory().substring(0, lastIndexOf);
                            String gender = categoryPrize.getCategory().substring(lastIndexOf + 1, categoryPrize.getCategory().length());

                            if (player.getClub().equalsIgnoreCase(club)) {
                                // check if the category is for club+gender+age
                                if (gender.matches(".*\\d+.*")) {
                                    String age = extractAgeFromType(gender);
                                    String playerAge = extractAgeFromType(player.getType());
                                    if (gender.equalsIgnoreCase(player.getType())) {
                                        player.setWinningCategory(categoryPrize.getCategory());
                                        player.setPrizeMoney(categoryPrize.getPrize());
                                        break;
                                    }
                                    // another check for female players - commented to fix a defect
                                    /*if ("F".equalsIgnoreCase(player.getGender()) && age.equalsIgnoreCase(playerAge)) {
                                        player.setWinningCategory(categoryPrize.getCategory());
                                        player.setPrizeMoney(categoryPrize.getPrize());
                                        break;
                                    }*/
                                }
                                // category is only for club+gender
                                else {
                                    if (player.getGender().equalsIgnoreCase(gender)) {
                                        player.setWinningCategory(categoryPrize.getCategory());
                                        player.setPrizeMoney(categoryPrize.getPrize());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                // non-cash prize allocation
                else if (categoryPrize.getPrize().equalsIgnoreCase("trophy") && player.getPrizeMoney().equalsIgnoreCase("0")) {

                    if (categoryPrize.getCategory().contains("Below")) {
                        Integer rating = Integer.parseInt(categoryPrize.getCategory().replaceAll("[^0-9]", ""));
                        // for mode 1 - range is : min=closest val
                        if (evaluationMode.equalsIgnoreCase(AppConstants.DEFAULT_MODE) && player.getRating() != 0) {
                            Integer closestVal = findClosestRating(player.getRating());
                            if (player.getRating() < rating && rating.equals(closestVal)) {
                                player.setWinningCategory(categoryPrize.getCategory());
                                player.setPrizeMoney(categoryPrize.getPrize());
                                break;
                            }
                        }
                        // for mode 2 - range is : min=0
                        else {
                            if (player.getRating() < rating) {
                                player.setWinningCategory(categoryPrize.getCategory());
                                player.setPrizeMoney(categoryPrize.getPrize());
                                break;
                            }
                        }
                    }
                    /*if (categoryPrize.getCategory().contains("Below") && player.getRating() != 0) {
                        Integer rating = Integer.parseInt(categoryPrize.getCategory().replaceAll("[^0-9]", ""));
                        Integer closestVal = findClosestRating(player.getRating());

                        if (evaluationMode.equalsIgnoreCase(AppConstants.DEFAULT_MODE)) {
                            if (player.getRating() < rating && rating.equals(closestVal)) {
                                player.setWinningCategory(categoryPrize.getCategory());
                                player.setPrizeMoney(categoryPrize.getPrize());
                                break;
                            }
                        } else {
                            if (player.getRating() < rating) {
                                player.setWinningCategory(categoryPrize.getCategory());
                                player.setPrizeMoney(categoryPrize.getPrize());
                                break;
                            }
                        }
                    }*/
                    if (categoryPrize.getCategory().contains("Un")) {
                        if (player.getRating().equals(0)) {
                            player.setWinningCategory(categoryPrize.getCategory());
                            player.setPrizeMoney(categoryPrize.getPrize());
                            break;
                        }
                    }
                    if (categoryPrize.getCategory().equalsIgnoreCase(player.getClub())) {
                        player.setWinningCategory(categoryPrize.getCategory());
                        player.setPrizeMoney(categoryPrize.getPrize());
                        break;
                    }
                    if (categoryPrize.getCategory().equalsIgnoreCase(player.getType())) {
                        player.setWinningCategory(categoryPrize.getCategory());
                        player.setPrizeMoney(categoryPrize.getPrize());
                        break;
                    }
                    if (categoryPrize.getCategory().equalsIgnoreCase(player.getDisability())) {
                        player.setWinningCategory(categoryPrize.getCategory());
                        player.setPrizeMoney(categoryPrize.getPrize());
                        break;
                    }
                    if (isWomenCategory(categoryPrize, player)) {
                        player.setWinningCategory(categoryPrize.getCategory());
                        player.setPrizeMoney(categoryPrize.getPrize());
                        break;
                    }
                    // club + gender category handling
                    if (isClubAndGenderCategory(categoryPrize)) {
                        int lastIndexOf = categoryPrize.getCategory().lastIndexOf("_");
                        String club = categoryPrize.getCategory().substring(0, lastIndexOf);
                        String genderAgeInCat = categoryPrize.getCategory().substring(lastIndexOf + 1, categoryPrize.getCategory().length());

                        if (player.getClub().equalsIgnoreCase(club)) {
                            // check if the category is for club+gender+age
                            if (genderAgeInCat.matches(".*\\d+.*")) {
                                String genderCat = extractGender(genderAgeInCat);
                                String playerGender = player.getGender();
                                String age = extractAgeFromType(genderAgeInCat);
                                String playerAge = extractAgeFromType(player.getType());
                                if (genderAgeInCat.equalsIgnoreCase(player.getType())) {
                                    player.setWinningCategory(categoryPrize.getCategory());
                                    player.setPrizeMoney(categoryPrize.getPrize());
                                    break;
                                }
                                // another check for female players - commented to fix a defect
                                /*if ("F".equalsIgnoreCase(player.getGender()) && age.equalsIgnoreCase(playerAge)) {
                                    player.setWinningCategory(categoryPrize.getCategory());
                                    player.setPrizeMoney(categoryPrize.getPrize());
                                    break;
                                }*/
                            }
                            // category is only for club+gender
                            else {
                                if (player.getGender().equalsIgnoreCase(genderAgeInCat)) {
                                    player.setWinningCategory(categoryPrize.getCategory());
                                    player.setPrizeMoney(categoryPrize.getPrize());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Processing completed!");
    }

    private String extractGender(String genderAgeInCat) {
        String gender = "F";
        if (genderAgeInCat.matches(".*U.*")) gender = "All";
        return gender;
    }

    // input examples M02, F11, U15 ; returns just digits
    private String extractAgeFromType(String inputStr) {
        String age = "0";
        if (null != inputStr && inputStr.length() > 0) {
            age = inputStr.substring(1);
            if (isNumber(age)) {
                return age;
            } else {
                return "0";
            }
        } else {
            return "0";
        }
    }

    private boolean isWomenCategory(CategoryPrize categoryPrize, Player player) {
        return categoryPrize.getCategory().equalsIgnoreCase("W") && player.getGender().equalsIgnoreCase("F") && !(player.getType().contains("F") || player.getType().contains("f"));
    }

    private boolean isClubAndGenderCategory(CategoryPrize categoryPrize) {
        return categoryPrize.getCategory().contains("_M") || categoryPrize.getCategory().contains("_F") || categoryPrize.getCategory().contains("_m") || categoryPrize.getCategory().contains("_f") || categoryPrize.getCategory().contains("_U") || categoryPrize.getCategory().contains("_u");
    }

    private Integer findClosestRating(Integer playerRating) {
        Integer closestVal = null;
        for (Integer ele : ratingList) {
            if (playerRating < ele) {
                closestVal = ele;
            }

        }
        return closestVal;
    }


    private void setRatingsOfCategoryPrize(List<CategoryPrize> categoryPrizes) {
        Set<Integer> ratingSet = new HashSet<>();

        for (CategoryPrize categoryPrize : categoryPrizes) {
            if (categoryPrize.getCategory().contains("Below")) {
                Integer rating = Integer.parseInt(categoryPrize.getCategory().replaceAll("[^0-9]", ""));
                ratingSet.add(rating);
            }
        }

        ratingList = new ArrayList<>(ratingSet);
        Collections.sort(ratingList, Collections.reverseOrder());

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

}
