package com.example.shopgame.model;

public class GameCategory {
    private int id;
    private String gameCategoryName;
    private String gameCategoryImage;

    public GameCategory(int id, String gameCategoryName, String gameCategoryImage) {
        this.id = id;
        this.gameCategoryName = gameCategoryName;
        this.gameCategoryImage = gameCategoryImage;
    }

    public int getId() {
        return id;
    }

    public String getGameCategoryName() {
        return gameCategoryName;
    }

    public String getGameCategoryImage() {
        return gameCategoryImage;
    }
}
