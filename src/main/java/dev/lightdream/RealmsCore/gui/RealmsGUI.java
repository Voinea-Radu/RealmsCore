package dev.lightdream.RealmsCore.gui;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.RealmsCore.database.Realm;
import dev.lightdream.RealmsCore.database.User;
import dev.lightdream.RealmsCore.gui.functions.GUIFunctions;
import dev.lightdream.api.IAPI;
import dev.lightdream.api.dto.GUIConfig;
import dev.lightdream.api.dto.GUIItem;
import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.managers.PAPI;
import dev.lightdream.api.utils.MessageBuilder;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.List;

public class RealmsGUI extends GUI {
    private final List<Realm> realms;
    private final User user;

    public RealmsGUI(IAPI api, User user) {
        super(api, user);
        this.user = user;
        List<Realm> tmp = Main.instance.databaseManager.getAll(Realm.class);
        if (tmp.size() > 11) {
            this.realms = tmp.subList(0, 10);
        } else {
            this.realms = tmp;
        }
    }

    @Override
    public String parse(String raw, String identifier, Integer index) {
        if (identifier.equals("members")) {
            return PAPI.parse(getUser().getOfflinePlayer(), new MessageBuilder(raw).addPlaceholders(new HashMap<String, String>() {{
                put("player_name", user.realm.getMembers().get(index).name);
            }}).parseString());
        } else if (identifier.equals("others_realms")) {
            return PAPI.parse(getUser().getOfflinePlayer(), new MessageBuilder(raw).addPlaceholders(new HashMap<String, String>() {{
                put("player_name", realms.get(index).getOwner().name);
            }}).parseString());
        }
        return PAPI.parse(getUser().getOfflinePlayer(), raw);
    }

    @Override
    public GUIConfig setConfig() {
        return Main.instance.config.realmsGUI;
    }

    @Override
    public InventoryProvider getProvider() {
        return new RealmsGUI(api, user);
    }

    @Override
    public void functionCall(Player player, String function, List<String> args) {
        GUIFunctions.valueOf(function.toUpperCase()).function.execute(this, Main.instance.databaseManager.getUser(player), args);
    }

    @Override
    public boolean canAddItem(GUIItem guiItem, String identifier, Integer index) {
        if (identifier.equals("members")) {
            if (!user.hasRealm()) {
                return false;
            }
            return index < user.realm.getMembers().size();
        } else if (identifier.equals("others_realms")) {
            return index < realms.size();
        }
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
