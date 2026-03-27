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
                ItemStack[] content = items.toArray(new ItemStack[0]);
                inv.setContents(content);
            }
        }
        return inv;
    }

    public void saveEnderChest(UUID uuid, ItemStack[] contents) {
        Bukkit.getScheduler().runTaskAsynchronously(ArisEnderChest.getInstance(), () -> {
            File dataFolder = new File(ArisEnderChest.getInstance().getDataFolder(), "data");
            if (!dataFolder.exists()) dataFolder.mkdirs();
            File f = new File(dataFolder, uuid + ".yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(f);
            config.set("items", contents);
            try {
                config.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void saveAll() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            String title = p.getOpenInventory().getTitle();
            if (title.startsWith(titlePrefix)) {
                String targetName = title.replace(titlePrefix + " §7- ", "");
                OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
                saveEnderChest(target.getUniqueId(), p.getOpenInventory().getTopInventory().getContents());
            }
        }
    }

    public String getTitlePrefix() {
        return titlePrefix;
    }
          }
