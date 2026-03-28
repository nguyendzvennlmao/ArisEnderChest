package me.aris.arisenderchest.commands;

import me.aris.arisenderchest.ArisEnderChest;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ECCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (args.length == 0) {
            if (!player.hasPermission("arisenderchest.use")) return true;
            
            int rows = 3;
            if (player.hasPermission("arisenderchest.row.6")) rows = 6;
            else if (player.hasPermission("arisenderchest.row.5")) rows = 5;
            else if (player.hasPermission("arisenderchest.row.4")) rows = 4;
            else if (player.hasPermission("arisenderchest.row.3")) rows = 3;

            Inventory ec = ArisEnderChest.getInstance().getDataManager().getEnderChest(player, rows);
            player.openInventory(ec);
        } else {
            if (!player.hasPermission("arisenderchest.admin")) return true;
            @SuppressWarnings("deprecation")
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            Inventory ec = ArisEnderChest.getInstance().getDataManager().getEnderChest(target, 6);
            player.openInventory(ec);
        }
        return true;
    }
    }
