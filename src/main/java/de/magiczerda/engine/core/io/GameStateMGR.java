package de.magiczerda.engine.core.io;

public class GameStateMGR {

    private static GameState currentGameState = GameState.PLAYING;

    public static void setCurrentGameState(GameState gameState) {
        if(currentGameState == GameState.PLAYING && gameState == GameState.MENU) DisplayManager.setCursorVisibility(true);
        if(currentGameState == GameState.MENU && gameState == GameState.PLAYING) DisplayManager.setCursorVisibility(false);

        currentGameState = gameState;
    }

    public static GameState getCurrentGameState() { return currentGameState; }

}
