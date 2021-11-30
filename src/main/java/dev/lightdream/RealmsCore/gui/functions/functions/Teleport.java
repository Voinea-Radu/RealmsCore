package dev.lightdream.RealmsCore.gui.functions.functions;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.RealmsCore.database.User;
import dev.lightdream.RealmsCore.gui.functions.GUIFunction;
import dev.lightdream.api.gui.GUI;

import java.util.List;

public class Teleport implements GUIFunction {

    @Override
    public void execute(GUI gui, User user, List<String> args) {
        if (args.size() != 1) {
            return;
        }
        String location = args.get(0);
        switch (location) {
            case "spawn":
                user.teleport(Main.instance.config.spawnLocation);
                break;
            case "resources":
                user.tpResources();
                break;
            case "realm":
                if (!user.hasRealm()) {
                    user.createRealm();
                }
                user.tpRealm();
                break;
            case "home":
                user.tpHome();
                break;
            default:
                User target = Main.instance.databaseManager.getUser(location);
                if (target == null) {
                    break;
                }
                target.realm.teleport(user);
                break;
        }
    }
}
