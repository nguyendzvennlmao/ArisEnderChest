package me.aris.arisenderchest;

import org.bukkit.plugin.java.JavaPlugin;
import me.aris.arisenderchest.commands.ECCommand;
import me.aris.arisenderchest.listeners.InventoryListener;
import me.aris.arisenderchest.managers.DataManager;

public class ArisEnderChest extends JavaPlugin {
    private static ArisEnderChest instance;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        instance = this;
        this.dataManager = new DataManager();
        
        getCommand("ec").setExecutor(new ECCommand());
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }

    @Override
    public void onDisable() {
        if (dataManager != null) {
            dataManager.saveAll();
        }
    }

    public static ArisEnderChest getInstance() {
        return instance;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
              }
