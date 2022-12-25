package ir.ghanbari.matin;

import ir.ghanbari.matin.data.Data;
import ir.ghanbari.matin.menu.Menu;
import javafx.application.Application;
import javafx.scene.input.KeyCombination;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;


import java.util.Objects;

/**
 * @author MatinGhanbari
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Menu menu = new Menu(stage);

        //Background music
        Data.EXECUTOR_SERVICE.execute(() -> {
            AudioClip audio = new AudioClip(getClass().getClassLoader().
                    getResource("sounds/background.mp3").toString());
            audio.setVolume(0.6f);
            audio.setCycleCount(1000);
            audio.play();
        });

        //Stage setting
        setStageSetting(stage);

        menu.show();
    }

    public void setStageSetting(Stage stage) {
        stage.setTitle(Data.GAME_TITLE);
        stage.setFullScreen(true);
        stage.getIcons().add(Data.GAME_ICON);
        stage.setResizable(true);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("Q"));
        stage.setAlwaysOnTop(false);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
    }
}
