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
            int rows = getRows(player);
            Inventory ec = ArisEnderChest.getInstance().getDataManager().getEnderChest(player, rows);
            player.openInventory(ec);
        } else {
            if (!player.hasPermission("arisenderchest.admin")) return true;
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            int rows = 6; 
            Inventory ec = ArisEnderChest.getInstance().getDataManager().getEnderChest(target, rows);
            player.openInventory(ec);
        }
        return true;
    }

    private int getRows(Player player) {
        if (player.hasPermission("arisenderchest.row.6")) return 6;
        if (player.hasPermission("arisenderchest.row.5")) return 5;
        if (player.hasPermission("arisenderchest.row.4")) return 4;
        return 3;
    }
}
