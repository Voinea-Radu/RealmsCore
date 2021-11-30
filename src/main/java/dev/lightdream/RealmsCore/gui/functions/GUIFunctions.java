package dev.lightdream.RealmsCore.gui.functions;

import dev.lightdream.RealmsCore.gui.functions.functions.*;

@SuppressWarnings("unused")
public enum GUIFunctions {

    NEXT_PAGE(new NextPage()),
    OPEN_GUI(new OpenGUI()),
    TELEPORT(new Teleport()),
    KICK(new Kick()),
    BACK_PAGE(new BackPage());

    public GUIFunction function;

    GUIFunctions(GUIFunction function) {
        this.function = function;
    }
}
