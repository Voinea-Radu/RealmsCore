package dev.lightdream.RealmsCore.commands.realms;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.dto.Test;
import dev.lightdream.api.dto.TestBattery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@dev.lightdream.api.annotations.commands.SubCommand(
        aliases = "tp",
        parentCommand = "realms",
        onlyForPlayers = true
)
public class Tp extends SubCommand {
    public Tp() {
        super(Main.instance);
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Override
    public void execute(User u, List<String> list) {
        new TestBattery(Main.instance, Arrays.asList(
                new Test(u.id, test -> {
                    dev.lightdream.RealmsCore.database.User user = Main.instance.databaseManager.getUser(u);

                    if (user == null) {
                        test.submitResults(-1);
                        return;
                    }

                    test.submitResults(user.id);
                })
        )).test();

        dev.lightdream.RealmsCore.database.User user = Main.instance.databaseManager.getUser(u);
        user.tpRealm();
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return new ArrayList<>();
    }
}
