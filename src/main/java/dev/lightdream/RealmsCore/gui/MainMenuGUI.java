package dev.lightdream.RealmsCore.gui;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.RealmsCore.gui.functions.GUIFunctions;
import dev.lightdream.api.IAPI;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.dto.GUIConfig;
import dev.lightdream.api.dto.GUIItem;
import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.managers.PAPI;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.List;

public class MainMenuGUI extends GUI {
    public MainMenuGUI(IAPI api, User user) {
        super(api, user);
    }

    @Override
    public String parse(String s, String s1, Integer integer) {
        return PAPI.parse(getUser().getOfflinePlayer(),s);
    }

    @Override
    public GUIConfig setConfig() {
        return Main.instance.config.mainMenuGUI;
    }

    @Override
    public InventoryProvider getProvider() {
        return new MainMenuGUI(api, getUser());
    }

    @Override
    public void functionCall(Player player, String function, List<String> args) {
        GUIFunctions.valueOf(function.toUpperCase()).function.execute(this, Main.instance.databaseManager.getUser(player), args);
    }

    @Override
    public boolean canAddItem(GUIItem guiItem, String s, Integer integer) {
        return true;
    }

    @Override
    public void setItems(Player player, InventoryContents inventoryContents) {

    }

    @Override
    public void beforeUpdate(Player player, InventoryContents inventoryContents) {

    }

    @Override
    public void onInventoryClose(InventoryCloseEvent inventoryCloseEvent) {

    }

    @Override
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {

    }

    @Override
    public void onPlayerInventoryClick(InventoryClickEvent inventoryClickEvent) {

    }

    @Override
    public boolean preventClose() {
        return false;
    }

    @Override
    public void changePage(int i) {

    }
}
