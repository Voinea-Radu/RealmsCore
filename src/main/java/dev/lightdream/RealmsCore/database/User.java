package dev.lightdream.RealmsCore.database;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.api.IAPI;
import dev.lightdream.api.annotations.database.DatabaseField;
import dev.lightdream.api.annotations.database.DatabaseTable;
import dev.lightdream.api.utils.Debugger;
import dev.lightdream.api.utils.MessageBuilder;

import java.util.HashMap;
import java.util.UUID;

@DatabaseTable(table = "users")
public class User extends dev.lightdream.api.databases.User {

    @DatabaseField(columnName = "realm")
    public Realm realm;
    @DatabaseField(columnName = "invired_realm")
    public Realm invitedRealm;

    public User(IAPI api, UUID uuid, String name, String lang) {
        super(api, uuid, name, lang);
        this.realm = null;
    }

    public User() {
        super();
    }

    public void tpRealm() {
        if (realm == null) {
            sendMessage(Main.instance, Main.instance.lang.notHaveRealm);
            return;
        }
        sendMessage(Main.instance, Main.instance.lang.realmTp);
        Main.instance.worldManager.tpRealm(this);
    }

    public void createRealm() {
        if (realm != null) {
            sendMessage(Main.instance, Main.instance.lang.alreadyHaveRealm);
            return;
        }

        new Realm(this);
        this.realm = Main.instance.databaseManager.getRealm(this);

        if (this.realm == null) {
            sendMessage(Main.instance, Main.instance.lang.realmCreationFailed);
            return;
        }

        Main.instance.worldManager.generateRealm(this.realm);
        sendMessage(Main.instance, Main.instance.lang.createdRealm);
        save();
    }

    public void deleteRealm() {
        if (realm == null) {
            Debugger.info("User does not have a realm");
            sendMessage(Main.instance, Main.instance.lang.notHaveRealm);
            return;
        }
        if (!realm.isOwner(this)) {
            sendMessage(Main.instance, Main.instance.lang.notOwner);
            return;
        }

        Main.instance.worldManager.deleteRealm(realm);
        this.realm.delete();
        this.realm = null;
        sendMessage(Main.instance, Main.instance.lang.deletedRealm);
        teleport(Main.instance.config.spawnLocation);
        save();
    }

    public void regenerateRealm() {
        if (realm == null) {
            sendMessage(Main.instance, Main.instance.lang.notHaveRealm);
            return;
        }

        Main.instance.worldManager.generateRealm(this.realm);
    }

    public void tpResources() {
        teleport(Main.instance.config.resourceWorld);
        sendMessage(Main.instance, Main.instance.lang.resourcesTp);
    }

    @Override
    public String toString() {
        return "User{" +
                "realm=" + realm +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", lang='" + lang + '\'' +
                ", id=" + id +
                '}';
    }

    public void upgradeRealm() {
        this.realm.upgrade();
        sendMessage(Main.instance, Main.instance.lang.realmUpgraded);
        tpRealm();
    }

    public void leaveRealm() {
        if (realm == null) {
            sendMessage(Main.instance, Main.instance.lang.notHaveRealm);
        }
        if (realm.isOwner(this)) {
            sendMessage(Main.instance, Main.instance.lang.owner);
            return;
        }
        this.realm = null;
        teleport(Main.instance.config.spawnLocation);
        sendMessage(Main.instance, Main.instance.lang.leftRealm);
    }

    public void inviteRealm(User target) {
        if (realm == null) {
            sendMessage(Main.instance, Main.instance.lang.notHaveRealm);
        }

        if (target.realm != null) {
            sendMessage(Main.instance, Main.instance.lang.targetAlreadyHaveRealm);
            return;
        }

        sendMessage(Main.instance, Main.instance.lang.inviteSent);
        User thisUser = this;

        target.invite(this.realm);
        target.sendMessage(Main.instance, new MessageBuilder(Main.instance.lang.invited).addPlaceholders(new HashMap<String, String>() {{
            put("player_name", thisUser.name);
        }}));
    }

    public void invite(Realm realm) {
        this.invitedRealm = realm;
        save();
    }

    public void joinRealm() {
        if (realm != null) {
            sendMessage(Main.instance, Main.instance.lang.alreadyHaveRealm);
        }

        if (invitedRealm == null) {
            sendMessage(Main.instance, Main.instance.lang.notInvited);
            return;
        }

        this.realm = invitedRealm;
        this.realm.join(this);
        save();
    }

    public boolean hasRealm() {
        return realm != null;
    }

    public void tpHome(){
        //todo
    }
}
