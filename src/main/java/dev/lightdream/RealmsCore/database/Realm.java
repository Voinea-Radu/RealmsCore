package dev.lightdream.RealmsCore.database;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.RealmsCore.dto.Coords;
import dev.lightdream.RealmsCore.dto.RealmTier;
import dev.lightdream.RealmsCore.utils.Utils;
import dev.lightdream.api.annotations.database.DatabaseField;
import dev.lightdream.api.annotations.database.DatabaseTable;
import dev.lightdream.api.databases.DatabaseEntry;
import dev.lightdream.api.dto.PluginLocation;
import dev.lightdream.api.utils.Debugger;
import dev.lightdream.libs.fasterxml.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable(table = "realms")
public class Realm extends DatabaseEntry {

    @DatabaseField(columnName = "owner_id")
    public Integer ownerID;
    @DatabaseField(columnName = "coords")
    public Coords coords;
    @DatabaseField(columnName = "level")
    public Integer level;
    @DatabaseField(columnName = "members")
    public List<Integer> members;
    @DatabaseField(columnName = "spawn")
    public PluginLocation spawn;

    public Realm(User owner) {
        super(Main.instance);
        this.ownerID = owner.id;
        Debugger.info("Creating realm");
        this.coords = Utils.convertToCoords(Main.instance.data);
        this.level = 0;
        this.members = new ArrayList<>();
        this.spawn = getRealLocation();

        if (Main.instance.data.cursor == Main.instance.data.size * 2 - 1) {
            Main.instance.data.size++;
            Main.instance.data.cursor = 1;
        } else {
            Main.instance.data.cursor++;
        }

        save();
    }

    @SuppressWarnings("unused")
    public Realm() {
        super(Main.instance);
    }

    public void teleport(User user) {
        Main.instance.worldManager.tpRealm(user, this);
    }

    @JsonIgnore
    public PluginLocation getRealLocation() {
        return coords
                .getLocation(Main.instance.config.realmsWorld)
                .multiply(Main.instance.config.realmSizeAllocation)
                .newOffset(0, 100, 0);
    }

    @JsonIgnore
    public User getOwner() {
        return Main.instance.databaseManager.getUser(ownerID);
    }

    public void upgrade() {
        this.level++;
        save();
    }

    @JsonIgnore
    public RealmTier getLevel() {
        if (level >= Main.instance.config.realmUpgrades.size()) {
            return Main.instance.config.realmUpgrades.get(Main.instance.config.realmUpgrades.size() - 1);
        }
        return Main.instance.config.realmUpgrades.get(level);
    }

    public List<User> getMembers() {
        List<User> members = new ArrayList<>();

        this.members.forEach(memberID -> members.add(Main.instance.databaseManager.getUser(memberID)));

        return members;
    }

    public boolean isOwner(User user) {
        return user.id == ownerID;
    }

    public void delete() {
        getMembers().forEach(User::leaveRealm);
        Main.instance.getDatabaseManager().delete(this);

    }

    public void join(User user) {
        this.members.add(user.id);
        save();
        user.sendMessage(Main.instance, Main.instance.lang.joinedRealm);
    }

    public void setSpawn(PluginLocation location) {
        this.spawn = location;
        save();
    }

    public boolean isMember(User user) {
        return members.contains(user.id) || ownerID == user.id;
    }

}
