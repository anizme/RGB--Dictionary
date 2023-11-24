package controller.panes.favoriteServices;

import controller.panes.ActionController;

public abstract class FavoriteAction {
    protected ActionController actionController;

    public void initFavoriteControllerContainer(ActionController favoriteController) {
        this.actionController = favoriteController;
    }
}