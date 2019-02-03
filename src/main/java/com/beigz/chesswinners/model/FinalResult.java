package com.beigz.chesswinners.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Zaheer Beig on 05/03/2018.
 */
public class FinalResult {


    private Map<String, List<Player>> finalResult;

    public Map<String, List<Player>> getFinalResult() {
        return finalResult;
    }

    public void setFinalResult(Map<String, List<Player>> finalResult) {
        this.finalResult = finalResult;
    }
}
