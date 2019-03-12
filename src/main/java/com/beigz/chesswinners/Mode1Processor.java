package com.beigz.chesswinners;

import com.beigz.chesswinners.model.CategoryPrize;
import com.beigz.chesswinners.model.Player;

import java.util.List;

import static com.beigz.chesswinners.util.AppUtil.*;

/**
 * Created by Zaheer Beig on 23/02/2019.
 */
@SuppressWarnings("Duplicates")
public class Mode1Processor {

    private static List<Integer> ratingList = null;

    public static void process(List<CategoryPrize> categoryPrizes, List<Player> players) {

        System.out.println("\nProcessing the winner's list in Mode 1");

        // set the ratings for the prize category of ratings
        ratingList = setRatingsOfCategoryPrize(categoryPrizes);

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

                        if (categoryPrize.getCategory().contains("Below") && player.getRating() != 0) {
                            Integer rating = Integer.parseInt(categoryPrize.getCategory().replaceAll("[^0-9]", ""));
                            Integer closestVal = findClosestRating(player.getRating(), ratingList);
                            if (player.getRating() < rating && rating.equals(closestVal)) {
                                player.setWinningCategory(categoryPrize.getCategory());
                                player.setPrizeMoney(categoryPrize.getPrize());
                                break;
                            }
                        }

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
                                    if (genderAgeInCat.equalsIgnoreCase(player.getType())) {
                                        player.setWinningCategory(categoryPrize.getCategory());
                                        player.setPrizeMoney(categoryPrize.getPrize());
                                        break;
                                    }
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
                // non-cash prize allocation
                else if (categoryPrize.getPrize().equalsIgnoreCase("trophy") && player.getPrizeMoney().equalsIgnoreCase("0")) {

                    if (categoryPrize.getCategory().contains("Below") && player.getRating() != 0) {
                        Integer rating = Integer.parseInt(categoryPrize.getCategory().replaceAll("[^0-9]", ""));
                        Integer closestVal = findClosestRating(player.getRating(), ratingList);
                        if (player.getRating() < rating && rating.equals(closestVal)) {
                            player.setWinningCategory(categoryPrize.getCategory());
                            player.setPrizeMoney(categoryPrize.getPrize());
                            break;
                        }
                    }

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
                                if (genderAgeInCat.equalsIgnoreCase(player.getType())) {
                                    player.setWinningCategory(categoryPrize.getCategory());
                                    player.setPrizeMoney(categoryPrize.getPrize());
                                    break;
                                }
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
        System.out.println("Processing complete!");
    }
}
