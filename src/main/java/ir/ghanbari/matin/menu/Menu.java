package ir.ghanbari.matin.menu;

import ir.ghanbari.matin.data.Data;
import ir.ghanbari.matin.game.GameScreen;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


/**
 * @author MatinGhanbari
 */
public class Menu {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public Menu(Stage stage) {
        this.stage = stage;
        Data.GAME_MENU = this;
    }

    public void show() {
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml_files/menu.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        scene = new Scene(root);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
        });
        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.show();
        });

    }

    public void startGame() {
        GameScreen gameScreen = new GameScreen(stage);
        gameScreen.show();
    }
}
