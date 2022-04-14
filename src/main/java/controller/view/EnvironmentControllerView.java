package controller.view;

import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import controller.EnvironmentController;
import controller.MainController;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import model.utility.Pair;
import model.utility.Vec3f;
import view.utility.Rectangle;
import view.utility.Sprite;
import view.utility.Texture;
import view.utility.TypeSprite;
import view.utility.Vector;

public class EnvironmentControllerView implements Initializable, ControllerView {

    private EnvironmentController ctrl;
    @FXML
    private Canvas canvas;
    @FXML
    private AnchorPane conteinerCanvas;
    @FXML
    private GraphicsContext contextView;

    private Optional<Sprite> lastSelectedSource;

    private double angleListener = 90;

    private Color colorFill = Color.LIGHTGRAY;

    private Set<Sprite> sprites;

    private double x = 10;
    private double y = 10;

    /**
     * 
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        sprites = new HashSet<>();
        contextView = canvas.getGraphicsContext2D();

        conteinerCanvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setWidth(newVal.doubleValue() - 1);
        });

        conteinerCanvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setHeight(newVal.doubleValue() - 1);
        });

        //TEST SPRITE SFONDO
        final Sprite sfondo = new Sprite(-2);
        final Texture tx = new Texture("sfondoCinema");
        sfondo.setTexture(tx);
        sfondo.draw(contextView);


        AnimationTimer musicLoop = new AnimationTimer() {

            @Override
            public void handle(final long now) {
                sprites.stream().forEach(e -> {
                    e.setVelocity(new Vector(0, 0));
                    e.getVelocity().multiply(1 / 60.0);
                });
                contextView.setFill(colorFill);
                contextView.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                //CINEMA DISABILITATO
                //sfondo.draw(contextView);
                sprites.stream().forEach(e -> {
                    Pair<Double, Double> pos = checkOutOfBorder(
                            new Pair<Double, Double>(e.getPosition().getX(), e.getPosition().getY()), e.getSize());
                    e.setPosition(pos.getX(), pos.getY());
                    if (e.getSpriteType().equals(TypeSprite.LISTENER)) {
                        listenerDraw(e, e.getPosition());
                    } else {
                        e.draw(contextView);
                    }
                });
            }
        };
        musicLoop.start();
    }


    @FXML public final void handleOnMouseClickedOrDrag(final Event event) {
        moveSprite(event);
    }

    /**
     * 
     */
    private void moveSprite(final Event event) {
        final Optional<Sprite> temp = sprites.stream()
                .filter(s -> s.getSize()
                        .overlap(new Rectangle(((MouseEvent) event).getX(), ((MouseEvent) event).getY(), 0.0, 0.0)))
                .findAny();

        if (temp.isPresent()) {
            Pair<Double, Double> newPos = new Pair<>(
                    ((MouseEvent) event).getX() - (temp.get().getSize().getWidth() / 2),
                    ((MouseEvent) event).getY() - (temp.get().getSize().getHeight() / 2));
            // CheckOutOfBorder
            newPos = checkOutOfBorder(newPos, temp.get().getSize());
            temp.get().setPosition(newPos.getX(), newPos.getY());
            final Pair<Float, Float> posFloat = updatePosForController(newPos);
            if (temp.get().getSpriteType() == TypeSprite.LISTENER) {
                this.ctrl.moveListener(new Vec3f(posFloat.getX(), posFloat.getY(), 0.0f));
            } else {
                this.ctrl.moveSource(new Vec3f(posFloat.getX(), posFloat.getY(), 0.0f), temp.get().getId());
                setLastSelectedSource(temp.get());
            }
        }
    }

    private Pair<Float, Float> updatePosForController(final Pair<Double, Double> pos) {
        final Pair<Float, Float> posFloat = new Pair<Float, Float>(
                (float) ((x * pos.getX()) / canvas.getWidth()),
                (float) ((y * pos.getY()) / canvas.getHeight()));
        return posFloat;
    }

    private Pair<Double, Double> checkOutOfBorder(final Pair<Double, Double> newPos, final Rectangle size) {
        if (newPos.getX() > canvas.getWidth() - size.getWidth()) {
            newPos.setX(canvas.getWidth() - size.getHeight());
        }
        if (newPos.getY() > canvas.getHeight() - size.getHeight()) {
            newPos.setY(canvas.getHeight() - size.getHeight());
        }
        if (newPos.getX() < 0) {
            newPos.setX(0.0);
        }
        if (newPos.getY() < 0) {
            newPos.setY(0.0);
        }
        return newPos;
    }

    /**
     * 
     * @param type
     * @param id
     * @param posElement
     */
    public void addSprite(/* type */ final TypeSprite type, final int id, final Vec3f posElement) {
        final Sprite sprite = new Sprite(id);
        sprite.setSpriteType(type);
        final Texture tx = new Texture(type.toString());
        sprite.setTexture(tx);
        Pair<Double, Double> posDouble = new Pair<Double, Double>(
                (double) ((canvas.getWidth() * posElement.getX()) / x),
                (double) ((canvas.getHeight() * posElement.getY()) / y));
        posDouble = checkOutOfBorder(new Pair<Double, Double>(posDouble.getX(), posDouble.getY()), sprite.getSize());
        sprite.setPosition((double) posDouble.getX(), (double) posDouble.getY());
        sprite.draw(contextView);
        checkSprite(sprite, posElement);
        sprites.add(sprite);
    }

    private void checkSprite(final Sprite sprite, final Vec3f posElement) {
        if (sprite.getSpriteType().equals(TypeSprite.LISTENER)) {
            this.ctrl.moveListener(new Vec3f(posElement.getX(), posElement.getY(), 0f));
        } else {
            setLastSelectedSource(sprite);
        }
    }


    private void setLastSelectedSource(final Sprite sprite) {
        this.lastSelectedSource = Optional.ofNullable(sprite);
        this.ctrl.lastSelectedSourceChange();
    }


    /**
     * 
     */
    @Override
    public void setControllerApplication(final MainController ctrMain) {
        this.ctrl = ctrMain.getEnvironmentController();
        this.ctrl.setControllerView(this);
    }

   /**
    * 
    * @return TODO
    */
    public double getX() {
        return x;
    }

    /**
     * 
     * @return TODO
     */
    public double gety() {
        return y;
    }

    /**
     * 
     * @return TODO
     */
    public int getLastSelectedSource() {
        return lastSelectedSource.isPresent() ? lastSelectedSource.get().getId() : -1;
    }

    /**
     * 
     */
    public void removeSpriteSource() {
        sprites.remove(lastSelectedSource.get());
    }

    /**
     * 
     * @param type
     */
    public void upgradeTypeSpriteSource(final TypeSprite type) {
        lastSelectedSource.get().setSpriteType(type);
    }

    /**
     * 
     * @param angle
     */
    public void setAngleListener(final float angle) {
        this.angleListener = angle;
    }

    /**
     * 
     * @param px
     * @param py
     */
    private void rotate(final double px, final double py) {
        Rotate r = new Rotate(angleListener, px, py);
        contextView.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    /**
     * 
     * @param listener
     */
    public void listenerDraw(final Sprite listener, final Vector pos) {
        //this.contextView.save();
        //rotate(pos.getX(), pos.getY());
        this.canvas.rotateProperty().set(angleListener);
        listener.draw(contextView);
        this.contextView.save();
        this.canvas.rotateProperty().set(0);
    }

    /**
     * 
     * @param x
     * @param y
     */
    public void setSize(final double x, final double y) {
        this.x = x;
        this.y = y;

        sprites.stream().forEach(e -> {
            Pair<Float, Float> temp = updatePosForController(new Pair<Double, Double>(e.getPosition().getX(), e.getPosition().getY()));
            Vec3f vec = new Vec3f(temp.getX(), temp.getY(), 0f);
            System.out.println(temp.getX() + " " + temp.getY());
            if (e.getSpriteType() == TypeSprite.LISTENER) {
                this.ctrl.moveListener(vec);
            } else {
                this.ctrl.moveSource(vec, e.getId());
                this.ctrl.lastSelectedSourceChange();
            }
        });
    }

    @Override
    public void showError(final String error) {
        // TODO Auto-generated method stub
    }

    /**
     *  TODO .
     */
    public void reset() {
        sprites.clear();
    }

}
