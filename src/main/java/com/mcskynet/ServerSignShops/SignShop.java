package com.mcskynet.ServerSignShops;

public class SignShop {
    private String material;
    private int amount;
    private int price;
    private SignShopLocation location;

    public SignShop(String material, int amount, int price, SignShopLocation location) {
        this.material = material;
        this.amount = amount;
        this.price = price;
        this.location = location;
    }
}
