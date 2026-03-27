package me.aris.arisenderchest.listeners;

import me.aris.arisenderchest.ArisEnderChest;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryListener implements Listener {

    private void handleSave(Player player, String title, org.bukkit.inventory.Inventory inv) {
        String prefix = ArisEnderChest.getInstance().getDataManager().getTitlePrefix();
        if (title.startsWith(prefix)) {
            String targetName = title.replace(prefix + " §7- ", "");
            OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
            ArisEnderChest.getInstance().getDataManager().saveEnderChest(target.getUniqueId(), inv);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        handleSave((Player) event.getWhoClicked(), event.getView().getTitle(), event.getInventory());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDrag(InventoryDragEvent event) {
        handleSave((Player) event.getWhoClicked(), event.getView().getTitle(), event.getInventory());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClose(InventoryCloseEvent event) {
        handleSave((Player) event.getPlayer(), event.getView().getTitle(), event.getInventory());
    }
                   }
