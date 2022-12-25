package ir.ghanbari.matin.ai;

import ir.ghanbari.matin.data.Data;
import ir.ghanbari.matin.game.GameScreen;
import ir.ghanbari.matin.util.*;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;

/**
 * @author MatinGhanbari
 */
public class AI {
    private int bestI;
    private int bestJ;
    private final Mark[][] map;
    private final SelectMode[][] selected;
    private final GameScreen game = Data.GAME_SCREEN;

    public AI(Mark[][] map, SelectMode[][] selected) {
        this.map = map;
        this.selected = selected;
    }

    public void move() {
        if (game.getTurn() == Turn.PLAYER_ONE || game.getRounds() >= Data.GAME_ROUNDS) {
            return;
        }
        switch (Data.COMPUTER_LEVEL) {
            case 10:
                bestMove();
                break;
            case 9:
            case 8:
            case 7:
            case 6:
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
                normalMove(Data.COMPUTER_LEVEL);
                break;
            case 0:
            default:
                easyMove();
                break;
        }
    }

    public void bestMove() {
        int bestScore = 0;
        bestI = -1;
        bestJ = -1;
        for (int i = 0; i < Data.MAP_SIZE; i++) {
            for (int j = 0; j < Data.MAP_SIZE; j++) {
                if (selected[i][j] == SelectMode.NOT_SELECTED) {
                    int score = 0;
                    for (int k = 0; k < Data.MAP_SIZE; k++) {
                        if (map[i][k] == Mark.PLAYER_ONE) {
                            score++;
                        }
                        if (map[k][j] == Mark.PLAYER_ONE) {
                            score++;
                        }
                    }
                    if (map[i][j] == Mark.PLAYER_ONE) {
                        score--;
                    }
                    if (bestScore < score) {
                        bestScore = score;
                        bestI = i;
                        bestJ = j;
                    }
                }
            }
        }
        if (bestI != -1 && bestJ != -1) {
            selected[bestI][bestJ] = SelectMode.SELECTED;
            game.selectAIButton(bestI, bestJ);
        }
    }

    public void normalMove(int computerLevel) {
        int bestScore = 0;
        int maxScore;
        maxScore = (int) (((Data.MAP_SIZE * 2) - 1) * computerLevel / 9);
        bestI = -1;
        bestJ = -1;
        while ((bestI == -1 || bestJ == -1) && (maxScore <= (Data.MAP_SIZE * 2))) {
            for (int i = 0; i < Data.MAP_SIZE; i++) {
                for (int j = 0; j < Data.MAP_SIZE; j++) {
                    if (selected[i][j] == SelectMode.NOT_SELECTED) {
                        int score = 0;
                        for (int k = 0; k < Data.MAP_SIZE; k++) {
                            if (map[i][k] == Mark.PLAYER_ONE) {
                                score++;
                            }
                            if (map[k][j] == Mark.PLAYER_ONE) {
                                score++;
                            }
                        }
                        if (map[i][j] == Mark.PLAYER_ONE) {
                            score--;
                        }
                        if (bestScore < score && maxScore > score) {
                            bestScore = score;
                            bestI = i;
                            bestJ = j;
                        }
                    }
                }
            }
            maxScore++;
        }
        if (bestI != -1 && bestJ != -1) {
            selected[bestI][bestJ] = SelectMode.SELECTED;
            game.selectAIButton(bestI, bestJ);
        }
    }

    public void easyMove() {
        int minScore = (Data.MAP_SIZE * 2) - 1;
        bestI = -1;
        bestJ = -1;
        for (int i = 0; i < Data.MAP_SIZE; i++) {
            for (int j = 0; j < Data.MAP_SIZE; j++) {
                if (selected[i][j] == SelectMode.NOT_SELECTED) {
                    int score = 0;
                    for (int k = 0; k < Data.MAP_SIZE; k++) {
                        if (map[i][k] == Mark.PLAYER_ONE) {
                            score++;
                        }
                        if (map[k][j] == Mark.PLAYER_ONE) {
                            score++;
                        }
                    }
                    if (map[i][j] == Mark.PLAYER_ONE) {
                        score--;
                    }
                    if (minScore > score) {
                        minScore = score;
                        bestI = i;
                        bestJ = j;
                    }
                }
            }
        }
        if (bestI != -1 && bestJ != -1) {
            selected[bestI][bestJ] = SelectMode.SELECTED;
            game.selectAIButton(bestI, bestJ);
        }
    }

    public void hint() {
        Mark mark;
        switch (game.getTurn()) {
            case PLAYER_ONE:
            default:
                mark = Mark.PLAYER_TWO;
                break;
            case PLAYER_TWO:
                mark = Mark.PLAYER_ONE;
                break;
        }
        int bestScore = 0;
        bestI = -1;
        bestJ = -1;
        for (int i = 0; i < Data.MAP_SIZE; i++) {
            for (int j = 0; j < Data.MAP_SIZE; j++) {
                if (selected[i][j] == SelectMode.NOT_SELECTED) {
                    int score = 0;
                    for (int k = 0; k < Data.MAP_SIZE; k++) {
                        if (map[i][k] == mark) {
                            score++;
                        }
                        if (map[k][j] == mark) {
                            score++;
                        }
                    }
                    if (map[i][j] == mark) {
                        score--;
                    }
                    if (bestScore < score) {
                        bestScore = score;
                        bestI = i;
                        bestJ = j;
                    }
                }
            }
        }
        if (bestI != -1 && bestJ != -1) {
            Platform.runLater(() -> {
                game.getButtons()[bestI][bestJ].setBackground(ButtonChanger.getBack(Data.BUTTON_HINT_BACK));
            });
            game.setHintButton(game.getButtons()[bestI][bestJ]);
        }
    }
}