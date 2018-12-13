package com.mcskynet.ServerSignShops;

import com.google.gson.Gson;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class SignChangeListener implements Listener {
    private Plugin plugin;

    public SignChangeListener(Plugin plugin, Economy economy) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        String[] lines = event.getLines();
        if (lines[0].toLowerCase().equals("[shop]")) {
            String shopString = plugin.getConfig().getString("signTopLine");
            shopString = ChatColor.translateAlternateColorCodes('&', shopString);

            Material material = Material.getMaterial(lines[1].toUpperCase());
            if (material == null) {
                event.getPlayer().sendMessage("§cInvalid item.");
                event.getPlayer().sendMessage("§cList of valid items: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html");
                event.getPlayer().sendMessage("§eFormat:");
                event.getPlayer().sendMessage("Line 1: [shop]");
                event.getPlayer().sendMessage("Line 2: ITEM");
                event.getPlayer().sendMessage("Line 1: AMOUNT");
                event.getPlayer().sendMessage("Line 1: PRICE");
                return;

            }

            int price;
            try {
                price = Integer.parseInt(lines[3]);
            } catch (NumberFormatException e) {
                event.getPlayer().sendMessage("§cPrice is not a number.");
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(lines[2]);
            } catch (NumberFormatException e) {
                event.getPlayer().sendMessage("§cAmount is not a number");
                return;
            }

            ItemStack shopItem = new ItemStack(Material.getMaterial(lines[1].toUpperCase()));

            Gson gson = new Gson();

            SignShopLocation shopLocation = new SignShopLocation(
                    event.getBlock().getWorld().getName(),
                    event.getBlock().getX(),
                    event.getBlock().getY(),
                    event.getBlock().getZ()
            );

            SignShop signShop = new SignShop(
                    shopItem.getType().name(),
                    amount,
                    price,
                    shopLocation
            );

            if (Signs.getInstance().createWarp(signShop)) {
                event.getPlayer().sendMessage("Created new shop");
            }

            event.setLine(0, shopString);
            event.setLine(1, Integer.toString(amount).concat(" ").concat(shopItem.getType().toString()));
            event.setLine(2, Integer.toString(price).concat(" $"));
            event.setLine(3, "");
        }
    }
}
