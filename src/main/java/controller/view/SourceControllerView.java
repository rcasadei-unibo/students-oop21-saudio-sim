package controller.view;

import java.net.URL;
import java.util.ResourceBundle;

import controller.MainController;
import controller.SourceController;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import model.source.FRSource;
import model.source.SourceType;

public class SourceControllerView implements Initializable, ControllerView {

    @FXML private Button btnAddSpeaker;
    @FXML private Button btnRemoveSpeaker;
    @FXML private RadioButton rbtnDefault;
    @FXML private RadioButton rbtnTweeter;
    @FXML private RadioButton rbtnMidRange;
    @FXML private RadioButton rbtnWoofer;
    @FXML private Label lblX;
    @FXML private Label lblY;
    private SourceController ctrSource;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setControllerApplication(final MainController ctrMain) {
        this.ctrSource = ctrMain.getSourceController();
        this.ctrSource.setControllerView(this);
    }

    @FXML 
    private void handleAddSpeaker(final Event event) {
        this.ctrSource.addSpeaker();

    }

    @FXML 
    private void handleRemoveSpeaker(final Event event) {
       // FRSource speaker = this.getSelectedSpeaker();

        //TODO Remove speaker from SourceHub in Environment

    }

    @FXML 
    private void handleRadioButtonChanged(final Event event) {
        ctrSource.setSpeakerType(this.getSelectedSpeaker(), ((RadioButton) event.getSource()).getId());
    }

    private FRSource getSelectedSpeaker() {
        FRSource selected = this.ctrSource.getSelectedSpeaker();
        lblX.setText(Float.toString(selected.getPosition().getX()));
        lblY.setText(Float.toString(selected.getPosition().getY()));
        return selected;
    }

    /**
     * 
     * @param rbtToSelect
     */
    public void setSelectedRadioButton(final String rbtToSelect) {
        switch (rbtToSelect) {
            case "rbtnDefault":
                rbtnDefault.setSelected(true);
                break;
            case "rbtnTweeter":
                rbtnTweeter.setSelected(true);
                break;
            case "rbtnMidRange":
                rbtnMidRange.setSelected(true);
                break;
            case "rbtnWoofer":
                rbtnWoofer.setSelected(true);
                break;
            default:
                break;
        }
    }

    /**
     * 
     */
    public void setSelectedSpeaker() {
        //TODO
        this.getSelectedSpeaker();
    }

}
