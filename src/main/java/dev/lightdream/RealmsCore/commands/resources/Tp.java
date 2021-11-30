package dev.lightdream.RealmsCore.commands.resources;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.api.databases.User;

import java.util.List;

@dev.lightdream.api.annotations.commands.SubCommand(
        aliases = "tp",
        onlyForPlayers = true,
        parentCommand = "resources"
)
public class Tp extends SubCommand {
    public Tp() {
        super(Main.instance);
    }

    @Override
    public void execute(User u, List<String> list) {
        dev.lightdream.RealmsCore.database.User user = Main.instance.databaseManager.getUser(u);
        user.tpResources();
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
