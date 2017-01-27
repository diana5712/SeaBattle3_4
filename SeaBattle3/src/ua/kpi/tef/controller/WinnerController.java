package ua.kpi.tef.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Created by Administrator on 13.01.2017.
 */
public class WinnerController {
    @FXML
    Label winnerLabel;

    public  void setWinnerLabel(String winner){
        this.winnerLabel.setText(winner);
    }
}
