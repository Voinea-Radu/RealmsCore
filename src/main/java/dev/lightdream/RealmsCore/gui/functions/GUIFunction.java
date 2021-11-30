package dev.lightdream.RealmsCore.gui.functions;

import dev.lightdream.RealmsCore.database.User;
import dev.lightdream.api.gui.GUI;

import java.util.List;

public interface GUIFunction {

    void execute(GUI gui, User user, List<String> args);

}
