package com.mcskynet.ServerSignShops;

import java.util.ArrayList;
import java.util.List;

public class SignShopArray {
    private List<SignShop> signShops;

    public SignShopArray(List<SignShop> signShops) {
        this.signShops = signShops;
    }

    public SignShopArray() {
        this.signShops = new ArrayList<SignShop>();
    }

    public void newShop(SignShop signShop) {
        signShops.add(signShop);
    }

    public List<SignShop> getList() {
        return signShops;
    }
}
