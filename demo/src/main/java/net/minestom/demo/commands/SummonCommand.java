package net.minestom.demo.commands;

import net.minestom.demo.entity.ZombieCreature;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.arguments.ArgumentEnum;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentEntityType;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
import net.minestom.server.command.builder.condition.Conditions;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.*;
import net.minestom.server.utils.location.RelativeVec;
import org.jetbrains.annotations.NotNull;

public class SummonCommand extends Command {

    private final ArgumentEntityType entity;
    private final Argument<RelativeVec> pos;
    private final Argument<EntityClass> entityClass;
    private final ArgumentInteger amount;

    public SummonCommand() {
        super("summon");
        setCondition(Conditions::playerOnly);

        entity = ArgumentType.EntityType("entity type");
        pos = ArgumentType.RelativeVec3("pos").setDefaultValue(() -> new RelativeVec(
                new Vec(0, 0, 0),
                RelativeVec.CoordinateType.RELATIVE,
                true, true, true
        ));
        entityClass = ArgumentType.Enum("class", EntityClass.class)
                .setFormat(ArgumentEnum.Format.LOWER_CASED)
                .setDefaultValue(EntityClass.CREATURE);

        amount = ArgumentType.Integer("amount");
        amount.setDefaultValue(1);

        addSyntax(this::execute, entity, pos, entityClass, amount);
        setDefaultExecutor((sender, context) -> sender.sendMessage("Usage: /summon <type> <x> <y> <z> <class> [amount]"));
    }

    private void execute(@NotNull CommandSender commandSender, @NotNull CommandContext commandContext) {
        final EntityClass entityClass = commandContext.get(this.entityClass);
        final EntityType entityType = commandContext.get(this.entity);
        final Vec pos = commandContext.get(this.pos).fromSender(commandSender);
        final int amount = commandContext.get(this.amount);

        for (int i = 0; i < amount; i++) {
            final Entity entity = entityClass.instantiate(entityType);
            //noinspection ConstantConditions - One couldn't possibly execute a command without being in an instance
            entity.setInstance(((Player) commandSender).getInstance(), pos);
        }
    }

    @SuppressWarnings("unused")
    enum EntityClass {
        BASE(Entity::new),
        LIVING(LivingEntity::new),
        CREATURE(EntityCreature::new),
        ZOMBIE(ZombieCreature::new);
        private final EntityFactory factory;

        EntityClass(EntityFactory factory) {
            this.factory = factory;
        }

        public Entity instantiate(EntityType type) {
            return factory.newInstance(type);
        }
    }

    @FunctionalInterface
    interface EntityFactory {
        Entity newInstance(EntityType type);
    }
}
