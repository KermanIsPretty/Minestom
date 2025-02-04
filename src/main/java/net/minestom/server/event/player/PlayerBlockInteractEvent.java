package net.minestom.server.event.player;

import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerHand;
import net.minestom.server.event.trait.BlockEvent;
import net.minestom.server.event.trait.CancellableEvent;
import net.minestom.server.event.trait.PlayerInstanceEvent;
import net.minestom.server.event.trait.mutation.EventMutatorCancellable;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player interacts with a block (right-click).
 * This is also called when a block is placed.
 *
 * @param blocksItemUse Does this interaction block the normal item use? True for containers.
 */
public value record PlayerBlockInteractEvent(@NotNull Player player, @NotNull PlayerHand hand,
                                      @NotNull Block block, @NotNull BlockVec blockPosition, @NotNull Point cursorPosition,
                                      @NotNull BlockFace blockFace, boolean blocksItemUse, boolean cancelled) implements PlayerInstanceEvent, BlockEvent, CancellableEvent<PlayerBlockInteractEvent> {


    public PlayerBlockInteractEvent(@NotNull Player player, @NotNull PlayerHand hand,
                                    @NotNull Block block, @NotNull BlockVec blockPosition, @NotNull Point cursorPosition,
                                    @NotNull BlockFace blockFace) {
        this(player, hand, block, blockPosition, cursorPosition, blockFace, false, false);
    }

    /**
     * Gets if the event should block the item use.
     *
     * @return true if the item use is blocked, false otherwise
     */
    @Override
    public boolean blocksItemUse() {
        return blocksItemUse;
    }

    /**
     * Gets the block which is being interacted with.
     *
     * @return the block
     */
    @Override
    public @NotNull Block block() {
        return block;
    }

    /**
     * Gets the position of the interacted block.
     *
     * @return the block position
     */
    @Override
    public @NotNull BlockVec blockPosition() {
        return blockPosition;
    }

    /**
     * Gets the cursor position of the interacted block
     * @return the cursor position of the interaction
     */
    @Override
    public @NotNull Point cursorPosition() { return cursorPosition; }

    /**
     * Gets the hand used for the interaction.
     *
     * @return the hand used
     */
    public @NotNull PlayerHand getHand() {
        return hand;
    }

    /**
     * Gets the block face.
     *
     * @return the block face
     */
    public @NotNull BlockFace getBlockFace() {
        return blockFace;
    }

    @Override
    public @NotNull Mutator mutator() {
        return new Mutator(this);
    }

    public static class Mutator extends EventMutatorCancellable.Simple<PlayerBlockInteractEvent> {
        private boolean blocksItemUse;

        public Mutator(PlayerBlockInteractEvent event) {
            super(event);
            this.blocksItemUse = event.blocksItemUse;
        }

        /**
         * Gets if the event should block the item use.
         *
         * @return true if the item use is blocked, false otherwise
         */
        public boolean isBlockingItemUse() {
            return blocksItemUse;
        }

        /**
         * Sets the blocking item use state of this event
         * Note: If this is true, then no {@link PlayerUseItemOnBlockEvent} will be fired.
         * @param blocks - true to block item interactions, false to not block
         */
        public void setBlockingItemUse(boolean blocks) {
            this.blocksItemUse = blocks;
        }

        @Override
        public @NotNull PlayerBlockInteractEvent mutated() {
            return new PlayerBlockInteractEvent(this.originalEvent.player, this.originalEvent.hand, this.originalEvent.block, this.originalEvent.blockPosition, this.originalEvent.cursorPosition, this.originalEvent.blockFace, this.blocksItemUse, this.isCancelled());
        }
    }
}
