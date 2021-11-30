package dev.lightdream.RealmsCore.commands.realms;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.api.annotations.commands.SubCommand;
import dev.lightdream.api.databases.User;

import java.util.ArrayList;
import java.util.List;

@SubCommand(
        aliases = "accept",
        parentCommand = "realms",
        onlyForPlayers = true
)
public class Accept extends dev.lightdream.api.commands.SubCommand {
    public Accept() {
        super(Main.instance);
    }

    @Override
    public void execute(User u, List<String> list) {
        dev.lightdream.RealmsCore.database.User user = (dev.lightdream.RealmsCore.database.User) u;
        user.joinRealm();
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return new ArrayList<>();
    }
}
