package com.mcskynet.ServerSignShops;

import com.google.gson.Gson;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Signs {
    private SignShopArray signShops = new SignShopArray();
    private Path path;
    private Plugin plugin;

    private static Signs instance;

    private Signs() {}

    static {
        try {
            instance = new Signs();
        } catch (Exception e) {
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }

    public static Signs getInstance() {
        if (instance == null)  {
            instance = new Signs();
        }
        return instance;
    }

    public boolean createWarp(SignShop signShop) {
        Gson gson = new Gson();
        plugin.getLogger().info(signShops.getList().toString());
        signShops.newShop(signShop);

        String json = gson.toJson(signShops);

        try {
            String file = "warps.json";
            Path filePath = Paths.get(plugin.getDataFolder().getAbsolutePath(), file);

            String[] newFileContents = {json};
            Iterable<String> iterableFileContents = Arrays.asList(newFileContents);

            Files.write(filePath, iterableFileContents);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void readFile(Plugin plugin) {
        this.plugin = plugin;

        String file = "warps.json";
        path = Paths.get(plugin.getDataFolder().getAbsolutePath(), file);
        try {
            List<String> warps = Files.readAllLines(path);
            String signShops = String.join("", warps);
            this.signShops = new Gson().fromJson(signShops, SignShopArray.class);
            plugin.getLogger().info(this.signShops.getList().toString());
        } catch (Exception e) {
            plugin.getLogger().severe("Could not read file warps.json");
            e.printStackTrace();
        }
    }
}
