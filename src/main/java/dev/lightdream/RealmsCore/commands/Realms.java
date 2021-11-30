package dev.lightdream.RealmsCore.commands;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.RealmsCore.gui.RealmsGUI;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.api.databases.User;

import java.util.ArrayList;
import java.util.List;

@dev.lightdream.api.annotations.commands.SubCommand(
        aliases = "",
        onlyForPlayers = true
)
public class Realms extends SubCommand {
    public Realms() {
        super(Main.instance);
    }

    @Override
    public void execute(User u, List<String> list) {
        dev.lightdream.RealmsCore.database.User user = (dev.lightdream.RealmsCore.database.User) u;
        new RealmsGUI(Main.instance, user).open();
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return new ArrayList<>();
    }
}
