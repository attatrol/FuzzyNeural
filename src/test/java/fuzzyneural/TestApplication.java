package fuzzyneural;

import java.util.Optional;

import com.github.attatrol.fuzzyneural.anfis.Anfis;
import com.github.attatrol.fuzzyneural.ui.javafx.AnfisCreationDialog;
import com.github.attatrol.preprocessing.ui.misc.UiUtils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Test application that shows both dialogs.
 * @author atta_troll
 *
 */
public class TestApplication extends Application {

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new TestScene());
        primaryStage.show();
        primaryStage.setTitle("Test");
    }

    /**
     * Entry point
     * @param strings not in use
     */
    public static final void main(String...strings) {
        launch(strings);
    }

    private static class TestScene extends Scene {

        TestScene() {
            super(new HBox());
            HBox hBox = (HBox) getRoot();
            Button callDataSourceDialogButton = new Button("Call anfis dialog");
            callDataSourceDialogButton.setOnAction(ev-> {
                Optional<Anfis> result =
                        (new AnfisCreationDialog(5)).showAndWait();
                if (result.isPresent()) {
                    Anfis anfis = result.get();
                    UiUtils.showInfoMessage(String.format("Hey, we have an anfis\n%s", anfis));
                }
                else {
                    UiUtils.showInfoMessage("Oh no, got no anfis!");
                }
            });
            hBox.getChildren().addAll(callDataSourceDialogButton);
        }
    }

}