package controller.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.EqualizerController;
import controller.MainController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.effect.ALEffects;

public class EqualizerControllerView implements Initializable, ControllerView {

    @FXML private GridPane slidersPane;
    @FXML private ToggleButton btnTurn;
    private EqualizerControllerView ctrl;

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        ctrl = new EqualizerControllerView();
        for (final Slider slider : getSliders()) {
            final Optional<ALEffects> effect = getEffect(slider.getId());
            if (effect.isPresent()) {
                final var eff = effect.get();
                slider.setMin(eff.getMinValue());
                slider.setMax(eff.getMaxValue());
                slider.valueProperty().addListener((obs, old, newValue) -> {
                    // MainController.getSources().foreach(s -> s.applyFilter(s, eff,
                    // newValue.floatValue()));
                });
            }
        }
    }

    @FXML public final void handlePress(final Event event) {
        final boolean state = btnTurn.isSelected();

        if (!state) {
            btnTurn.setText("Turn on");
            btnTurn.setStyle("-fx-background-color: #F06249");
        } else {
            btnTurn.setText("Turn off");
            btnTurn.setStyle("-fx-background-color: #6CF049");
        }

        getSliders().forEach(s -> s.setDisable(!state));
    }

    /**
     * 
     * @return
     */
    public EqualizerControllerView getController() {
        return ctrl;
    }

    private Optional<ALEffects> getEffect(final String id) {
        switch (id) {
        case "sldReverb":
            return Optional.of(ALEffects.REVERB);
        case "sldDistortion":
            return Optional.of(ALEffects.DISTORTION);
        case "sldEcho":
            return Optional.of(ALEffects.ECHO);
        case "sldFlanger":
            return Optional.of(ALEffects.FLANGER);
        case "sldPitch":
            return Optional.of(ALEffects.PITCH);
        case "sldChorus":
            return Optional.of(ALEffects.CHORUS);
        default:
            return Optional.empty();
        }
    }

    private List<Slider> getSliders() {
        final List<Slider> sliders = new ArrayList<>();

        slidersPane.getChildren().forEach(s -> {
            if (s instanceof Slider) {
                sliders.add((Slider) s);
            }
        });
        return sliders;
    }

    @FXML public final void testClickSlider(final Event event) {
        Slider tempSlider = null; 
        if (event.getSource() instanceof Slider) {
            tempSlider = (Slider) event.getSource();
        }
        System.out.println(event.getSource() + " -> " + tempSlider.getValue());
    }

    @Override
    public void setControllerApplication(final MainController ctrMain) {
        // TODO Auto-generated method stub

    }
}
