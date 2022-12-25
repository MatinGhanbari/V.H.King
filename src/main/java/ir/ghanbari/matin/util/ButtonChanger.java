package ir.ghanbari.matin.util;


import ir.ghanbari.matin.data.Data;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;


/**
 * @author MatinGhanbari
 */
public class ButtonChanger {
    public static void changeButton(Button button) {
        Data.EXECUTOR_SERVICE.execute(() -> {
            AudioClip audio = new AudioClip(ButtonChanger.class.getClassLoader().getResource("sounds/game_button.wav").toString());
            audio.setVolume(0.3f);
            audio.play();
        });
        Data.EXECUTOR_SERVICE.execute(() -> {
            Platform.runLater(() -> button.setBackground(getBack(Data.BUTTON_SELECTED_BACK_1)));
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> button.setBackground(getBack(Data.BUTTON_SELECTED_BACK_2)));
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> button.setBackground(getBack(Data.BUTTON_SELECTED_BACK_3)));
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> button.setBackground(getBack(Data.BUTTON_SELECTED_BACK_4)));
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> button.setBackground(getBack(Data.BUTTON_SELECTED_BACK_5)));
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> button.setBackground(getBack(Data.BUTTON_SELECTED_BACK)));
        });
    }

    public static Background getBack(Image image) {
        return new Background(new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(((double) Data.RECT_SIZE / Data.MAP_SIZE), ((double) Data.RECT_SIZE / Data.MAP_SIZE),
                        true, true, true, true)));
    }
}
