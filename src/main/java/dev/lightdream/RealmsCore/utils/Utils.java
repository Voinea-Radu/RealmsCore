package dev.lightdream.RealmsCore.utils;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.RealmsCore.database.Realm;
import dev.lightdream.RealmsCore.database.User;
import dev.lightdream.RealmsCore.dto.Coords;
import dev.lightdream.RealmsCore.files.Data;
import dev.lightdream.api.dto.PluginLocation;

public class Utils extends dev.lightdream.api.utils.Utils {

    public static Coords convertToCoords(Data data) {
        int x;
        int y;
        if (data.cursor > data.size) {
            y = data.size - (data.cursor - data.size);
            x = data.size;
        } else {
            x = data.cursor;
            y = data.size;
        }
        return new Coords(x, y);
    }

    public static Realm getOwnedRealm(User user) {
        if (!user.isOnline()) {
            return null;
        }

        Realm realm = Main.instance.databaseManager.getRealm(new PluginLocation(user.getPlayer().getLocation()));

        if (realm == null) {
            return null;
        }

        if (realm.isMember(user)) {
            return realm;
        }

        return null;
    }

}
