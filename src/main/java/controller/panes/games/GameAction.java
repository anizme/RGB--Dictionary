package controller.panes.games;

import controller.panes.ContainerController;
import controller.panes.GameController;

public abstract class GameAction {
    protected GameController gameControllerContainer;

    public void initGameControllerContainer(GameController gameControllerContainer) {
        this.gameControllerContainer = gameControllerContainer;
    }
}
