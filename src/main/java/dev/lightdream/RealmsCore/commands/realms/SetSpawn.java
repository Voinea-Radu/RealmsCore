package dev.lightdream.RealmsCore.commands.realms;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.RealmsCore.database.Realm;
import dev.lightdream.RealmsCore.utils.Utils;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.api.databases.User;

import java.util.List;

@dev.lightdream.api.annotations.commands.SubCommand(
        aliases = "setSpawn",
        onlyForPlayers = true,
        parentCommand = "realms"
)
public class SetSpawn extends SubCommand {
    public SetSpawn() {
        super(Main.instance);
    }

    @Override
    public void execute(User u, List<String> list) {
        dev.lightdream.RealmsCore.database.User user = (dev.lightdream.RealmsCore.database.User) u;
        Realm realm = Utils.getOwnedRealm(user);

        if (realm == null) {
            return;
        }

        realm.setSpawn(user.getLocation());
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
