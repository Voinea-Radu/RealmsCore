package dev.lightdream.RealmsCore;

import dev.lightdream.RealmsCore.commands.Navigator;
import dev.lightdream.RealmsCore.commands.Realms;
import dev.lightdream.RealmsCore.commands.realms.*;
import dev.lightdream.RealmsCore.database.User;
import dev.lightdream.RealmsCore.dto.Coords;
import dev.lightdream.RealmsCore.files.Config;
import dev.lightdream.RealmsCore.files.Data;
import dev.lightdream.RealmsCore.files.Lang;
import dev.lightdream.RealmsCore.managers.DatabaseManager;
import dev.lightdream.RealmsCore.managers.EventManager;
import dev.lightdream.RealmsCore.managers.WorldManager;
import dev.lightdream.RealmsCore.utils.Utils;
import dev.lightdream.api.API;
import dev.lightdream.api.LightDreamPlugin;
import dev.lightdream.api.commands.Command;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.api.configs.SQLConfig;
import dev.lightdream.api.dto.Test;
import dev.lightdream.api.dto.TestBattery;
import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.api.managers.database.IDatabaseManagerImpl;
import dev.lightdream.api.utils.Debugger;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class Main extends LightDreamPlugin {

    public static Main instance;

    //Settings
    public Config config;
    public Lang lang;
    public Data data;

    //Managers
    public DatabaseManager databaseManager;
    public WorldManager worldManager;
    public EventManager eventManager;

    @Override
    public void onEnable() {
        instance = this;

        init("RealmsCore", "realms", "realms");

        test();

        databaseManager = new DatabaseManager(this);
        worldManager = new WorldManager();
        eventManager = new EventManager(this);

        //noinspection ArraysAsListWithZeroOrOneArgument
        new Command(this, "resources", Arrays.asList(
                new dev.lightdream.RealmsCore.commands.resources.Tp()
        ));

        //noinspection ArraysAsListWithZeroOrOneArgument
        new Command(this, "navigator", Arrays.asList(
                new Navigator()
        ));
    }


    @Override
    public @NotNull
    String parsePapi(OfflinePlayer offlinePlayer, String s) {
        return "";
    }

    @Override
    public void loadConfigs() {
        sqlConfig = fileManager.load(SQLConfig.class);
        config = fileManager.load(Config.class);
        baseConfig = config;
        lang = fileManager.load(Lang.class, fileManager.getFile(baseConfig.baseLang));
        baseLang = lang;
        data = fileManager.load(Data.class);
    }

    @Override
    public void disable() {
        fileManager.save(data);
    }

    @Override
    public void registerFileManagerModules() {

    }

    @Override
    public void registerUser(Player player) {

    }

    @Override
    public List<SubCommand> getBaseSubCommands() {
        return Arrays.asList(
                new Tp(),
                new Create(),
                new Delete(),
                new Regenerate(),
                new Upgrade(),
                new Invite(),
                new Accept(),
                new SetSpawn(),
                new Realms()
        );
    }

    @Override
    public MessageManager instantiateMessageManager() {
        return new MessageManager(this, Main.class);
    }

    @Override
    public void registerLangManager() {
        API.instance.langManager.register(Main.class, getLangs());
    }

    @Override
    public HashMap<String, Object> getLangs() {
        HashMap<String, Object> langs = new HashMap<>();

        baseConfig.langs.forEach(lang -> {
            Lang l = fileManager.load(Lang.class, fileManager.getFile(lang));
            langs.put(lang, l);
        });

        return langs;
    }

    @Override
    public IDatabaseManagerImpl getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public void setLang(Player player, String s) {
        User user = databaseManager.getUser(player);
        user.setLang(s);
        user.save();
    }

    public void test() {
        if (!Debugger.enabled) {
            return;
        }

        new TestBattery(this, Arrays.asList(
                new Test(List.class, test -> {
                    List<String> members = new ArrayList<>();
                    test.submitResults(members.getClass());
                }),

                new Test(ArrayList.class, test -> {
                    List<String> members = new ArrayList<>();
                    test.submitResults(members.getClass());
                }),

                new Test(new Coords(4, 3), test -> {
                    Data testData = new Data();
                    testData.size = 4;
                    testData.cursor = 5;
                    test.submitResults(Utils.convertToCoords(testData));
                }),

                new Test(new Coords(3, 4), test -> {
                    Data testData = new Data();
                    testData.size = 4;
                    testData.cursor = 3;
                    test.submitResults(Utils.convertToCoords(testData));
                }),

                new Test(new Coords(1, 1), test -> {
                    Data testData = new Data();
                    testData.size = 1;
                    testData.cursor = 1;
                    test.submitResults(Utils.convertToCoords(testData));
                }),

                new Test(1, test -> test.submitResults(1))

        ));
    }


}
