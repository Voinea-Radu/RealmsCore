package dev.lightdream.RealmsCore.files;

import dev.lightdream.RealmsCore.dto.RealmTier;
import dev.lightdream.api.dto.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class Config extends dev.lightdream.api.configs.Config {

    public String realmsWorld = "world";
    public Position realmSizeAllocation = new Position(1000, 0, 1000);
    public PluginLocation resourceWorld = new PluginLocation("world", 0, 100, 0);
    public List<RealmTier> realmUpgrades = Arrays.asList(
            new RealmTier(10, 1),
            new RealmTier(20, 2),
            new RealmTier(30, 3),
            new RealmTier(40, 4),
            new RealmTier(50, 5)
    );
    public PluginLocation spawnLocation = new PluginLocation("world", 0, 100, 0);

    public GUIConfig mainMenuGUI = new GUIConfig(
            "main_menu",
            "CHEST",
            "Navigator",
            6,
            new Item(XMaterial.AIR),
            new HashMap<String, GUIItem>() {{
                put("spawn", new GUIItem(new Item(XMaterial.FEATHER, 0, 1, "Go to Spawn", new ArrayList<>()),
                        new GUIItem.GUIItemArgs(new HashMap<String, Object>() {{
                            put("teleport", "spawn");
                        }})));
                put("resources", new GUIItem(new Item(XMaterial.IRON_INGOT, 1, 1, "Go to Resources World",
                        new ArrayList<>()), new GUIItem.GUIItemArgs(new HashMap<String, Object>() {{
                    put("teleport", "resources");
                }})));
                put("realm", new GUIItem(new Item(XMaterial.GRASS_BLOCK, 2, 1, "Go to your Realm", new ArrayList<>()),
                        new GUIItem.GUIItemArgs(new HashMap<String, Object>() {{
                            put("teleport", "realm");
                        }})));
            }},
            false
    );

    public GUIConfig realmsGUI = new GUIConfig(
            "realms_menu",
            "CHEST",
            "Realms",
            6,
            new Item(XMaterial.AIR),
            new HashMap<String, GUIItem>() {{
                put("realm", new GUIItem(new Item(XMaterial.GRASS_BLOCK, 4, 1, "Go to your Realm", new ArrayList<>()), new GUIItem.GUIItemArgs(new HashMap<String, Object>() {{
                    put("teleport", "realm");
                }})));

                put("others_realms", new GUIItem(new Item(XMaterial.PLAYER_HEAD, "Go to %player_name%'s Realm"), new GUIItem.GUIItemArgs(new HashMap<String, Object>() {{
                    put("teleport", "%player_name%");
                }}), Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16, 17)));

                put("members", new GUIItem(new Item(XMaterial.PLAYER_HEAD, 1, "Member %player_name%", Arrays.asList("Click to remove from realm")), new GUIItem.GUIItemArgs(new HashMap<String, Object>() {{
                    put("kick", "%player_name%");
                }}), Arrays.asList(18, 19, 20, 21, 22, 23, 24, 25, 26)));
            }},
            false
    );

}
