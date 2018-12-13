package com.mcskynet.ServerSignShops;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class ServerSignShops extends JavaPlugin {

    private Economy economy = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        createJson();
        if (!setupEconomy()) {
            getLogger().severe("Could not set up economy");
            getPluginLoader().disablePlugin(this);
            return;
        }
        getServer().getPluginManager().registerEvents(new SignChangeListener(this, economy), this);

        Signs signs = Signs.getInstance();
        signs.readFile(this);
    }
    
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    private void createJson() {
        try {
            String file = "warps.json";
            Path filePath = Paths.get(getDataFolder().getAbsolutePath(), file);

            String[] newFileContents = {"{shops: []}"};
            Iterable<String> iterableFileContents = Arrays.asList(newFileContents);

            Files.createFile(filePath);
            Files.write(filePath, iterableFileContents);
            getLogger().info("Created new warps.json");
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                getLogger().info("warps.json already exists, skipping creation ");
                return;
            }
            getLogger().severe("Could not create warps.json");
            e.printStackTrace();
            getPluginLoader().disablePlugin(this);
        }
    }

}
