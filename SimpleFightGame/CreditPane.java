import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CreditPane extends VBox {

    public CreditPane() {
        Button back = new Button("Return to Title Screen");
        Text me = new Text("Nathaniel Rickertsen");

        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.getChildren().addAll(me, back);

        back.setOnAction(e -> SimpleFightingGame.setScene(Enums.Scenes.title));
    }
}
