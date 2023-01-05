package azuazu3939.lifepvp;

import org.bukkit.plugin.java.JavaPlugin;

public final class LifePvP extends JavaPlugin {

    private static LifePvP pvp;
    public LifePvP() {pvp = this;}
    public static LifePvP inst() {return pvp;}

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PvPListener(), this);
        getCommand("pvp").setExecutor(new LifePvPCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
