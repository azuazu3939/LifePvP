package azuazu3939.lifepvp;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.util.jnbt.CompoundTag;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;


public class InventoryCheck {

    private final FileConfiguration info = LifePvP.inst().getConfig();

    public boolean invCheck(@NotNull Player player) {
        for (PotionEffectType type: PotionEffectType.values()) {
            player.removePotionEffect(type);
        }

        AttributeInstance health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (health != null) {
            for (AttributeModifier modifier: health.getModifiers()) {
                if (modifier.getName().equals("PlayerBuff.SetHealth")) health.removeModifier(modifier);
            }
        }

        AttributeInstance armor = player.getAttribute(Attribute.GENERIC_ARMOR);
        if (armor != null) {
            for (AttributeModifier modifier: armor.getModifiers()) {
                if (modifier.getName().equals("PlayerBuff.SetArmor")) armor.removeModifier(modifier);
            }
        }

        AttributeInstance toughness = player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
        if (toughness != null) {
            for (AttributeModifier modifier: toughness.getModifiers()) {
                if (modifier.getName().equals("PlayerBuff.SetArmor_Toughness")) toughness.removeModifier(modifier);
            }
        }

        return mmCheck(player) && isHead(player) && isChest(player) && isLeg(player) && isBoots(player) && isOffHand(player);
    }

    public boolean mmCheck(@NotNull Player player) {

        check:for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null) continue;
            CompoundTag tag = MythicMobs.inst().getVolatileCodeHandler().getItemHandler().getNBTData(itemStack);
            if (tag != null && tag.containsKey("MYTHIC_TYPE")) {
                for (String string : info.getStringList("Item")) {
                    if (tag.getString("MYTHIC_TYPE").equals(string)) {
                        continue check;
                    }
                }
                for (String string : info.getStringList("Armor")) {
                    if (tag.getString("MYTHIC_TYPE").equals(string)) {
                        continue check;
                    }
                }
            }
            player.sendMessage(itemStack.getItemMeta().getDisplayName() + ChatColor.RESET + "はlifeFFAワールド内では使用できません。");
            return false;
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
            player.sendMessage(item.getItemMeta().getDisplayName() + ChatColor.RESET + "はlifeFFAワールド内では使用できません。");
            return false;
        }
        player.sendMessage(item.getItemMeta().getDisplayName() + ChatColor.RESET + "はlifeFFAワールド内では使用できません。");
        return false;
    }

    public boolean isChest(@NotNull Player player) {
        ItemStack item = player.getInventory().getChestplate();
        if (item == null) return true;
        CompoundTag tag = MythicMobs.inst().getVolatileCodeHandler().getItemHandler().getNBTData(item);
        if (tag != null && tag.containsKey("MYTHIC_TYPE")) {
            for (String string : info.getStringList("Armor")) {
                if (tag.getString("MYTHIC_TYPE").equals(string)) {
                    return true;
                }
            }
            player.sendMessage(item.getItemMeta().getDisplayName() + ChatColor.RESET + "はlifeFFAワールド内では使用できません。");
            return false;
        }
        player.sendMessage(item.getItemMeta().getDisplayName() + ChatColor.RESET + "はlifeFFAワールド内では使用できません。");
        return false;
    }

    public boolean isLeg(@NotNull Player player) {
        ItemStack item = player.getInventory().getLeggings();
        if (item == null) return true;
        CompoundTag tag = MythicMobs.inst().getVolatileCodeHandler().getItemHandler().getNBTData(item);
        if (tag != null && tag.containsKey("MYTHIC_TYPE")) {
            for (String string : info.getStringList("Armor")) {
                if (tag.getString("MYTHIC_TYPE").equals(string)) return true;
            }
            player.sendMessage(item.getItemMeta().getDisplayName() + ChatColor.RESET + "はlifeFFAワールド内では使用できません。");
            return false;
        }
        player.sendMessage(item.getItemMeta().getDisplayName() + ChatColor.RESET + "はlifeFFAワールド内では使用できません。");
        return false;
    }

    public boolean isBoots(@NotNull Player player) {
        ItemStack item = player.getInventory().getBoots();
        if (item == null) return true;
        CompoundTag tag = MythicMobs.inst().getVolatileCodeHandler().getItemHandler().getNBTData(item);
        if (tag != null && tag.containsKey("MYTHIC_TYPE")) {
            for (String string : info.getStringList("Armor")) {
                if (tag.getString("MYTHIC_TYPE").equals(string)) return true;
            }
            player.sendMessage(item.getItemMeta().getDisplayName() + ChatColor.RESET + "はlifeFFAワールド内では使用できません。");
            return false;
        }
        player.sendMessage(item.getItemMeta().getDisplayName() + ChatColor.RESET + "はlifeFFAワールド内では使用できません。");
        return false;
    }

    public boolean isOffHand(@NotNull Player player) {
        ItemStack item = player.getInventory().getItemInOffHand();
        if (item.getType().isAir()) return true;
        CompoundTag tag = MythicMobs.inst().getVolatileCodeHandler().getItemHandler().getNBTData(item);
        if (tag != null && tag.containsKey("MYTHIC_TYPE")) {
            for (String string : info.getStringList("Armor")) {
                if (tag.getString("MYTHIC_TYPE").equals(string)) return true;
            }
            player.sendMessage(item.getItemMeta().getDisplayName() + ChatColor.RESET + "はlifeFFAワールド内では使用できません。");
            return false;
        }
        player.sendMessage(item.getItemMeta().getDisplayName() + ChatColor.RESET + "はlifeFFAワールド内では使用できません。");
        return false;
    }
}
