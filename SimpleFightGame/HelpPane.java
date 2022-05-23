import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HelpPane extends VBox {

    public HelpPane() {
        Button back = new Button("Return to Title Screen");
        Text text = new Text("git gud");
        CheckBox sauceChecker = new CheckBox("Show Sauce");
        
        sauceChecker.setOnAction(e->GamePane.setSauce(sauceChecker.isSelected()));

        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.getChildren().addAll(text, sauceChecker, back);

        back.setOnAction(e -> SimpleFightingGame.setScene(Enums.Scenes.title));
    }
}
