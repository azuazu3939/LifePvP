package azuazu3939.lifepvp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PvPItemListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        try {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.WHITE + "持ち込み可能なアイテムリスト。");
                player.sendMessage(ChatColor.AQUA+ "Itemリスト");
                for (String string: LifePvP.inst().getConfig().getStringList("Item")) {
                    player.sendMessage(string);
                }
                player.sendMessage(ChatColor.AQUA + "Armorリスト");
                for (String string: LifePvP.inst().getConfig().getStringList("Armor")) {
                    player.sendMessage(string);
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Player実行のみ表示可能です。");
            }
            return true;

        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "正しく入力してください。");
            return true;
        }
    }
}
