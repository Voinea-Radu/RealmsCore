package dev.lightdream.RealmsCore.commands.realms;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.api.databases.User;

import java.util.ArrayList;
import java.util.List;

@dev.lightdream.api.annotations.commands.SubCommand(
        parentCommand = "realms",
        aliases = "invite",
        onlyForPlayers = true,
        minimumArgs = 1
)
public class Invite extends SubCommand {

    public Invite() {
        super(Main.instance);
    }

    @Override
    public void execute(User u, List<String> args) {
        dev.lightdream.RealmsCore.database.User user = (dev.lightdream.RealmsCore.database.User) u;
        dev.lightdream.RealmsCore.database.User target = Main.instance.databaseManager.getUser(args.get(0));
        if (target == null) {
            user.sendMessage(Main.instance, Main.instance.lang.invalidUser);
            return;
        }
        user.inviteRealm(target);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return new ArrayList<>();
    }
}
