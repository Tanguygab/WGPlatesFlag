package io.github.tanguygab.wGPlatesFlag;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.session.SessionManager;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import com.sk89q.worldguard.WorldGuard;

public final class WGPlatesFlag extends JavaPlugin {

    public static StateFlag PRESSURE_PLATES_FLAG;

    @Override
    public void onLoad() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            StateFlag flag = new StateFlag("pressure-plates", true);
            registry.register(flag);
            PRESSURE_PLATES_FLAG = flag;
        } catch (FlagConflictException e) {
            Flag<?> existing = registry.get("pressure-plates");
            if (existing instanceof StateFlag) {
                PRESSURE_PLATES_FLAG = (StateFlag) existing;
                return;
            }
            // types don't match - this is bad news! some other plugin conflicts with you
            // hopefully this never actually happens
            getLogger().warning("Another plugin is using the pressure-plates flag! WGPlatesFlag won't work.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onEnable() {
        SessionManager sessionManager = WorldGuard.getInstance().getPlatform().getSessionManager();
        // second param allows for ordering of handlers - see the JavaDocs
        sessionManager.registerHandler(PlatesHandler.FACTORY, null);
        getServer().getPluginManager().registerEvents(new PlatesListener(), this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
