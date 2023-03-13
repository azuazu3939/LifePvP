package azuazu3939.lifepvp;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;

import static azuazu3939.lifepvp.LifePvPCommand.life;


public class PvPListener implements Listener {

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event) {

        Player player = event.getPlayer();
        World world = player.getWorld();
        if (world.getName().equals("lifeFFA")) {
            Bukkit.getScheduler().runTaskLater(LifePvP.inst(), () -> {
                player.performCommand("spawn");
                player.sendMessage(ChatColor.WHITE + "lifeFFAにいたため、spawnに戻されました。");
            }, 60L);
        }
        life.remove(player);
    }

    @EventHandler
    public void onChange(@NotNull PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (!world.getName().equals("lifeFFA")) {
            life.remove(player);
        }
    }

    @EventHandler
    public void onRespawn(@NotNull PlayerRespawnEvent event) {

        //RandomSpawn
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals("lifeFFA")) {
            if (life.containsKey(player) && life.get(player) >= 1) {
                life.replace(player, life.get(player) - 1);
                Bukkit.getScheduler().runTaskLater(LifePvP.inst(), () -> {
                    for(int i = 0; i < 360; ++i) {
                        for(int y = 24; y < 70; ++y) {
                            World world = Bukkit.getWorld("lifeFFA");
                            int in = (int)(Math.random() * 128.0);
                            Location loc = new Location(world, 70.0, 31.0, -188.0);
                            int x = (int)(loc.getX() + Math.sin(i) * (double)in);
                            int z = (int)(loc.getZ() + Math.cos(i) * (double)in);
                            Location location = new Location(world, (double)x + 0.5, y, (double)z + 0.5);
                            if (location.getBlock().getType() == Material.GRASS_BLOCK) {
                                Block block = location.getBlock().getRelative(BlockFace.UP);
                                Block block1 = block.getRelative(BlockFace.UP);
                                if (block.getType() == Material.AIR && block1.getType() == Material.AIR) {
                                    Location finalLoc = new Location(world, location.getX(), location.getY() + 1.0, location.getZ());
                                    player.teleport(finalLoc);
                                    player.sendMessage(ChatColor.GREEN + "リスポーンしました。");
                                    return;
                                }
                            }
                        }
                    }
                }, 20L);

            } else {
                player.sendMessage(ChatColor.GREEN + "復活回数がないためスポーンへ転送します。");
                Bukkit.getScheduler().runTaskLater(LifePvP.inst(), () -> {
                    player.performCommand("spawn");
                    life.remove(player);
                }, 20L);
            }
        }
    }
}
