package ir.ghanbari.matin.menu;

import io.github.gleidson28.GNAvatarView;
import ir.ghanbari.matin.data.Data;
import ir.ghanbari.matin.util.GameMode;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.*;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author MatinGhanbari
 */
public class MenuController implements Initializable {
    public AnchorPane pane_main;
    public Button btn_beginning_menu;
    public Button btn_player_one_menu;
    public Button btn_player_two_menu;
    public Button btn_start_menu;
    public AnchorPane pane_beginning;
    public Button btn_start;
    public Button btn_exit;
    public Slider slider_map_size;
    public Slider slider_game_rounds;
    public AnchorPane pane_player_two;
    public GNAvatarView avatar_player_two;
    public TextField textField_player_two_name;
    public ComboBox<String> comboBox_player_two_avatar;
    public ColorPicker colorPicker_player_two_color;
    public AnchorPane pane_player_one;
    public GNAvatarView avatar_player_one;
    public TextField textField_player_one_name;
    public ComboBox<String> comboBox_player_one_avatar;
    public ColorPicker colorPicker_player_one_color;
    public AnchorPane pane_start;
    public Label label_loading;
    public ProgressBar progress_loading;
    public ComboBox<GameMode> comboBox_game_mode;
    public GNAvatarView avatar_computer;
    public TextField textField_computer_name;
    public ColorPicker colorPicker_computer_color;
    public Slider slider_computer_level;
    public AnchorPane pane_computer;
    public CheckBox checkBox_level_by_level;
    private AnchorPane currentPane;
    private Button currentMenuButton;
    private final ArrayList<String> avatarsNames = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Data.MENU_CONTROLLER = this;

        avatar_player_one.setImage(new Image(getClass().getClassLoader().
                getResource("new-avatars/boy-1.png").toString()));
        avatar_player_two.setImage(new Image(getClass().getClassLoader().
                getResource("new-avatars/girl-1.png").toString()));

        ArrayList<GameMode> gameModes = new ArrayList<>();
        gameModes.add(GameMode.PVP);
        gameModes.add(GameMode.PVC);
        comboBox_game_mode.setItems(FXCollections.observableList(gameModes));

        avatarsNames.add("Boy-1");
        avatarsNames.add("Boy-2");
        avatarsNames.add("Boy-3");
        avatarsNames.add("Boy-4");
        avatarsNames.add("Boy-5");
        avatarsNames.add("Boy-6");
        avatarsNames.add("Boy-7");
        avatarsNames.add("Boy-8");
        avatarsNames.add("Girl-1");
        avatarsNames.add("Girl-2");
        avatarsNames.add("Girl-3");
        avatarsNames.add("Girl-4");
        avatarsNames.add("Girl-5");
        avatarsNames.add("Girl-6");
        avatarsNames.add("Girl-7");
        avatarsNames.add("Girl-8");
        avatarsNames.add("Girl-9");
        comboBox_player_one_avatar.setItems(FXCollections.observableList(avatarsNames));
        comboBox_player_two_avatar.setItems(FXCollections.observableList(avatarsNames));
        pane_main.getChildren().removeAll(pane_beginning, pane_player_one, pane_player_two, pane_start, pane_computer);
        pane_main.getChildren().add(pane_beginning);
        currentPane = pane_beginning;
        btn_beginning_menu.setTextFill(Color.BLACK);
        currentMenuButton = btn_beginning_menu;
        progress_loading.setOpacity(0);
        label_loading.setOpacity(0);

        pane_beginning.setOpacity(1);
        pane_player_one.setOpacity(1);
        pane_player_two.setOpacity(1);
        pane_computer.setOpacity(1);
        pane_start.setOpacity(1);
    }

    public void showBeginningPage(MouseEvent mouseEvent) {
        Data.EXECUTOR_SERVICE.execute(() -> {
            AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/menu_button.mp3").toString());
            audio.setVolume(0.5f);
            audio.play();
        });
        if (currentPane == pane_beginning) {
            return;
        }
        pane_main.getChildren().remove(currentPane);
        pane_main.getChildren().add(pane_beginning);
        currentPane = pane_beginning;

        btn_beginning_menu.setStyle("-fx-background-color: rgba(80, 0, 80, 0.2);");
        btn_beginning_menu.setTextFill(Color.BLACK);
        currentMenuButton.setStyle("-fx-background-color: transparent;");
        currentMenuButton.setTextFill(Color.WHITE);
        currentMenuButton = btn_beginning_menu;
    }

    public void showPlayerOnePage(MouseEvent mouseEvent) {
        Data.EXECUTOR_SERVICE.execute(() -> {
            AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/menu_button.mp3").toString());
            audio.setVolume(0.5f);
            audio.play();
        });
        if (currentPane == pane_player_one) {
            return;
        }
        pane_main.getChildren().remove(currentPane);
        pane_main.getChildren().add(pane_player_one);
        currentPane = pane_player_one;

        btn_player_one_menu.setStyle("-fx-background-color: rgba(80, 0, 80, 0.2);");
        btn_player_one_menu.setTextFill(Color.BLACK);
        currentMenuButton.setStyle("-fx-background-color: transparent;");
        currentMenuButton.setTextFill(Color.WHITE);
        currentMenuButton = btn_player_one_menu;
    }

    public void showPlayerTwoPage(MouseEvent mouseEvent) {
        Data.EXECUTOR_SERVICE.execute(() -> {
            AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/menu_button.mp3").toString());
            audio.setVolume(0.5f);
            audio.play();
        });
        if (currentPane == pane_computer || currentPane == pane_player_two) {
            return;
        }
        pane_main.getChildren().remove(currentPane);

        GameMode gameMode = comboBox_game_mode.getValue();
        if (gameMode == null) {
            gameMode = GameMode.PVP;
        }
        switch (gameMode) {
            case PVC:
                pane_main.getChildren().add(pane_computer);
                currentPane = pane_computer;
                break;
            case PVP:
            default:
                pane_main.getChildren().add(pane_player_two);
                currentPane = pane_player_two;
                break;
        }

        btn_player_two_menu.setStyle("-fx-background-color: rgba(80, 0, 80, 0.2);");
        btn_player_two_menu.setTextFill(Color.BLACK);
        currentMenuButton.setStyle("-fx-background-color: transparent;");
        currentMenuButton.setTextFill(Color.WHITE);
        currentMenuButton = btn_player_two_menu;
    }

    public void showStartPage(MouseEvent mouseEvent) {
        Data.EXECUTOR_SERVICE.execute(() -> {
            AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/menu_button.mp3").toString());
            audio.setVolume(0.5f);
            audio.play();
        });
        if (currentPane == pane_start) {
            return;
        }
        pane_main.getChildren().remove(currentPane);
        pane_main.getChildren().add(pane_start);
        currentPane = pane_start;

        btn_start_menu.setStyle("-fx-background-color: rgba(80, 0, 80, 0.2);");
        btn_start_menu.setTextFill(Color.BLACK);
        currentMenuButton.setStyle("-fx-background-color: transparent;");
        currentMenuButton.setTextFill(Color.WHITE);
        currentMenuButton = btn_start_menu;
    }

    public void exitGame(MouseEvent mouseEvent) {
        Data.EXECUTOR_SERVICE.execute(() -> {
            AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/menu_button.mp3").toString());
            audio.setVolume(0.5f);
            audio.play();
        });
        System.exit(0);
    }

    public void SetPlayerOneAvatar(ActionEvent contextMenuEvent) {
        Data.EXECUTOR_SERVICE.execute(() -> {
            AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/menu_button.mp3").toString());
            audio.setVolume(0.5f);
            audio.play();
        });
        String avatarName = comboBox_player_one_avatar.getValue().toLowerCase().trim();
        avatar_player_one.setImage(new Image(getClass().getClassLoader().
                getResource("new-avatars/" + avatarName + ".png").toString()));
        Data.PLAYER_ONE_AVATAR_VIEW = avatar_player_one;
    }

    public void SetPlayerTwoAvatar(ActionEvent contextMenuEvent) {
        Data.EXECUTOR_SERVICE.execute(() -> {
            AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/menu_button.mp3").toString());
            audio.setVolume(0.5f);
            audio.play();
        });
        String avatarName = comboBox_player_two_avatar.getValue().toLowerCase().trim();
        avatar_player_two.setImage(new Image(getClass().getClassLoader().
                getResource("new-avatars/" + avatarName + ".png").toString()));
        Data.PLAYER_TWO_AVATAR_VIEW = avatar_player_two;
    }

    public void SetPlayerOneColor(ContextMenuEvent contextMenuEvent) {
        Data.EXECUTOR_SERVICE.execute(() -> {
            AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/menu_button.mp3").toString());
            audio.setVolume(0.5f);
            audio.play();
        });
        Data.PLAYER_ONE_COLOR = colorPicker_player_one_color.getValue();
    }

    public void setPlayerTwoColor(ContextMenuEvent contextMenuEvent) {
        Data.EXECUTOR_SERVICE.execute(() -> {
            AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/menu_button.mp3").toString());
            audio.setVolume(0.5f);
            audio.play();
        });
        Data.PLAYER_TWO_COLOR = colorPicker_player_two_color.getValue();
    }

    public void setGameMode(ActionEvent actionEvent) {
        Data.GAME_MODE = comboBox_game_mode.getValue();
        switch (comboBox_game_mode.getValue()) {
            case PVP:
                avatar_player_two.setImage(new Image(getClass().getClassLoader().
                        getResource("new-avatars/girl-1.png").toString()));
                comboBox_player_two_avatar.setDisable(false);
                comboBox_player_two_avatar.setOpacity(1);
                textField_player_two_name.setText("");
                textField_player_two_name.setDisable(false);
                textField_player_two_name.setDisable(false);
                btn_player_two_menu.setText("PLAYER TWO");
                break;
            case PVC:
                avatar_player_two.setImage(new Image(getClass().getClassLoader().
                        getResource("new-avatars/bot.png").toString()));
                comboBox_player_two_avatar.setDisable(true);
                comboBox_player_two_avatar.setOpacity(0.5);
                textField_player_two_name.setText("Computer A.I.");
                textField_player_two_name.setDisable(true);
                textField_player_two_name.setDisable(true);
                btn_player_two_menu.setText("Computer A.I.");
                checkBox_level_by_level.setSelected(false);
                slider_computer_level.setOpacity(1);
                slider_computer_level.setDisable(false);
                break;
        }
    }

    public void CheckLevelByLevel(MouseEvent mouseEvent) {
        if (checkBox_level_by_level.isSelected()) {
            Data.GAME_MODE = GameMode.PVC_LEVEL_BY_LEVEL;
            slider_computer_level.setDisable(true);
            slider_computer_level.setOpacity(0.5);
        } else {
            Data.GAME_MODE = GameMode.PVC;
            slider_computer_level.setDisable(false);
            slider_computer_level.setOpacity(1);
        }
        slider_computer_level.setValue(0);
        Data.COMPUTER_LEVEL = 0;
    }

    public void setComputerColor(ContextMenuEvent contextMenuEvent) {
        Data.EXECUTOR_SERVICE.execute(() -> {
            AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/menu_button.mp3").toString());
            audio.setVolume(0.5f);
            audio.play();
        });
        Data.PLAYER_TWO_COLOR = colorPicker_computer_color.getValue();
    }

    public void setGameLevelSlider(MouseEvent mouseEvent) {
        if (!checkBox_level_by_level.isSelected()) {
            Data.COMPUTER_LEVEL = (int) slider_computer_level.getValue();
        }
    }

    public void startGame(MouseEvent mouseEvent) {
        Data.EXECUTOR_SERVICE.execute(() -> {
            AudioClip audio = new AudioClip(getClass().getClassLoader().getResource("sounds/menu_button.mp3").toString());
            audio.setVolume(0.5f);
            audio.play();
        });
        Data.EXECUTOR_SERVICE.execute(() -> {
            Platform.runLater(() -> {
                label_loading.setOpacity(1);
                progress_loading.setOpacity(1);
                progress_loading.setProgress(0);
            });
            Data.PLAYER_ONE_COLOR = colorPicker_player_one_color.getValue();
            Data.PLAYER_TWO_COLOR = colorPicker_player_two_color.getValue();
            Data.PLAYER_ONE_AVATAR_VIEW = avatar_player_one;
            Data.PLAYER_TWO_AVATAR_VIEW = avatar_player_two;
            if (!textField_player_one_name.getText().equalsIgnoreCase("")) {
                Data.PLAYER_ONE_NAME = textField_player_one_name.getText();
            }
            if (!textField_player_two_name.getText().equalsIgnoreCase("")) {
                Data.PLAYER_TWO_NAME = textField_player_two_name.getText();
            }
            Data.GAME_ROUNDS = (int) slider_game_rounds.getValue();
            Data.MAP_SIZE = (int) slider_map_size.getValue();

            double p = 0.3;
            do {
                double finalP = p;
                Platform.runLater(() -> progress_loading.setProgress(finalP));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                p += p / 4;
            } while (progress_loading.getProgress() < 0.95);

            Data.EXECUTOR_SERVICE.execute(Data.GAME_MENU::startGame);
        });
    }
}
