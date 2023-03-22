package net.toshimichi.mcbase;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Hello World!");
        Bukkit.getScheduler().runTaskTimer(this, ()-> {
            System.out.println("Hello World!");
        }, 0, 20);
    }
}
