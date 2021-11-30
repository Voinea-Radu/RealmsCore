package dev.lightdream.RealmsCore.managers;

import dev.lightdream.RealmsCore.Main;
import dev.lightdream.RealmsCore.database.Realm;
import dev.lightdream.RealmsCore.database.User;
import dev.lightdream.RealmsCore.utils.Utils;
import dev.lightdream.api.dto.PluginLocation;
import dev.lightdream.api.utils.Debugger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventManager implements Listener {

    public EventManager(Main plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        User user = Main.instance.databaseManager.getUser(event.getPlayer());

        Debugger.info("Received event");
        if (!event.getPlayer().getLocation().getWorld().getName().equals(Main.instance.config.realmsWorld)) {
            Debugger.info("The player is not in the realms world");
            return;
        }

        Realm realm = Main.instance.databaseManager.getRealm(new PluginLocation(event.getPlayer().getLocation()));
        if (realm == null) {
            Debugger.info("The player is not at any realms");
            return;
        }

        Debugger.info("Teleporting the player");
        realm.teleport(user);
    }

    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent event){
        User user = Main.instance.databaseManager.getUser(event.getPlayer());

        if(Utils.getOwnedRealm(user)==null){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent event){
        User user = Main.instance.databaseManager.getUser(event.getPlayer());

        if(Utils.getOwnedRealm(user)==null){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        User user = Main.instance.databaseManager.getUser(event.getPlayer());

        if(Utils.getOwnedRealm(user)==null){
            event.setCancelled(true);
        }
    }

}
