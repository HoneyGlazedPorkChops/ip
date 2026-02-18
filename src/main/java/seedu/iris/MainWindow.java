package seedu.iris;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import seedu.iris.exception.IrisException;
import seedu.iris.exception.IrisInvalidException;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private Button playPauseButton;
    @FXML
    private Label trackName;
    @FXML
    private Pane songPane;

    private Iris iris;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/seedu/iris/images/user.jpg"));
    private Image irisImage = new Image(this.getClass().getResourceAsStream("/seedu/iris/images/iris.jpg"));
    private Image errImage = new Image(this.getClass().getResourceAsStream("/seedu/iris/images/stare.png"));
    private MusicPlayer musicPlayer;
    private Label playIcon;
    private Label pauseIcon;
    private Timeline timeline;

    private double textPosition = 115;

    @FXML
    public void initialize() {
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        dialogContainer.heightProperty().addListener((obs, oldVal, newVal) -> {
            scrollPane.setVvalue(1.0);
        });

        Rectangle clip = new Rectangle(songPane.getPrefWidth(), songPane.getPrefHeight());
        songPane.setClip(clip);

        timeline = new Timeline(
                new KeyFrame(Duration.millis(20), e -> scrollTextStep())
        );
        timeline.setCycleCount(Animation.INDEFINITE);

        playIcon = new Label("▶");
        playIcon.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        pauseIcon = new Label("⏸");
        pauseIcon.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        playPauseButton.setGraphic(pauseIcon);

        musicPlayer = new MusicPlayer();
        musicPlayer.play();
        scrollTextStart();
        timeline.play();

        dialogContainer.getChildren().add(
                DialogBox.getIrisDialog("""
                 Hello! I'm Iris
                 What can I do for you?
                """, irisImage)
        );
    }

    public void setIris(Iris i) {
        iris = i;
    }

    private void scrollTextStart() {
        trackName.setTranslateX(textPosition);
    }

    private void scrollTextStep() {
        textPosition -= 0.5;

        if (textPosition + trackName.getWidth() < 0) {
            textPosition = trackName.getParent().getLayoutBounds().getWidth();
        }

        trackName.setTranslateX(textPosition);
    }

    @FXML
    private void handlePlayPause() {
        if (musicPlayer.isPlaying()) {
            musicPlayer.pause();
            playPauseButton.setGraphic(playIcon);
            timeline.pause();
            textPosition = 115;
            trackName.setTranslateX(textPosition);
        } else {
            musicPlayer.play();
            playPauseButton.setGraphic(pauseIcon);
            timeline.play();
        }
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Iris' reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        try {
            String response = iris.getResponse(input);

            if (input.equals("bye")) {
                PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
                delay.setOnFinished(event -> Platform.exit());
                delay.play();
            }

            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getIrisDialog(response, irisImage)
            );
            userInput.clear();
        } catch (IrisException e) {
            String errMsg = e.getMessage();

            dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input, userImage),
                    DialogBox.getIrisDialog(errMsg, irisImage));
            userInput.clear();
        } catch (IrisInvalidException e) {
            String errMsg = e.getMessage();

            dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input, userImage),
                    DialogBox.getIrisDialog(errMsg, errImage, irisImage));

            userInput.clear();
        }
    }
}
