package tika;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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

    private Tika tikaChat;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image tikaImage = new Image(this.getClass().getResourceAsStream("/images/tika.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        Platform.runLater(() -> {
            dialogContainer.getScene().getStylesheets().add(
                    getClass().getResource("/view/style.css").toExternalForm()
            );
        });

        dialogContainer.getChildren().add(
                DialogBox.getTikaDialog(
                        "Hello! I'm Tika. How can I help you today?",
                        tikaImage
                )
        );
    }

    /** Injects the Duke instance */
    public void setTika(Tika t) {
        tikaChat = t;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = tikaChat.getResponse(input);

        if (tikaChat.shouldExit()) {
            // delay a bit so user sees the message
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getTikaDialog(response, tikaImage)
        );
        userInput.clear();
    }
}
