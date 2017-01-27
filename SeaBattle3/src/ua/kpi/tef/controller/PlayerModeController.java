package ua.kpi.tef.controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ua.kpi.tef.model.Algorithm;
import ua.kpi.tef.view.View;

/**
 * Created by diana on 09.01.17.
 */
public class PlayerModeController {
    @FXML
    private RadioButton withComputerButton;

    @FXML
    private RadioButton withHumanButton;

    @FXML
    private RadioButton alphabetFieldMode;

    @FXML
    private RadioButton respublicaFieldMode;

    @FXML
    private Button applyBut;

    Label l1;
    Label l2;
    Button b1;
    Button b2;

    ToggleGroup playerGroup;
    ToggleGroup fieldGroup;

    public static String playerMode = View.COMPUTER_MODE;
    public static String fieldMode = View.ALPHABET_MODE;

    public  PlayerModeController setLabels(Label l1,Label l2,Button b1,Button b2){
        this.l1 = l1;
        this.l2 = l2;
        this.b1 = b1;
        this.b2 = b2;
        return this;
    }

    public void initialize() {
        playerGroup = new ToggleGroup();
        withComputerButton.setToggleGroup(playerGroup);
        withHumanButton.setToggleGroup(playerGroup);
        withComputerButton.setSelected(true);

        fieldGroup = new ToggleGroup();
        alphabetFieldMode.setToggleGroup(fieldGroup);
        respublicaFieldMode.setToggleGroup(fieldGroup);
        alphabetFieldMode.setSelected(true);
    }

    @FXML
    public void applyPlayerMode() {
        playerMode = ((RadioButton)playerGroup.getSelectedToggle()).getText();
        fieldMode = ((RadioButton)fieldGroup.getSelectedToggle()).getText();

        Algorithm.selectFieldMode(PlayerModeController.fieldMode,l1,l2);
        Algorithm.selectPlayerMode(PlayerModeController.playerMode,b1,b2);
        ((Stage)applyBut.getScene().getWindow()).close();
    }
}
