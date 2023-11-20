package controller.panes.favoriteServices;


import controller.panes.FavoriteController;

public abstract class FavoriteAction{
    protected FavoriteController favoriteController;

    public void initFavoriteControllerContainer(FavoriteController favoriteController) {
        this.favoriteController = favoriteController;
    }
}