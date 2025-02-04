package net.minestom.server.event.player;

import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.CancellableEvent;
import net.minestom.server.event.trait.PlayerInstanceEvent;
import net.minestom.server.event.trait.mutation.EventMutatorCancellable;
import net.minestom.server.utils.MathUtils;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player change his held slot (by pressing 1-9 keys).
 */
public value record PlayerChangeHeldSlotEvent(@NotNull Player player, byte slot, boolean cancelled) implements PlayerInstanceEvent, CancellableEvent<PlayerChangeHeldSlotEvent> {

    public PlayerChangeHeldSlotEvent(@NotNull Player player, byte slot) {
        this(player, slot, false);
    }

    /**
     * Gets the slot which the player will hold.
     *
     * @return the future slot
     */
    @Override
    public byte slot() {
        return slot;
    }

    @Override
    public @NotNull Mutator mutator() {
        return new Mutator(this);
    }

    public static class Mutator extends EventMutatorCancellable.Simple<PlayerChangeHeldSlotEvent> {
        private byte slot;

        public Mutator(PlayerChangeHeldSlotEvent event) {
            super(event);

            this.slot = event.slot;
        }

        /**
         * Gets the slot which the player will hold.
         *
         * @return the future slot
         */
        public byte getSlot() {
            return slot;
        }

        /**
         * Changes the final held slot of the player.
         *
         * @param slot the new held slot
         * @throws IllegalArgumentException if <code>slot</code> is not between 0 and 8
         */
        public void setSlot(byte slot) {
            Check.argCondition(!MathUtils.isBetween(slot, 0, 8), "The held slot needs to be between 0 and 8");
            this.slot = slot;
        }

        @Override
        public @NotNull PlayerChangeHeldSlotEvent mutated() {
            return new PlayerChangeHeldSlotEvent(this.originalEvent.player, this.slot, this.isCancelled());
        }
    }
}
