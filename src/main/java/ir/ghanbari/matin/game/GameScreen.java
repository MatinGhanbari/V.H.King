package ir.ghanbari.matin.game;

import ir.ghanbari.matin.ai.AI;
import ir.ghanbari.matin.data.Data;
import ir.ghanbari.matin.util.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * @author MatinGhanbari
 */
public class GameScreen {
    private final Stage stage;
    private Scene scene;
    private AnchorPane root = new AnchorPane();
    private Mark[][] map;
    private SelectMode[][] selected;
    private Button[][] buttons;
    private Turn turn;
    private GridPane gridPane;
    private int round = 0;
    private GameMode gameMode = Data.GAME_MODE;
    private AI ai;
    private GameState gameState = GameState.NOT_STARTED;
    private String cheat = "";
    private Button hintButton = null;
    private int roundOneP1 = 0;
    private int roundOneP2 = 0;
    private int roundGame = 1;

    public GameScreen(Stage stage) {
        this.stage = stage;
        Data.GAME_SCREEN = this;
        initFields();
    }

    private void initFields() {
        this.turn = Turn.PLAYER_ONE;
        if (Data.GAME_SET == 2) {
            this.turn = Turn.PLAYER_TWO;
        }
        map = new Mark[Data.MAP_SIZE][];
        selected = new SelectMode[Data.MAP_SIZE][];
        for (int i = 0; i < Data.MAP_SIZE; i++) {
            map[i] = new Mark[Data.MAP_SIZE];
            selected[i] = new SelectMode[Data.MAP_SIZE];
            for (int j = 0; j < Data.MAP_SIZE; j++) {
                map[i][j] = Mark.EMPTY;
                selected[i][j] = SelectMode.NOT_SELECTED;
            }
        }
        ai = new AI(map, selected);
    }

    private void initCheat() {
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.M && cheat.equalsIgnoreCase("")) {
                cheat = "M";
            } else if (keyEvent.getCode() == KeyCode.A && cheat.equalsIgnoreCase("M")) {
                cheat = "MA";
            } else if (keyEvent.getCode() == KeyCode.T && cheat.equalsIgnoreCase("MA")) {
                cheat = "MAT";
            } else if (keyEvent.getCode() == KeyCode.I && cheat.equalsIgnoreCase("MAT")) {
                cheat = "MATI";
            } else if (keyEvent.getCode() == KeyCode.N && cheat.equalsIgnoreCase("MATI")) {
                cheat = "MATIN";
            } else {
                cheat = "";
            }
            if (cheat.equalsIgnoreCase("MATIN")) {
                cheat = "";
                ai.hint();
            }
        });
    }

    public Turn getTurn() {
        return turn;
    }

    public Button[][] getButtons() {
        return buttons;
    }

    public void show() {
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml_files/game.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {
            scene = new Scene(root);
            scene.setOnKeyPressed(keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    System.exit(0);
                }
            });
            initCheat();
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
        });
    }

    public void restartGame() {
        GameScreen gs = new GameScreen(stage);
        gs.show();
    }

    public void drawGame() {
        buttons = new Button[Data.MAP_SIZE][];
        for (int i = 0; i < Data.MAP_SIZE; i++) {
            buttons[i] = new Button[Data.MAP_SIZE];
            for (int j = 0; j < Data.MAP_SIZE; j++) {
                buttons[i][j] = new Button("");
                initButton(buttons[i][j], i, j);
                buttons[i][j].setMinWidth(((double) Data.RECT_SIZE / Data.MAP_SIZE));
                buttons[i][j].setMinHeight(((double) Data.RECT_SIZE / Data.MAP_SIZE));
                buttons[i][j].setLayoutX(Data.PANE_LEFT_GAP + (j * ((double) Data.RECT_SIZE / Data.MAP_SIZE)));
                buttons[i][j].setLayoutY(Data.PANE_UP_GAP + (i * ((double) Data.RECT_SIZE / Data.MAP_SIZE)));
                buttons[i][j].setBackground(new Background(new BackgroundImage(Data.BUTTON_NOT_SELECTED_BACK, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(((double) Data.RECT_SIZE / Data.MAP_SIZE), ((double) Data.RECT_SIZE / Data.MAP_SIZE), true, true, true, true))));
                buttons[i][j].setFont(Font.font(Data.BUTTONS_FONT, (double) 200 / Data.MAP_SIZE));
                gridPane.add(buttons[i][j], i, j);
            }
        }
        Platform.runLater(() -> {
            switch (turn) {
                case PLAYER_ONE:
                    Data.GAME_CONTROLLER.label_player_one_name.setText(Data.TURN_MARK + " " + Data.PLAYER_ONE_NAME);
                    Data.GAME_CONTROLLER.label_player_two_name.setText(Data.PLAYER_TWO_NAME);
                    break;
                case PLAYER_TWO:
                    Data.GAME_CONTROLLER.label_player_two_name.setText(Data.TURN_MARK + " " + Data.PLAYER_TWO_NAME);
                    Data.GAME_CONTROLLER.label_player_one_name.setText(Data.PLAYER_ONE_NAME);
                    break;
            }
        });
        if (this.turn == Turn.PLAYER_TWO && (gameMode == GameMode.PVC || gameMode == GameMode.PVC_LEVEL_BY_LEVEL))
            ai.move();
    }


    private void initButton(Button button, int i, int j) {
        button.setOnMouseClicked(mouseEvent -> {
            if (selected[i][j] == SelectMode.NOT_SELECTED) {
                selected[i][j] = SelectMode.SELECTED;
                selectButton(this.turn, i, j);
            }
        });
    }

    private void selectButton(Turn turn, int i, int j) {
        if (hintButton != null)
            hintButton.setBackground(ButtonChanger.getBack(Data.BUTTON_NOT_SELECTED_BACK));
        hintButton = null;
        if (gameState == GameState.NOT_STARTED) {
            gameState = GameState.RUNNING;
        }
        Platform.runLater(() -> buttons[i][j].setText(""));
        ButtonChanger.changeButton(buttons[i][j]);
        int score = 0;
        switch (turn) {
            case PLAYER_ONE:
                this.turn = Turn.PLAYER_TWO;
                Platform.runLater(() -> {
                    Data.GAME_CONTROLLER.label_player_two_name.setText(Data.TURN_MARK + " " + Data.PLAYER_TWO_NAME);
                    Data.GAME_CONTROLLER.label_player_one_name.setText(Data.PLAYER_ONE_NAME);
                });
                for (int k = 0; k < Data.MAP_SIZE; k++) {
                    if (map[i][k] == Mark.PLAYER_TWO)
                        score++;
                    map[i][k] = Mark.PLAYER_ONE;
                    int finalK = k;
                    Platform.runLater(() -> {
                        buttons[i][finalK].setText(Data.PLAYER_ONE_CHAR);
                        buttons[i][finalK].setTextFill(Data.PLAYER_ONE_COLOR);
                    });
                    if (map[k][j] == Mark.PLAYER_TWO)
                        score++;
                    map[k][j] = Mark.PLAYER_ONE;
                    int finalK1 = k;
                    Platform.runLater(() -> {
                        buttons[finalK1][j].setText(Data.PLAYER_ONE_CHAR);
                        buttons[finalK1][j].setTextFill(Data.PLAYER_ONE_COLOR);
                    });
                }
                int finalScore = score;
                Platform.runLater(() -> {
                    Data.GAME_CONTROLLER.progress_player_one_last_move_score.setProgress((double) finalScore / (Data.MAP_SIZE * 2));
                    if (finalScore == 0)
                        Data.GAME_CONTROLLER.progress_player_one_last_move_score.setProgress(1);
                });
                if (Data.GAME_SET == 2)
                    round++;
                countPlayersMarks();
                break;
            case PLAYER_TWO:
                this.turn = Turn.PLAYER_ONE;
                Platform.runLater(() -> {
                    Data.GAME_CONTROLLER.label_player_one_name.setText(Data.TURN_MARK + " " + Data.PLAYER_ONE_NAME);
                    Data.GAME_CONTROLLER.label_player_two_name.setText(Data.PLAYER_TWO_NAME);
                });
                for (int k = 0; k < Data.MAP_SIZE; k++) {
                    if (map[i][k] == Mark.PLAYER_ONE)
                        score++;
                    map[i][k] = Mark.PLAYER_TWO;
                    int finalK = k;
                    Platform.runLater(() -> {
                        buttons[i][finalK].setText(Data.PLAYER_TWO_CHAR);
                        buttons[i][finalK].setTextFill(Data.PLAYER_TWO_COLOR);
                    });
                    if (map[k][j] == Mark.PLAYER_ONE)
                        score++;
                    map[k][j] = Mark.PLAYER_TWO;
                    int finalK1 = k;
                    Platform.runLater(() -> {
                        buttons[finalK1][j].setText(Data.PLAYER_TWO_CHAR);
                        buttons[finalK1][j].setTextFill(Data.PLAYER_TWO_COLOR);
                    });
                }
                int finalScore1 = score;
                Platform.runLater(() -> Data.GAME_CONTROLLER.progress_player_two_last_move_score
                        .setProgress((double) finalScore1 / (Data.MAP_SIZE * 2)));
                countPlayersMarks();
                if (Data.GAME_SET == 1)
                    round++;
                break;
        }
        checkGameState();
        if (this.turn == Turn.PLAYER_TWO && (gameMode == GameMode.PVC || gameMode == GameMode.PVC_LEVEL_BY_LEVEL))
            ai.move();
    }

    private void countPlayersMarks() {
        int countP1 = 0;
        int countP2 = 0;
        for (int i = 0; i < Data.MAP_SIZE; i++) {
            for (int j = 0; j < Data.MAP_SIZE; j++) {
                if (map[i][j] == Mark.PLAYER_ONE) {
                    countP1++;
                }
                if (map[i][j] == Mark.PLAYER_TWO) {
                    countP2++;
                }
            }
        }
        int finalCountP = countP1;
        int finalCountP1 = countP2;
        Platform.runLater(() -> {
            Data.GAME_CONTROLLER.progress_player_one_game_state.setProgress((double) finalCountP / (Data.MAP_SIZE * Data.MAP_SIZE));
            Data.GAME_CONTROLLER.progress_player_two_game_state.setProgress((double) finalCountP1 / (Data.MAP_SIZE * Data.MAP_SIZE));
        });
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    private void checkGameState() {
        if (Data.GAME_MODE != GameMode.PVP && Data.GAME_SET == 1 && gameState != GameState.FINISHED && (round >= Data.GAME_ROUNDS || finishSelected())) {
            gameState = GameState.FINISHED;
            countEndGame();
        } else if (Data.GAME_SET == 1 && gameState != GameState.FINISHED && (round >= Data.GAME_ROUNDS || finishSelected())) {
            gameState = GameState.FINISHED;
            countEndSet();
        } else if (Data.GAME_SET == 2 && gameState != GameState.FINISHED && (round >= Data.GAME_ROUNDS || finishSelected())) {
            gameState = GameState.FINISHED;
            countEndGame();
        }
    }

    private void countEndGame() {
        int countP1 = Data.FIRST_SET_P1_SCORE;
        int countP2 = Data.FIRST_SET_P2_SCORE;
        for (int i = 0; i < Data.MAP_SIZE; i++) {
            for (int j = 0; j < Data.MAP_SIZE; j++) {
                if (map[i][j] == Mark.PLAYER_ONE) {
                    countP1++;
                }
                if (map[i][j] == Mark.PLAYER_TWO) {
                    countP2++;
                }
            }
        }
        if (countP1 > countP2) {
            setWin(FinishState.PLAYER_ONE_IS_WIN, countP1, countP2);
        } else if (countP2 > countP1) {
            setWin(FinishState.PLAYER_TWO_IS_WIN, countP1, countP2);
        } else {
            setWin(FinishState.TIE, countP1, countP2);
        }
    }

    private boolean finishSelected() {
        for (int i = 0; i < Data.MAP_SIZE; i++) {
            for (int j = 0; j < Data.MAP_SIZE; j++) {
                if (selected[i][j] == SelectMode.NOT_SELECTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void countEndSet() {
        int countP1 = 0;
        int countP2 = 0;
        for (int i = 0; i < Data.MAP_SIZE; i++) {
            for (int j = 0; j < Data.MAP_SIZE; j++) {
                if (map[i][j] == Mark.PLAYER_ONE) {
                    countP1++;
                }
                if (map[i][j] == Mark.PLAYER_TWO) {
                    countP2++;
                }
            }
        }
        if (countP1 > countP2) {
            ShowEndOfSet(FinishState.PLAYER_ONE_IS_WIN, countP1, countP2);
        } else if (countP2 > countP1) {
            ShowEndOfSet(FinishState.PLAYER_TWO_IS_WIN, countP1, countP2);
        } else {
            ShowEndOfSet(FinishState.TIE, countP1, countP2);
        }
    }

    private void setWin(FinishState finishState, int countP1, int countP2) {
        Platform.runLater(() -> {
            switch (finishState) {
                case PLAYER_ONE_IS_WIN:
                    Data.GAME_CONTROLLER.label_finish.setText(Data.PLAYER_ONE_NAME + " Wins The Game\n" + countP1 + " : " + countP2);
                    if (Data.COMPUTER_LEVEL == 10) {
                        Data.COMPUTER_LEVEL = 11;
                    }
                    Data.GAME_CONTROLLER.label_finish_image.setImage(Data.PLAYER_ONE_AVATAR_VIEW.getImage());
                    if (Data.GAME_MODE == GameMode.PVC_LEVEL_BY_LEVEL && Data.COMPUTER_LEVEL < 10) {
                        Platform.runLater(() -> Data.GAME_CONTROLLER.btn_restart.setText("next level"));
                    }
                    if (Data.COMPUTER_LEVEL == 11) {
                        Data.COMPUTER_LEVEL = 0;
                    }
                    break;
                case PLAYER_TWO_IS_WIN:
                    Data.GAME_CONTROLLER.label_finish.setText(Data.PLAYER_TWO_NAME + " Wins The Game" + "\n" + countP2 + " : " + countP1);
                    if (Data.GAME_MODE == GameMode.PVC || Data.GAME_MODE == GameMode.PVC_LEVEL_BY_LEVEL) {
                        Data.GAME_CONTROLLER.label_finish.setText("Computer Wins The Game\n" + countP2 + " : " + countP1);
                    }
                    Data.GAME_CONTROLLER.label_finish_image.setImage(Data.PLAYER_TWO_AVATAR_VIEW.getImage());
                    break;
                case TIE:
                    Data.GAME_CONTROLLER.label_finish.setText("Tie\n" + countP1 + " :" + countP2);
                    Data.GAME_CONTROLLER.label_finish_image.setImage(new Image(getClass().getClassLoader().
                            getResource("images/controller_icon.png").toString()));
                    break;
            }
        });
        Platform.runLater(() -> {
            if (!root.getChildren().contains(Data.GAME_CONTROLLER.rect)) {
                root.getChildren().addAll(Data.GAME_CONTROLLER.rect, Data.GAME_CONTROLLER.label_finish);
            }
            root.getChildren().remove(Data.GAME_CONTROLLER.btn_restart);
            root.getChildren().remove(Data.GAME_CONTROLLER.btn_exit);
            root.getChildren().addAll(Data.GAME_CONTROLLER.btn_restart, Data.GAME_CONTROLLER.btn_exit);
        });
    }

    private void ShowEndOfSet(FinishState finishState, int countP1, int countP2) {
        Data.GAME_SET = 2;
        Data.FIRST_SET_P1_SCORE = countP1;
        Data.FIRST_SET_P2_SCORE = countP2;
        Platform.runLater(() -> {
            Data.GAME_CONTROLLER.label_finish.setText("Set 1 Finished\t" + countP1 + " : " + countP2 + "\nPress Set 2 To Start Set 2");
            Data.GAME_CONTROLLER.label_finish_image.setImage(
                    new Image(getClass().getClassLoader().getResource("images/controller_icon.png").toString())
            );
            Data.GAME_CONTROLLER.btn_restart.setText("Set 2");
            if (!root.getChildren().contains(Data.GAME_CONTROLLER.rect)) {
                root.getChildren().addAll(Data.GAME_CONTROLLER.rect, Data.GAME_CONTROLLER.label_finish);
            }
            root.getChildren().remove(Data.GAME_CONTROLLER.btn_restart);
            root.getChildren().remove(Data.GAME_CONTROLLER.btn_exit);
            root.getChildren().addAll(Data.GAME_CONTROLLER.btn_restart, Data.GAME_CONTROLLER.btn_exit);
        });
    }

    public AnchorPane getRoot() {
        return root;
    }

    public AI getAi() {
        return ai;
    }

    public void selectAIButton(int bestI, int bestJ) {
        Data.EXECUTOR_SERVICE.execute(() -> {
            Platform.runLater(() -> {
                if (!root.getChildren().contains(Data.GAME_CONTROLLER.rect))
                    root.getChildren().addAll(Data.GAME_CONTROLLER.rect, Data.GAME_CONTROLLER.label_finish);
                root.getChildren().remove(Data.GAME_CONTROLLER.btn_restart);
                root.getChildren().remove(Data.GAME_CONTROLLER.btn_exit);
                if (!root.getChildren().contains(Data.GAME_CONTROLLER.btn_restart))
                    root.getChildren().addAll(Data.GAME_CONTROLLER.btn_restart, Data.GAME_CONTROLLER.btn_exit);
                Data.GAME_CONTROLLER.label_finish_image.setImage(new Image(getClass().getClassLoader().
                        getResource("new-avatars/thinking.png").toString()));
                Data.GAME_CONTROLLER.label_finish.setText("Thinking...");
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                root.getChildren().remove(Data.GAME_CONTROLLER.rect);
                root.getChildren().remove(Data.GAME_CONTROLLER.label_finish);
                if (Data.GAME_CONTROLLER.btn_restart.getText().equalsIgnoreCase("start"))
                    Data.GAME_CONTROLLER.btn_restart.setText("restart");
            });
            selectButton(Turn.PLAYER_TWO, bestI, bestJ);
        });
    }

    public int getRounds() {
        return round;
    }

    public void setHintButton(Button hintButton) {
        this.hintButton = hintButton;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }
}
