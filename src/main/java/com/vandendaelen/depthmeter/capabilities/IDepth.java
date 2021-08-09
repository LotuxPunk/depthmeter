package com.vandendaelen.depthmeter.capabilities;

import com.vandendaelen.depthmeter.misc.DepthLevels;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface IDepth  extends INBTSerializable<CompoundTag> {
    void tick(Player playerEntity);
    DepthLevels getDepth();
    int getPosSeaLevel();

    public static class Provider implements ICapabilitySerializable<CompoundTag> {

        private IDepth depth;

        public Provider(IDepth depth) {
            this.depth = depth;
        }

        public Provider() {
            this.depth = new DepthCapability();
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return cap == DepthMeterCapabilities.DEPTH ? LazyOptional.of(() -> (T) depth) : LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            return depth.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            depth.deserializeNBT(nbt);
        }

    }

}
