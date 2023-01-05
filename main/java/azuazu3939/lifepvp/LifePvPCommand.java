package azuazu3939.lifepvp;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LifePvPCommand implements CommandExecutor {

    protected static Map<Player, Integer> life = new HashMap<>();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("LifePvP.Command.pvp")) {
                    player.sendMessage(ChatColor.RED + "権限がありません。");
                    return true;
                }

                if (player.getWorld().getName().equals("lifeFFA")) {
                    player.sendMessage(ChatColor.RED + "lifeFFAでは再度ランダムTPで場所を移動できません。");
                    return true;
                }

                String playerName = args[0];
                Player name;
                try {
                    name = Bukkit.getPlayer(playerName);
                    if (name == null) {
                        player.sendMessage(ChatColor.RED + "そのプレイヤーは存在していません。");
                        return true;
                    }
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "そのプレイヤーは存在していません。");
                    return true;
                }
                if (new InventoryCheck().invCheck(name)) {
                    for (int i = 0;i < 360; i++) {
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
                                    name.teleport(finalLoc);
                                    name.sendMessage(ChatColor.GREEN + "LifeFFAにテレポートしました。");
                                    life.put(name, LifePvP.inst().getConfig().getInt("PlayerLife", 2));
                                    return true;
                                }
                            }
                        }
                    }
                } else {
                    name.sendMessage(ChatColor.RED + "LifeFFAには指定アイテム以外のスキル持ちmmアイテムは持ち込めません。");
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
}
