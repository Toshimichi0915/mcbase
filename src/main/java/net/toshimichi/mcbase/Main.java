package net.toshimichi.mcbase;

import lombok.SneakyThrows;
import net.toshimichi.mcbase.services.ServiceRegistry;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

public class Main extends JavaPlugin {

    private ServiceRegistry serviceRegistry;

    @SneakyThrows
    @Override
    public void onEnable() {

        // load config
        Path configPath = getDataFolder().toPath().resolve("config.yml");
        PluginConfig pluginConfig = PluginConfig.load(configPath);

        // load services
        serviceRegistry = new ServiceRegistry();
        serviceRegistry.setEnabled(true);
    }

    @Override
    public void onDisable() {
        serviceRegistry.setEnabled(false);
    }
}
