package me.rtn.ch;

import me.rtn.ch.commands.TitleStringReplacementTester;
import me.rtn.ch.events.PlayerJoin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public final class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Stream.of(
                new PlayerJoin()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

        getCommand("trt").setExecutor(new TitleStringReplacementTester());
    }

    @Override
    public void onDisable() {

    }
}
