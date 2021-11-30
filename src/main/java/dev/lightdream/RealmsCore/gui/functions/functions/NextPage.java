package dev.lightdream.RealmsCore.gui.functions.functions;

import dev.lightdream.RealmsCore.database.User;
import dev.lightdream.RealmsCore.gui.functions.GUIFunction;
import dev.lightdream.api.gui.GUI;

import java.util.List;

public class NextPage implements GUIFunction {
    @Override
    public void execute(GUI gui, User user, List<String> args) {
        gui.nextPage();
    }
}
