package com.beigz.chesswinners;

import com.beigz.chesswinners.model.CategoryPrize;
import com.beigz.chesswinners.model.Player;

import java.util.List;

/**
 * Created by Zaheer Beig on 07/03/2018.
 */
@SuppressWarnings("Duplicates")
public class WinningListProcessor {

    private String evaluationMode;

    public WinningListProcessor(String evaluationMode) {
        this.evaluationMode = evaluationMode;
    }

    public void processWinnersList(List<CategoryPrize> categoryPrizes, List<Player> players) {

        if (evaluationMode.equalsIgnoreCase(AppConstants.DEFAULT_MODE)) {
            Mode1Processor.process(categoryPrizes, players);
        } else {
            Mode2Processor.process(categoryPrizes, players);
        }
    }
}
