package dev.lightdream.RealmsCore.managers;

import com.google.gson.Gson;
import dev.lightdream.RealmsCore.Main;
import dev.lightdream.RealmsCore.database.Realm;
import dev.lightdream.RealmsCore.database.User;
import dev.lightdream.RealmsCore.dto.Coords;
import dev.lightdream.api.IAPI;
import dev.lightdream.api.dto.LambdaExecutor;
import dev.lightdream.api.dto.PluginLocation;
import dev.lightdream.api.managers.database.HikariDatabaseManager;
import dev.lightdream.api.managers.database.IDatabaseManagerImpl;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class DatabaseManager extends HikariDatabaseManager implements IDatabaseManagerImpl {
    public DatabaseManager(IAPI api) {
        super(api);
    }

    @Override
    public void setup() {
        setup(User.class);
        setup(Realm.class);
    }

    @Override
    public @NotNull User getUser(@NotNull UUID uuid) {
        Optional<User> optionalUser = getAll(User.class).stream().filter(user -> user.uuid.equals(uuid)).findFirst();

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        User user = new User(api, uuid, Bukkit.getOfflinePlayer(uuid).getName(), api.getSettings().baseLang);
        save(user, false);
        return getUser(uuid);
    }

    @SuppressWarnings("unused")
    @Override
    public @Nullable User getUser(@NotNull String name) {
        Optional<User> optionalUser = getAll(User.class).stream().filter(user -> user.name.equals(name)).findFirst();

        return optionalUser.orElse(null);
    }

    @SuppressWarnings("unused")
    @Override
    public @NotNull User getUser(@NotNull OfflinePlayer player) {
        return getUser(player.getUniqueId());
    }

    @Override
    public @NotNull User getUser(@NotNull Player player) {
        return getUser(player.getUniqueId());
    }

    @SuppressWarnings("unused")
    @Override
    public @Nullable User getUser(int id) {
        Optional<User> optionalUser = getAll(User.class).stream().filter(user -> user.id == id).findFirst();

        return optionalUser.orElse(null);
    }

    @SuppressWarnings("unused")
    public @Nullable dev.lightdream.api.databases.User getUser(@NotNull CommandSender sender) {
        System.out.println("Getting now");
        if (sender instanceof Player) {
            return getUser((Player) sender);
        }
        return api.getConsoleUser();
    }

    @Override
    public HashMap<Class<?>, String> getDataTypes() {
        return new HashMap<Class<?>, String>() {{
            put(User.class, sqlConfig.driver.dataTypes.get(Integer.class));
            put(Realm.class, sqlConfig.driver.dataTypes.get(Integer.class));
            put(Coords.class, sqlConfig.driver.dataTypes.get(String.class));
            put(ArrayList.class, sqlConfig.driver.dataTypes.get(String.class));
            put(List.class, sqlConfig.driver.dataTypes.get(String.class));
            put(PluginLocation.class, sqlConfig.driver.dataTypes.get(String.class));
        }};
    }

    @Override
    public HashMap<Class<?>, LambdaExecutor> getSerializeMap() {
        return new HashMap<Class<?>, LambdaExecutor>() {{
            put(User.class, object -> {
                User user = (User) object;
                return user.id;
            });
            put(Realm.class, object -> {
                Realm realm = (Realm) object;
                return realm.id;
            });
            put(Coords.class, object -> {
                Coords coords = (Coords) object;
                return "\"" + coords.x + ";" + coords.z + "\"";
            });
            put(List.class, object -> "\"" + new Gson().toJson(object) + "\"");
            put(ArrayList.class, object -> "\"" + new Gson().toJson(object) + "\"");
            put(PluginLocation.class, object -> {
                PluginLocation coords = (PluginLocation) object;
                return "\"" + coords.x + ";" + coords.y + ";" + coords.z + "\"";
            });
        }};
    }

    @Override
    public HashMap<Class<?>, LambdaExecutor> getDeserializeMap() {
        return new HashMap<Class<?>, LambdaExecutor>() {{
            put(User.class, object -> {
                Integer id = (Integer) object;
                return Main.instance.databaseManager.getUser(id);
            });
            put(Realm.class, object -> {
                Integer id = (Integer) object;
                return getRealm(id);
            });
            put(Coords.class, object -> {
                String data = (String) object;
                String[] datas = data.split(";");
                return new Coords(Integer.parseInt(datas[0]), Integer.parseInt(datas[1]));
            });
            put(List.class, object -> new Gson().fromJson(object.toString(), List.class));
            put(ArrayList.class, object -> new Gson().fromJson(object.toString(), List.class));
            put(PluginLocation.class, object -> {
                String data = (String) object;
                String[] datas = data.split(";");
                return new PluginLocation(
                        Main.instance.config.realmsWorld,
                        Double.parseDouble(datas[0]),
                        Double.parseDouble(datas[1]),
                        Double.parseDouble(datas[2])
                );
            });
        }};
    }

    public User getUser(dev.lightdream.api.databases.User user) {
        return getUser(user.id);
    }

    public @Nullable Realm getRealm(Integer id) {
        return get(Realm.class, new HashMap<String, Object>() {{
            put("id", id);
        }}).stream().findFirst().orElse(null);
    }

    public @Nullable Realm getRealm(User owner) {
        return get(Realm.class, new HashMap<String, Object>() {{
            put("owner_id", owner.id);
        }}).stream().findFirst().orElse(null);
    }

    public @Nullable Realm getRealm(Coords coords) {
        return get(Realm.class, new HashMap<String, Object>() {{
            put("coords", coords.x + ";" + coords.z);
        }}).stream().findFirst().orElse(null);
    }

    public @Nullable Realm getRealm(PluginLocation location) {
        int x = (int) (location.x + 500) / 1000;
        int z = (int) (location.z + 500) / 1000;
        return getRealm(new Coords(x, z));
    }


}
