package seedu.iris;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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

    private Iris iris;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/seedu/iris/images/user.jpg"));
    private Image irisImage = new Image(this.getClass().getResourceAsStream("/seedu/iris/images/iris.jpg"));
    private Image errImage = new Image(this.getClass().getResourceAsStream("/seedu/iris/images/stare.png"));
    private Media bgm = new Media(this.getClass().getResource("/seedu/iris/music/bgm.mp3").toExternalForm());

    private MediaPlayer mediaPlayer = new MediaPlayer(bgm);

    @FXML
    public void initialize() {
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        dialogContainer.heightProperty().addListener((obs, oldVal, newVal) -> {
            scrollPane.setVvalue(1.0);
        });
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.2);
        mediaPlayer.play();

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

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Iris' reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        if (input.equals("pause")) {
            mediaPlayer.pause();
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getIrisDialog("Music has been pause", irisImage)
            );
            userInput.clear();
        } else if (input.equals("resume")) {
            mediaPlayer.play();
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getIrisDialog("Music has been resumed", irisImage)
            );
            userInput.clear();
        } else {
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
}


