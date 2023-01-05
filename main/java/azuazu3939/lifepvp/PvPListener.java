package azuazu3939.lifepvp;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

import static azuazu3939.lifepvp.LifePvPCommand.life;

public class PvPListener implements Listener {

    int count = 0;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        if (player.getWorld().getName().equals("lifeFFA")) {
            count++;
            if (count >= 5) {
                count = 0;
                double count1 = 0.0;
                double count2 = 0.0;
                double count3 = 0.0;
                double count4 = 0.0;
                double count5 = 0.0;
                double count6 = 0.0;
                removeAttribute(player);
                AttributeInstance attr1 = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (attr1 == null) return;
                for (AttributeModifier modifier: attr1.getModifiers()) {
                    count1 += modifier.getAmount();
                }
                double set1 = LifePvP.inst().getConfig().getDouble("Player.Health") - count1;
                attr1.addModifier(new AttributeModifier("LifePvP_Health", set1, AttributeModifier.Operation.ADD_NUMBER));

                AttributeInstance attr2 = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
                if (attr2 == null) return;
                for (AttributeModifier modifier: attr2.getModifiers()) {
                    count2 += modifier.getAmount();
                }
                double set2 = LifePvP.inst().getConfig().getDouble("Player.AttackSpeed") - count2;
                attr2.addModifier(new AttributeModifier("LifePvP_AttackSpeed", set2, AttributeModifier.Operation.ADD_NUMBER));

                AttributeInstance attr3 = player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if (attr3 == null) return;
                for (AttributeModifier modifier: attr3.getModifiers()) {
                    count3 += modifier.getAmount();
                }
                double set3 = LifePvP.inst().getConfig().getDouble("Player.Toughness") - count3;
                attr3.addModifier(new AttributeModifier("LifePvP_Toughness", set3, AttributeModifier.Operation.ADD_NUMBER));

                AttributeInstance attr4 = player.getAttribute(Attribute.GENERIC_ARMOR);
                if (attr4 == null) return;
                for (AttributeModifier modifier: attr4.getModifiers()) {
                    count4 += modifier.getAmount();
                }
                double set4 = LifePvP.inst().getConfig().getDouble("Player.Armor") - count4;
                attr4.addModifier(new AttributeModifier("LifePvP_Toughness", set4, AttributeModifier.Operation.ADD_NUMBER));

                AttributeInstance attr5 = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                if (attr5 == null) return;
                for (AttributeModifier modifier: attr5.getModifiers()) {
                    count5 += modifier.getAmount();
                }
                double set5 = LifePvP.inst().getConfig().getDouble("Player.Damage") - count5;
                attr5.addModifier(new AttributeModifier("LifePvP_Damage", set5, AttributeModifier.Operation.ADD_NUMBER));

                AttributeInstance attr6 = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if (attr6 == null) return;
                for (AttributeModifier modifier: attr6.getModifiers()) {
                    count6 += modifier.getAmount();
                }
                double setCount6 = -count6;
                if (player.hasPotionEffect(PotionEffectType.SPEED)) {
                    setCount6 += (double)(Objects.requireNonNull(player.getPotionEffect(PotionEffectType.SPEED)).getAmplifier() + 1) * 0.2;
                }

                attr6.addModifier(new AttributeModifier("LifePvP_Speed", setCount6, AttributeModifier.Operation.ADD_SCALAR));
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (world.getName().equals("lifeFFA")) {
            Bukkit.getScheduler().runTaskLater(LifePvP.inst(), () -> {
                player.performCommand("spawn");
                player.sendMessage(ChatColor.WHITE + "lifeFFAにいたため、spawnに戻されました。");
            }, 60L);
        }
        removeAttribute(player);
    }

    @EventHandler
    public void onChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (!world.getName().equals("lifeFFA")) {
            removeAttribute(player);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
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

    public void removeAttribute(Player player) {

        AttributeInstance attr1 = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (attr1 == null) return;
        for (AttributeModifier modifier: attr1.getModifiers()) {
            if (modifier.getName().equals("LifePvP_Health")) {
                attr1.removeModifier(modifier);
            }
        }

        AttributeInstance attr2 = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (attr2 == null) return;
        for (AttributeModifier modifier: attr2.getModifiers()) {
            if (modifier.getName().equals("LifePvP_AttackSpeed")) {
                attr2.removeModifier(modifier);
            }
        }

        AttributeInstance attr3 = player.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
        if (attr3 == null) return;
        for (AttributeModifier modifier: attr3.getModifiers()) {
            if (modifier.getName().equals("LifePvP_Toughness")) {
                attr3.removeModifier(modifier);
            }
        }

        AttributeInstance attr4 = player.getAttribute(Attribute.GENERIC_ARMOR);
        if (attr4 == null) return;
        for (AttributeModifier modifier: attr4.getModifiers()) {
            if (modifier.getName().equals("LifePvP_Armor")) {
                attr4.removeModifier(modifier);
            }
        }

        AttributeInstance attr5 = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        if (attr5 == null) return;
        for (AttributeModifier modifier: attr5.getModifiers()) {
            if (modifier.getName().equals("LifePvP_Damage")) {
                attr5.removeModifier(modifier);
            }
        }

        AttributeInstance attr6 = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        if (attr6 == null) return;
        for (AttributeModifier modifier: attr6.getModifiers()) {
            if (modifier.getName().equals("LifePvP_Speed")) {
                attr6.removeModifier(modifier);
            }
        }
    }
}
