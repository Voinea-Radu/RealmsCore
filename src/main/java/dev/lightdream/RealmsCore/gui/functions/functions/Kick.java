package dev.lightdream.RealmsCore.gui.functions.functions;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.RealmsCore.database.User;
import dev.lightdream.RealmsCore.gui.functions.GUIFunction;
import dev.lightdream.api.gui.GUI;

import java.util.List;

public class Kick implements GUIFunction {
    @Override
    public void execute(GUI gui, User user, List<String> args) {
        if (args.size() != 1) {
            return;
        }

        User target = Main.instance.databaseManager.getUser(args.get(0));
        if (target == null) {
            return;
        }

        if (!user.hasRealm()) {
            user.sendMessage(Main.instance, Main.instance.lang.notHaveRealm);
            return;
        }

        if (!user.realm.isOwner(user)) {
            user.sendMessage(Main.instance, Main.instance.lang.notOwner);
            return;
        }

        if (!user.realm.getMembers().contains(target)) {
            user.sendMessage(Main.instance, Main.instance.lang.invalidUser);
            return;
        }

        target.leaveRealm();
    }
}
