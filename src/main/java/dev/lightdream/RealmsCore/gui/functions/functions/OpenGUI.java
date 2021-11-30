package dev.lightdream.RealmsCore.gui.functions.functions;

import dev.lightdream.RealmsCore.database.User;
import dev.lightdream.RealmsCore.gui.functions.GUIFunction;
import dev.lightdream.api.gui.GUI;

import java.util.List;

public class OpenGUI implements GUIFunction {

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public void execute(GUI gui, User user, List<String> args) {
        if (args.size() == 0) {
            return;
        }

        String guiName = args.get(0);

        switch (guiName) {

        }
    }
}
