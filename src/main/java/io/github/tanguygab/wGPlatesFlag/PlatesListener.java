package io.github.tanguygab.wGPlatesFlag;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlatesListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlates(PlayerInteractEvent e) {
        if (e.getAction() != Action.PHYSICAL) return;
        Block plate = e.getClickedBlock();
        if (plate == null || !plate.getType().toString().contains("PRESSURE_PLATE")) return;

        LocalPlayer player = WorldGuardPlugin.inst().wrapPlayer(e.getPlayer());
        PlatesHandler handler = WorldGuard.getInstance().getPlatform().getSessionManager().get(player).getHandler(PlatesHandler.class);

        if (handler == null || handler.getPlates() == null) return;
        e.setCancelled(handler.getPlates() == StateFlag.State.DENY);
    }

}
