package io.github.interstena.spongetest;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

@Plugin(id = "spongetest", name = "SpongeTest", version = "1.0", description = "Testing sponge features")

/**
 * Created by interwall on 02.07.17.
 */
public class SpongeTest {

    @Inject private PluginContainer container;

    @Inject
    private Logger logger;
    CommandSpec helloWorld = CommandSpec.builder()
            .description(Text.of("Hello World Command"))
            .executor(new HelloWorldCommand())
            .build();
    CommandSpec pasteBlock = CommandSpec.builder()
            .description(Text.of("Paste Block Command"))
            .executor(new PasteBlockCommand())
            .build();

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        Sponge.getCommandManager().register(this, helloWorld, "helloworld");
        Sponge.getCommandManager().register(this, pasteBlock, "pasteblock");
        logger.info("Plugin loaded.");
    }

    class HelloWorldCommand implements CommandExecutor {

        @Override
        public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
            src.sendMessage(Text.of("Hello World!"));
            return CommandResult.success();
        }
    }
    public void setBlock(Location<World> blockLoc, Object myPluginInstance) {
        blockLoc.setBlockType(BlockTypes.GOLD_BLOCK, Cause.source(myPluginInstance).build());
    }
    class PasteBlockCommand implements CommandExecutor {

        @Override
        public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
            src.sendMessage(Text.of("Pasting Block..."));
            Player player = (Player) src;
            Location<World> loc = player.getLocation();
            setBlock(loc.add(0, -1, 0), container);
            return CommandResult.success();
        }
    }
}