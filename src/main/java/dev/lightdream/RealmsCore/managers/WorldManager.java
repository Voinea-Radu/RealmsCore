package dev.lightdream.RealmsCore.managers;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.RealmsCore.database.Realm;
import dev.lightdream.RealmsCore.database.User;
import dev.lightdream.api.utils.Debugger;
import dev.lightdream.api.utils.WorldEditUtils;
import org.bukkit.Bukkit;

public class WorldManager {

    public WorldManager() {

    }

    public void generateRealm(Realm realm) {
        User owner = realm.getOwner();

        owner.sendMessage(Main.instance, Main.instance.lang.generatingRealm);

        WorldEditUtils.paste(
                realm.getRealLocation(),
                WorldEditUtils.load("schematics", "realm", Main.instance));

        owner.sendMessage(Main.instance, Main.instance.lang.generatedRealm);
        tpRealm(owner, realm);

    }

    public void tpRealm(User user, Realm realm) {
        user.teleport(realm.spawn);
        user.sendMessage(Main.instance, Main.instance.lang.realmTp);
        Bukkit.getScheduler().runTaskLater(Main.instance, () ->
                Main.instance.protocolLibManager.sendWorldBorder(user.getPlayer(), realm.getRealLocation(), realm.getLevel().realmSize), 1);
    }

    public void tpRealm(User user) {
        tpRealm(user, user.realm);
    }

    public void deleteRealm(Realm realm) {
        realm.coords = null;
    }


}
