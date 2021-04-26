package com.vandendaelen.depthmeter.capabilities;

import com.vandendaelen.depthmeter.misc.DepthLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public interface IDepth  extends INBTSerializable<CompoundNBT> {
    void tick(PlayerEntity playerEntity);
    DepthLevels getDepth();
    int getPosSeaLevel();

    public static class Storage implements Capability.IStorage<IDepth>{

        @Nullable
        @Override
        public INBT writeNBT(Capability<IDepth> capability, IDepth instance, Direction side) {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<IDepth> capability, IDepth instance, Direction side, INBT nbt) {
            instance.deserializeNBT((CompoundNBT) nbt);
        }
    }

    public static class Provider implements ICapabilitySerializable<CompoundNBT> {

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
            return cap == DepthMeterCapabilities.DEPTH ? (LazyOptional<T>) LazyOptional.of(() -> (T) depth) : LazyOptional.empty();
        }

        @Override
        public CompoundNBT serializeNBT() {
            return depth.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            depth.deserializeNBT(nbt);
        }

    }

}
