package com.example.shopgame.model;

import java.io.Serializable;

public class Game implements Serializable {
    private int id;
    private String game;
    private int priceGame;
    private String imgGame;
    private String desGame;
    private int idGameCategory;

    public Game(int id, String game, int priceGame, String imgGame, String desGame, int idGameCategory) {
        this.id = id;
        this.game = game;
        this.priceGame = priceGame;
        this.imgGame = imgGame;
        this.desGame = desGame;
        this.idGameCategory = idGameCategory;
    }

    public int getId() {
        return id;
    }

    public String getGame() {
        return game;
    }

    public int getPriceGame() {
        return priceGame;
    }

    public String getImgGame() {
        return imgGame;
    }

    public String getDesGame() {
        return desGame;
    }

    public int getIdGameCategory() {
        return idGameCategory;
    }
}
