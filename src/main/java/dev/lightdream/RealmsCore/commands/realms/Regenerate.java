package dev.lightdream.RealmsCore.commands.realms;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.api.databases.User;

import java.util.List;

@dev.lightdream.api.annotations.commands.SubCommand(
        aliases = "regenerate",
        onlyForPlayers = true,
        parentCommand = "realms"
)
public class Regenerate extends SubCommand {
    public Regenerate() {
        super(Main.instance);
    }

    @Override
    public void execute(User u, List<String> list) {
        dev.lightdream.RealmsCore.database.User user = (dev.lightdream.RealmsCore.database.User) u;
        user.regenerateRealm();
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
