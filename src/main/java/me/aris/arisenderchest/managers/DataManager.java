package me.aris.arisenderchest.managers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.aris.arisenderchest.ArisEnderChest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class DataManager {
    
    private final String titlePrefix = "§8ᴇɴᴅᴇʀᴄʜᴇsᴛ";

    public Inventory getEnderChest(OfflinePlayer target, int rows) {
        String finalTitle = titlePrefix + " §7- " + target.getName();
        Inventory inv = Bukkit.createInventory(null, rows * 9, finalTitle);
        
        File f = new File(ArisEnderChest.getInstance().getDataFolder(), "data/" + target.getUniqueId() + ".yml");
        if (f.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(f);
            List<?> items = config.getList("items");
            if (items != null) {
                for (int i = 0; i < items.size() && i < inv.getSize(); i++) {
                    ItemStack item = (ItemStack) items.get(i);
                    if (item != null) inv.setItem(i, item);
                }
            }
        }
        return inv;
    }

    public void saveEnderChest(UUID uuid, Inventory inv) {
        Bukkit.getScheduler().runTaskAsynchronously(ArisEnderChest.getInstance(), () -> {
            File dataFolder = new File(ArisEnderChest.getInstance().getDataFolder(), "data");
            if (!dataFolder.exists()) dataFolder.mkdirs();
            File f = new File(dataFolder, uuid + ".yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(f);
            config.set("items", inv.getContents());
            try {
                config.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void saveAll() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getOpenInventory().getTitle().startsWith(titlePrefix)) {
                String title = p.getOpenInventory().getTitle();
                String targetName = title.replace(titlePrefix + " §7- ", "");
                OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
                saveEnderChest(target.getUniqueId(), p.getOpenInventory().getTopInventory());
            }
        }
    }

    public String getTitlePrefix() {
        return titlePrefix;
    }
          }
