package jp.kentan.example.commodore_test;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CommodoreTestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // register your command executor as normal.
        PluginCommand command = getCommand("mycommand");
        command.setExecutor(new MyCommandExecutor());

        // check if brigadier is supported
        if (CommodoreProvider.isSupported()) {

            // get a commodore instance
            Commodore commodore = CommodoreProvider.getCommodore(this);

            // register your completions.
            registerCompletions(commodore, command);
        }
    }

    // You will need to put this method inside another class to prevent classloading
    // errors when your plugin loads on pre 1.13 versions.
    private static void registerCompletions(Commodore commodore, PluginCommand command) {
        commodore.register(command, LiteralArgumentBuilder.literal("mycommand")
                .then(RequiredArgumentBuilder.argument("some-argument", StringArgumentType.string()))
                .then(RequiredArgumentBuilder.argument("some-other-argument", BoolArgumentType.bool()))
        );
    }
}
