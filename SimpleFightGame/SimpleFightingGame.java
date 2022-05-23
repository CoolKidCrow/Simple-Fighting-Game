
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SimpleFightingGame extends Application {
    public int resX = 1242, resY = 473; // actual SF res if u ever wanna add cam pan and zoom 384x224

    private static Stage stage;
    private static Scene titleScene, helpScene, creditScene, gameScene, charScene;
    private static GamePane gamePane;

    public void start(Stage stage) {
        SimpleFightingGame.stage = stage;
        SimpleFightingGame.stage.setResizable(false);

        TitleScreenPane titleScreenPane = new TitleScreenPane();
        HelpPane helpPane = new HelpPane();
        CreditPane creditPane = new CreditPane();
        CharacterSelectPane charPane = new CharacterSelectPane();

        gamePane = new GamePane();

        titleScene = new Scene(titleScreenPane, resX, resY);
        helpScene = new Scene(helpPane, resX, resY);
        creditScene = new Scene(creditPane, resX, resY);
        charScene = new Scene(charPane, resX, resY);
        gameScene = new Scene(gamePane, resX, resY);

        stage.setTitle("Simple Fighting Game");
        stage.setScene(titleScene);
        stage.show();
    }

    public static void setScene(Enums.Scenes scene) {
        switch (scene) {
            case title:
                stage.setScene(titleScene);
                break;
            case help:
                stage.setScene(helpScene);
                break;
            case credits:
                stage.setScene(creditScene);
                break;
            case game:
                GamePane.resetStage();
                gamePane.startTimer();
                stage.setScene(gameScene);
                break;
            case characterSelect:
                stage.setScene(charScene);
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
