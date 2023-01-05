package azuazu3939.lifepvp;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.util.jnbt.CompoundTag;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InventoryCheck {

    private final FileConfiguration info = LifePvP.inst().getConfig();

    public boolean invCheck(@NotNull Player player) {
       return mmCheck(player) && isHead(player) && isChest(player) && isLeg(player) && isBoots(player) && isOffHand(player);
    }

    public boolean mmCheck(@NotNull Player player) {

        check:for (ItemStack itemStack : player.getInventory().getContents()) {
            CompoundTag tag = MythicMobs.inst().getVolatileCodeHandler().getItemHandler().getNBTData(itemStack);
            if (tag != null && tag.containsKey("MYTHIC_TYPE")) {
                for (String string : info.getStringList("Item")) {
                    if (tag.getString("MYTHIC_TYPE").equals(string)) {
                        continue check;
                    }
                }
                return false;
            }
        }
        return true;
    }

    public boolean isHead(@NotNull Player player) {
        ItemStack item = player.getInventory().getHelmet();
        if (item == null) return true;
        CompoundTag tag = MythicMobs.inst().getVolatileCodeHandler().getItemHandler().getNBTData(item);
        if (tag != null && tag.containsKey("MYTHIC_TYPE")) {
            for (String string : info.getStringList("Armor")) {
                if (tag.getString("MYTHIC_TYPE").equals(string)) return true;
            }
            return false;
        }
        return true;
    }

    public boolean isChest(@NotNull Player player) {
        ItemStack item = player.getInventory().getChestplate();
        if (item == null) return true;
        CompoundTag tag = MythicMobs.inst().getVolatileCodeHandler().getItemHandler().getNBTData(item);
        if (tag != null && tag.containsKey("MYTHIC_TYPE")) {
            for (String string : info.getStringList("Armor")) {
                if (tag.getString("MYTHIC_TYPE").equals(string)) return true;
            }
            return false;
        }
        return true;
    }

    public boolean isLeg(@NotNull Player player) {
        ItemStack item = player.getInventory().getLeggings();
        if (item == null) return true;
        CompoundTag tag = MythicMobs.inst().getVolatileCodeHandler().getItemHandler().getNBTData(item);
        if (tag != null && tag.containsKey("MYTHIC_TYPE")) {
            for (String string : info.getStringList("Armor")) {
                if (tag.getString("MYTHIC_TYPE").equals(string)) return true;
            }
            return false;
        }
        return true;
    }

    public boolean isBoots(@NotNull Player player) {
        ItemStack item = player.getInventory().getBoots();
        if (item == null) return true;
        CompoundTag tag = MythicMobs.inst().getVolatileCodeHandler().getItemHandler().getNBTData(item);
        if (tag != null && tag.containsKey("MYTHIC_TYPE")) {
            for (String string : info.getStringList("Armor")) {
                if (tag.getString("MYTHIC_TYPE").equals(string)) return true;
            }
            return false;
        }
        return true;
    }

    public boolean isOffHand(@NotNull Player player) {
        ItemStack item = player.getInventory().getItemInOffHand();
        CompoundTag tag = MythicMobs.inst().getVolatileCodeHandler().getItemHandler().getNBTData(item);
        if (tag != null && tag.containsKey("MYTHIC_TYPE")) {
            for (String string : info.getStringList("Armor")) {
                if (tag.getString("MYTHIC_TYPE").equals(string)) return true;
            }
            return false;
        }
        return true;
    }
}
