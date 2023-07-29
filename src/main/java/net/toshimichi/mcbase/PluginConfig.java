package net.toshimichi.mcbase;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ConfigSerializable
public class PluginConfig {

    public static PluginConfig load(Path path) throws IOException {
        PluginConfig config;

        if (Files.exists(path)) {
            config = new PluginConfig();
        } else {
            config = YamlConfigurationLoader.builder()
                    .path(path)
                    .build()
                    .load()
                    .get(PluginConfig.class);
        }

        return config;
    }

    public void save(Path path) throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        ConfigurationNode node = YamlConfigurationLoader.builder()
                .build()
                .createNode();

        node.set(this);

        YamlConfigurationLoader.builder()
                .path(path)
                .build()
                .save(node);
    }
}
