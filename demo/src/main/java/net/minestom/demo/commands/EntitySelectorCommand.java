package net.minestom.demo.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntitySelector;

import java.util.List;

public class EntitySelectorCommand extends Command {

    public EntitySelectorCommand() {
        super("ent");

        setDefaultExecutor((sender, context) -> System.out.println("DEFAULT"));

        var argumentEntity = ArgumentType.Entity("entities");

        setArgumentCallback((sender, exception) -> exception.printStackTrace(), argumentEntity);

        addSyntax(this::executor, argumentEntity);

    }

    private void executor(CommandSender commandSender, CommandContext context) {
        EntitySelector<Entity> selector = context.get("entities");
        List<Entity> entities = commandSender.selectEntity(selector).toList();
        commandSender.sendMessage("found " + entities.size() + " entities");
    }
}
