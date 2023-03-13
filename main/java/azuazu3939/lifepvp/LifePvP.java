package azuazu3939.lifepvp;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static azuazu3939.lifepvp.LifePvPCommand.life;

public final class LifePvP extends JavaPlugin {

    private static LifePvP pvp;
    public LifePvP() {pvp = this;}
    public static LifePvP inst() {return pvp;}

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PvPListener(), this);
        Objects.requireNonNull(getCommand("pvp")).setExecutor(new LifePvPCommand());
        Objects.requireNonNull(getCommand("pvpItemList")).setExecutor(new PvPItemListCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        life.clear();
    }
}
