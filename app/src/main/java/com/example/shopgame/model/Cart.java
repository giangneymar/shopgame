package com.example.shopgame.model;

public class Cart {
    private int idGame;
    private String game;
    private long totalPriceGame;
    private String imgGame;
    private int quantity;

    public Cart(int idGame, String game, long totalPriceGame, String imgGame, int quantity) {
        this.idGame = idGame;
        this.game = game;
        this.totalPriceGame = totalPriceGame;
        this.imgGame = imgGame;
        this.quantity = quantity;
    }

    public int getIdGame() {
        return idGame;
    }

    public String getGame() {
        return game;
    }

    public long getPriceGame() {
        return totalPriceGame;
    }

    public String getImgGame() {
        return imgGame;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setTotalPriceGame(long totalPriceGame) {
        this.totalPriceGame = totalPriceGame;
    }

    public void setImgGame(String imgGame) {
        this.imgGame = imgGame;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
