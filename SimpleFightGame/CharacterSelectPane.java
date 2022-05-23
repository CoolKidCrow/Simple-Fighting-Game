import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CharacterSelectPane extends BorderPane {

    private Enums.character player1;
    private Enums.character player2;
    private Enums.map map;

    public CharacterSelectPane() {
        setStyle("-fx-background-color: black");
        ImageView player1Title = new ImageView(new Image("/Assets/CharacterSelect/Player1Title.png"));
        ImageView player2Title = new ImageView(new Image("/Assets/CharacterSelect/Player2Title.png"));
        ImageView map1 = new ImageView(new Image("/Assets/BackgroundStages/Background-Stage-Ken-USA.gif"));
        ImageView map2 = new ImageView(new Image("/Assets/BackgroundStages/Background-Stage-Ryu-Japan.gif"));
        ImageView mapSelectImage = new ImageView(new Image("/Assets/CharacterSelect/MapSelect.png"));
        ImageView ken = new ImageView(new Image("/Assets/CharacterSelect/KenMugshot.png"));
        ImageView ryu = new ImageView(new Image("/Assets/CharacterSelect/RyuMugshot.png"));
        ImageView ken2 = new ImageView(new Image("/Assets/CharacterSelect/KenMugshot.png"));
        ImageView ryu2 = new ImageView(new Image("/Assets/CharacterSelect/RyuMugshot.png"));
        ImageView start = new ImageView(new Image("/Assets/CharacterSelect/StartTitle.png"));
        ImageView selector = new ImageView(new Image("/Assets/CharacterSelect/Selector.png"));
        ken2.setScaleX(-1);
        ryu2.setScaleX(-1);

        Button kenButton = new Button("", ken);
        kenButton.setStyle("-fx-background-color: transparent");
        Button ryuButton = new Button("", ryu);
        ryuButton.setStyle("-fx-background-color: transparent");
        Button kenButton2 = new Button("", ken2);
        kenButton2.setStyle("-fx-background-color: transparent");
        Button ryuButton2 = new Button("", ryu2);
        ryuButton2.setStyle("-fx-background-color: transparent");

        Button map1Button = new Button("", map1);
        map1Button.setStyle("-fx-background-color: transparent");
        Button map2Button = new Button("", map2);
        map2Button.setStyle("-fx-background-color: transparent");

        Button startButton = new Button("", start);
        startButton.setStyle("-fx-background-color: transparent");

        map1.setFitWidth(300);
        map1.setPreserveRatio(true);
        map2.setFitWidth(300);
        map2.setPreserveRatio(true);

        VBox player1VBox = new VBox(20, player1Title, kenButton, ryuButton);
        VBox player2VBox = new VBox(20, player2Title, kenButton2, ryuButton2);
        VBox mapSelectVBox = new VBox(15, mapSelectImage, map1Button, map2Button);
        HBox startHBox = new HBox(10, startButton);

        mapSelectVBox.setAlignment(Pos.CENTER);
        player1VBox.setAlignment(Pos.TOP_CENTER);
        player2VBox.setAlignment(Pos.TOP_CENTER);
        startHBox.setAlignment(Pos.CENTER);

        Border border = new Border(
                new BorderStroke(Color.ORANGE, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        kenButton.setOnAction(e -> {
            player1 = Enums.character.ken;
            kenButton.setBorder(border);
            ryuButton.setBorder(Border.EMPTY);
        });
        ryuButton.setOnAction(e -> {
            player1 = Enums.character.ryu;
            ryuButton.setBorder(border);
            kenButton.setBorder(Border.EMPTY);
        });
        kenButton2.setOnAction(e -> {
            player2 = Enums.character.ken;
            kenButton2.setBorder(border);
            ryuButton2.setBorder(Border.EMPTY);
        });
        ryuButton2.setOnAction(e -> {
            player2 = Enums.character.ryu;
            ryuButton2.setBorder(border);
            kenButton2.setBorder(Border.EMPTY);
        });
        map1Button.setOnAction(e -> {
            map = Enums.map.usa;
            map1Button.setBorder(border);
            map2Button.setBorder(Border.EMPTY);
        });
        map2Button.setOnAction(e -> {
            map = Enums.map.japan;
            map2Button.setBorder(border);
            map1Button.setBorder(Border.EMPTY);
        });

        startButton.setOnAction(e -> {
            if (player1 != null && player2 != null && map != null) {
                GamePane.setGamePane(map, player1, player2);
                SimpleFightingGame.setScene(Enums.Scenes.game);
            }
        });

        startButton.setOnMouseEntered(e -> startHBox.getChildren().add(selector));
        startButton.setOnMouseExited(e -> startHBox.getChildren().remove(selector));

        setRight(player2VBox);
        setLeft(player1VBox);
        setCenter(mapSelectVBox);
        setBottom(startHBox);
    }
}
