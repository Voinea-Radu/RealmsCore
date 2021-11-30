package dev.lightdream.RealmsCore.commands;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.RealmsCore.gui.MainMenuGUI;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.api.databases.User;

import java.util.ArrayList;
import java.util.List;

@dev.lightdream.api.annotations.commands.SubCommand(
        aliases = "",
        parentCommand = "",
        onlyForPlayers = true
)
public class Navigator extends SubCommand {
    public Navigator() {
        super(Main.instance);
    }

    @Override
    public void execute(User user, List<String> list) {
        new MainMenuGUI(Main.instance, user).open();
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return new ArrayList<>();
    }
}
