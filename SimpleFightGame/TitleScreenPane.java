import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TitleScreenPane extends VBox {

    public TitleScreenPane() {
        Button start = new Button("", new ImageView(new Image("/Assets/TitleScreen/StartButton.png")));
        Button help = new Button("", new ImageView(new Image("/Assets/TitleScreen/HelpButton.png")));
        Button credit = new Button("", new ImageView(new Image("/Assets/TitleScreen/CreditsButton.png")));
        start.setStyle("-fx-background-color: transparent");
        help.setStyle("-fx-background-color: transparent");
        credit.setStyle("-fx-background-color: transparent");
        setStyle("-fx-background-color: black");

        ImageView titleImage = new ImageView(new Image("/Assets/TitleScreen/TitleScreen.png"));
        ImageView selector = new ImageView(new Image("/Assets/TitleScreen/Selector.png"));

        HBox startHbox = new HBox(0, start);
        HBox helpHbox = new HBox(0, help);
        HBox creditHBox = new HBox(0, credit);

        startHbox.alignmentProperty().set(Pos.CENTER);
        helpHbox.alignmentProperty().set(Pos.CENTER);
        creditHBox.alignmentProperty().set(Pos.CENTER);

        getChildren().addAll(titleImage, startHbox, helpHbox, creditHBox);
        setSpacing(4);
        setAlignment(Pos.CENTER);

        start.setOnMouseEntered(e -> startHbox.getChildren().add(selector));
        start.setOnMouseExited(e -> startHbox.getChildren().remove(selector));
        help.setOnMouseEntered(e -> helpHbox.getChildren().add(selector));
        help.setOnMouseExited(e -> helpHbox.getChildren().remove(selector));
        credit.setOnMouseEntered(e -> creditHBox.getChildren().add(selector));
        credit.setOnMouseExited(e -> creditHBox.getChildren().remove(selector));

        start.setOnAction(e -> SimpleFightingGame.setScene(Enums.Scenes.characterSelect));
        help.setOnAction(e -> SimpleFightingGame.setScene(Enums.Scenes.help));
        credit.setOnAction(e -> SimpleFightingGame.setScene(Enums.Scenes.credits));
    }



}