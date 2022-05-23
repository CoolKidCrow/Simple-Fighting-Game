import javafx.animation.AnimationTimer;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GamePane extends BorderPane {

    private static Sprite player1;
    private static Sprite player2;

    private static boolean showSauce;

    private static boolean gameOver;

    private static AnimationTimer timer;

    private static Pane fightArea;

    private static HBox winnerHBox;
    private static ImageView player1Win;
    private static ImageView player2Win;
    private static ImageView winsTitle;

    private static ImageView player1Title;
    private static ImageView player2Title;
    private static Rectangle player1Health;
    private static Rectangle player2Health;

    public GamePane() {
        fightArea = new Pane();
        Button back = new Button("Return to Title Screen");

        player1Title = new ImageView(new Image("/Assets/CharacterSelect/Player1Title.png"));
        player2Title = new ImageView(new Image("/Assets/CharacterSelect/Player2Title.png"));
        winsTitle = new ImageView(new Image("/Assets/CharacterSelect/WinsTitle.png"));
        player1Win = new ImageView(new Image("/Assets/CharacterSelect/Player1Title.png"));
        player2Win = new ImageView(new Image("/Assets/CharacterSelect/Player2Title.png"));

        player1Health = new Rectangle(40, 5, 0, 50);
        player2Health = new Rectangle(650, 5, 0, 50);
        player1Health.setFill(Color.ORANGE);
        player2Health.setFill(Color.ORANGE);

        player1Title.relocate(25, 30);
        player2Title.relocate(1010, 30);
        winnerHBox = new HBox();
        winnerHBox.relocate(420, 80);
        fightArea.getChildren().addAll(winnerHBox, player1Title, player2Title, player1Health, player2Health);

        this.setTop(back);
        this.setCenter(fightArea);

        this.setOnKeyPressed(e -> inputHandler(e.getCode(), e.getEventType()));
        this.setOnKeyReleased(e -> inputHandler(e.getCode(), e.getEventType()));

        back.setOnAction(e -> SimpleFightingGame.setScene(Enums.Scenes.title));

        timer = new AnimationTimer() {
            public void handle(long now) {
                update();
            }
        };
    }

    public void startTimer() {
        timer.start();
        player1.startTimer();
        player2.startTimer();
    }

    public void stopTimer() {
        timer.stop();
        player1.stopTimer();
        player2.stopTimer();
    }

    public static void setGamePane(Enums.map map, Enums.character player1Char, Enums.character player2Char) {
        if (map == Enums.map.japan) {
            fightArea.setBackground(new Background(
                    new BackgroundImage(new Image("/Assets/BackgroundStages/Background-Stage-Ryu-Japan.gif"),
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                            new BackgroundSize(620, 224, false, false, true, false))));
        } else if (map == Enums.map.usa) {
            fightArea.setBackground(new Background(
                    new BackgroundImage(new Image("/Assets/BackgroundStages/Background-Stage-Ken-USA.gif"),
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                            new BackgroundSize(620, 224, false, false, true, false))));
        }
        if (player1 != null) {
            fightArea.getChildren().setAll();
            winnerHBox.getChildren().setAll();
            fightArea.getChildren().addAll(winnerHBox, player1Title, player2Title, player1Health, player2Health);
        }
        if (player1Char == Enums.character.ken) {
            player1 = new Ken(showSauce);
        } else if (player1Char == Enums.character.ryu) {
            player1 = new Ryu(showSauce);
        }
        if (player2Char == Enums.character.ken) {
            player2 = new Ken(showSauce);
        } else if (player2Char == Enums.character.ryu) {
            player2 = new Ryu(showSauce);
        }
        fightArea.getChildren().addAll(player1, player2);
        gameOver = false;
        resetStage();
        for (Box collider : player1.colliders) {
            fightArea.getChildren().add(collider);
        }
        for (Box collider : player2.colliders) {
            fightArea.getChildren().add(collider);
        }
    }

    public static void setSauce(boolean bool) {
        showSauce = bool;
    }

    public static void resetStage() {
        player1.setTranslateX(300);
        player1.setTranslateY(275);
        player2.setTranslateX(750);
        player2.setTranslateY(275);
    }

    private void update() {
        if (gameOver) {
            return;
        }

        directionHandler();
        collisionHandler();
        updateHealthBar();
    }

    private void updateHealthBar() {
        player1Health.setWidth(550 * (player1.health / 100f));
        player1Health.setTranslateX(0 + (550 - (550 * (player1.health / 100f))));

        player2Health.setWidth(550 * (player2.health / 100f));
    }

    public static void checkGameOver() {
        if (player1.health <= 0) {
            player1.isGameOver = true;
            player2.isGameOver = true;
            player1.playLoser();
            player2.playWinner();
            winnerHBox.getChildren().addAll(player2Win, winsTitle);
            gameOver = true;
        } else if (player2.health <= 0) {
            player1.isGameOver = true;
            player2.isGameOver = true;
            player2.playLoser();
            player1.playWinner();
            winnerHBox.getChildren().addAll(player1Win, winsTitle);
            gameOver = true;
        }
    }

    private void collisionHandler() {
        if (player1.getCurrentCollider() == null || player2.getCurrentCollider() == null) {
            return;
        }
        if (player1.getCurrentCollider().intersects(player2.getCurrentCollider())) {
            if (0 < player1.getTranslateX() && player1.getTranslateX() < 1100) {
                player1.setTranslateX(player1.getTranslateX() + (1.2 * player1.getDirection() * -1));
            }
            if (0 < player2.getTranslateX() && player2.getTranslateX() < 1100) {
                player2.setTranslateX(player2.getTranslateX() + (1.2 * player2.getDirection() * -1));
            }
        }

        if (player1.getCurrentAttack() != null) {
            if (player1.getCurrentAttack().intersects(player2.getCurrentCollider())) {
                if (player1.getHasHit()) {
                    player1.setHasHit(false);
                    player2.changeHealth(player1.currentAttack.getType());
                }
                if (0 < player2.getTranslateX() && player2.getTranslateX() < 1100) {
                    player2.setTranslateX(player2.getTranslateX() + (1.2 * player2.getDirection() * -1));
                }
            }
        }

        if (player2.getCurrentAttack() != null) {
            if (player2.getCurrentAttack().intersects(player1.getCurrentCollider())) {
                if (player2.getHasHit()) {
                    player2.setHasHit(false);
                    player1.changeHealth(player2.currentAttack.getType());
                }
                if (0 < player1.getTranslateX() && player1.getTranslateX() < 1100) {
                    player1.setTranslateX(player1.getTranslateX() + (1.2 * player1.getDirection() * -1));
                }
            }
        }
    }

    private void directionHandler() {
        double dir = player1.getTranslateX() - player2.getTranslateX();
        if (dir > 0) {
            player1.setDirection(-1);
            player2.setDirection(1);
        } else if (dir < 0) {
            player1.setDirection(1);
            player2.setDirection(-1);
        }
    }

    private void inputHandler(KeyCode key, EventType<KeyEvent> type) {
        if (gameOver) {
            return;
        }
        boolean x = (type == KeyEvent.KEY_PRESSED) ? true : false;
        switch (key) {
            case D:
                player1.setAction(Enums.Inputs.right, x);
                break;
            case A:
                player1.setAction(Enums.Inputs.left, x);
                break;
            case W:
                player1.setAction(Enums.Inputs.jump, x);
                break;
            case S:
                player1.setAction(Enums.Inputs.crouch, x);
                break;
            case B:
                player1.setAction(Enums.Inputs.punch, x);
                break;
            case N:
                player1.setAction(Enums.Inputs.kick, x);
                break;
            case M:
                player1.setAction(Enums.Inputs.block, x);
                break;
            case L:
                player2.setAction(Enums.Inputs.right, x);
                break;
            case J:
                player2.setAction(Enums.Inputs.left, x);
                break;
            case I:
                player2.setAction(Enums.Inputs.jump, x);
                break;
            case K:
                player2.setAction(Enums.Inputs.crouch, x);
                break;
            case Z:
                player2.setAction(Enums.Inputs.punch, x);
                break;
            case X:
                player2.setAction(Enums.Inputs.kick, x);
                break;
            case C:
                player2.setAction(Enums.Inputs.block, x);
                break;
            default:
                break;
        }
    }

}
