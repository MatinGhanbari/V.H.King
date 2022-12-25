package ir.ghanbari.matin.data;

//import io.github.gleidson28.GNAvatarView;
import ir.ghanbari.matin.game.GameController;
import ir.ghanbari.matin.game.GameScreen;
import ir.ghanbari.matin.menu.Menu;
import ir.ghanbari.matin.menu.MenuController;
import ir.ghanbari.matin.util.GameMode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author MatinGhanbari
 */
public class Data {
    // Final Constants
    public static final String GAME_TITLE = "V.H.King";
    public static final Image GAME_ICON = new Image(Data.class.getClassLoader().
            getResource("images/game_icon.png").toString());
    public static final Image BUTTON_SELECTED_BACK = new Image(Data.class.getClassLoader().
            getResource("game_buttons/selected_done.png").toString());
    public static final Image BUTTON_SELECTED_BACK_1 = new Image(Data.class.getClassLoader().
            getResource("game_buttons/1.png").toString());
    public static final Image BUTTON_SELECTED_BACK_2 = new Image(Data.class.getClassLoader().
            getResource("game_buttons/2.png").toString());
    public static final Image BUTTON_SELECTED_BACK_3 = new Image(Data.class.getClassLoader().
            getResource("game_buttons/3.png").toString());
    public static final Image BUTTON_SELECTED_BACK_4 = new Image(Data.class.getClassLoader().
            getResource("game_buttons/4.png").toString());
    public static final Image BUTTON_SELECTED_BACK_5 = new Image(Data.class.getClassLoader().
            getResource("game_buttons/5.png").toString());
    public static final Image BUTTON_NOT_SELECTED_BACK = new Image(Data.class.getClassLoader().
            getResource("game_buttons/not_selected.png").toString());
    public static final Image BUTTON_HINT_BACK = new Image(Data.class.getClassLoader().
            getResource("game_buttons/hint.png").toString());
    public static final String BUTTONS_FONT = "Berlin Sans FB Demi";
    public static final String PLAYER_ONE_CHAR = "P1";
    public static final String PLAYER_TWO_CHAR = "P2";
    public static final String TURN_MARK = "👉";
    public static final int PANE_LEFT_GAP = 60;
    public static final int PANE_UP_GAP = 100;
    public static final int RECT_SIZE = 700;
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());


    // Editable Variables
    public static Menu GAME_MENU;
    public static MenuController MENU_CONTROLLER;
    public static GameScreen GAME_SCREEN;
    public static GameController GAME_CONTROLLER;
    public static int MAP_SIZE = 5; //Default
    public static int GAME_ROUNDS = 5; //Default
    public static String PLAYER_ONE_NAME = "Player One"; //Default
    public static String PLAYER_TWO_NAME = "Player Two"; //Default
//    public static GNAvatarView PLAYER_ONE_AVATAR_VIEW;
//    public static GNAvatarView PLAYER_TWO_AVATAR_VIEW;
    public static Color PLAYER_ONE_COLOR = Color.WHITE; //Default
    public static Color PLAYER_TWO_COLOR = Color.BLACK; //Default
    public static GameMode GAME_MODE = GameMode.PVP; //Default
    public static int COMPUTER_LEVEL = 0; //Default
    public static int GAME_SET = 1;
    public static int FIRST_SET_P1_SCORE = 0;
    public static int FIRST_SET_P2_SCORE = 0;
}
