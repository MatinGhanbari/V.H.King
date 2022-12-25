package ir.ghanbari.matin.util;

/**
 * @author MatinGhanbari
 */
public enum GameMode {
    PVP,
    PVC,
    PVC_LEVEL_BY_LEVEL;

    GameMode() {
    }

    @Override
    public String toString() {
        switch (this) {
            case PVP:
            default:
                return "Player vs Player";
            case PVC:
                return "Player vs Computer";
        }
    }
}