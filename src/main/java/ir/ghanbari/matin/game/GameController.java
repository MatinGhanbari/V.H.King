package ir.ghanbari.matin.game;

import io.github.gleidson28.GNAvatarView;
import ir.ghanbari.matin.data.Data;
import ir.ghanbari.matin.util.GameMode;
import ir.ghanbari.matin.util.Turn;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author MatinGhanbari
 */
public class GameController implements Initializable {
//    public GNAvatarView avatar_player_one;
//    public GNAvatarView avatar_player_two;
    public Label label_player_one_name;
    public Label label_player_two_name;
    public ProgressBar progress_player_one_last_move_score;
    public ProgressBar progress_player_one_game_state;
    public ProgressBar progress_player_two_last_move_score;
    public ProgressBar progress_player_two_game_state;
    public Button btn_restart;
    public Button btn_exit;
    public GridPane gridPane;
    public Rectangle rect;
    public Label label_finish;
    public ImageView label_finish_image;
    public Label label_computer_level;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Data.GAME_CONTROLLER = this;

        progress_player_one_last_move_score.setProgress(0);
        progress_player_one_last_move_score.setStyle("-fx-accent: gold");
        progress_player_one_game_state.setProgress(0);
        progress_player_one_game_state.setStyle("-fx-accent: " + toHexString(Data.PLAYER_ONE_COLOR));
        progress_player_two_last_move_score.setProgress(0);
        progress_player_two_last_move_score.setStyle("-fx-accent: gold");
        progress_player_two_game_state.setProgress(0);
        progress_player_two_game_state.setStyle("-fx-accent: " + toHexString(Data.PLAYER_TWO_COLOR));

        label_player_one_name.setText(Data.PLAYER_ONE_NAME);
        label_player_two_name.setText(Data.PLAYER_TWO_NAME);
//        avatar_player_one.setImage(Data.PLAYER_ONE_AVATAR_VIEW.getImage());
//        avatar_player_two.setImage(Data.PLAYER_TWO_AVATAR_VIEW.getImage());

        if (Data.GAME_MODE == GameMode.PVC || Data.GAME_MODE == GameMode.PVC_LEVEL_BY_LEVEL) {
            label_computer_level.setDisable(false);
            label_computer_level.setOpacity(1);
            label_computer_level.setText("Level : " + Data.COMPUTER_LEVEL);
        } else {
            label_computer_level.setDisable(true);
            label_computer_level.setOpacity(0);
            label_computer_level.setText("");
        }

        Data.GAME_SCREEN.setGridPane(gridPane);
        Data.GAME_SCREEN.drawGame();
    }

    public String toHexString(Color color) {
        String str = String.format("#%02X%02X%02X", ((int) color.getRed()) * 255,
                ((int) color.getGreen()) * 255, ((int) color.getBlue()) * 255);
        if (!Character.isDigit(str.replaceAll("#", "").charAt(0)) &&
                str.replaceAll("#", "").charAt(0) == str.replaceAll("#", "").charAt(2) &&
                str.replaceAll("#", "").charAt(2) == str.replaceAll("#", "").charAt(4)) {
            return "#a6a6a6";
        }
        return str;
    }

    public void restartGame(MouseEvent mouseEvent) {

        if (btn_restart.getText().equalsIgnoreCase("start")) {
            Data.EXECUTOR_SERVICE.execute(() -> {
                AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/fight.mp3").toString());
                audio.setVolume(0.5f);
                audio.play();
                Platform.runLater(() -> {
                    Data.GAME_SCREEN.getRoot().getChildren().removeAll(rect, label_finish);
                });
            });
            btn_restart.setText("restart");
        } else if (btn_restart.getText().equalsIgnoreCase("restart")) {
            Data.EXECUTOR_SERVICE.execute(() -> {
                AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/menu_button.mp3").toString());
                audio.setVolume(0.5f);
                audio.play();
            });
            Data.GAME_SET = 1;
            Data.FIRST_SET_P1_SCORE = 0;
            Data.FIRST_SET_P2_SCORE = 0;
            Data.GAME_SCREEN.restartGame();
        } else if (btn_restart.getText().equalsIgnoreCase("Set 2")) {
            Data.EXECUTOR_SERVICE.execute(() -> {
                AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/menu_button.mp3").toString());
                audio.setVolume(0.5f);
                audio.play();
            });
            Data.GAME_SET = 2;
            Data.GAME_SCREEN.restartGame();
        } else {
            Data.EXECUTOR_SERVICE.execute(() -> {
                AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/menu_button.mp3").toString());
                audio.setVolume(0.5f);
                audio.play();
            });
            Data.GAME_SET = 1;
            Data.FIRST_SET_P1_SCORE = 0;
            Data.FIRST_SET_P2_SCORE = 0;
            Data.COMPUTER_LEVEL++;
            Data.GAME_SCREEN.restartGame();
        }
    }

    public void exit(MouseEvent mouseEvent) {
        Data.EXECUTOR_SERVICE.execute(() -> {
            AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/menu_button.mp3").toString());
            audio.setVolume(0.5f);
            audio.play();
        });
        System.exit(0);
    }
}
