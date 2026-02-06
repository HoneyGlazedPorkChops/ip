package seedu.iris;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    
    @FXML
    private ImageView displayPicture;

    @FXML
    private ImageView messageImage;

    @FXML
    private StackPane messagePaneWrapper;

    @FXML
    private StackPane userMessagePane;

    @FXML
    private VBox messagePane;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/seedu/iris/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        makeImageCircular();
    }

    private DialogBox(String text, Image messageImg, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/seedu/iris/view/DialogImageBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        messageImage.setImage(messageImg);
        displayPicture.setImage(img);

        makeImageCircular();
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    private void makeImageCircular() {
        double radius = displayPicture.getFitWidth() / 2.0;

        Circle clip = new Circle(radius, radius, radius);

        displayPicture.setClip(clip);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.getStyleClass().add("user");
        return db;
    }

    public static DialogBox getIrisDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        db.getStyleClass().add("bot");
        return db;
    }

    public static DialogBox getIrisDialog(String text, Image errImg, Image img) {
        var db = new DialogBox(text, errImg, img);
        db.flip();
        db.getStyleClass().add("bot");
        return db;
    }
}
