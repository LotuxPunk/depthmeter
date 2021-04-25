package com.vandendaelen.depthmeter.capabilities;

import com.vandendaelen.depthmeter.misc.DepthLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public class DepthCapability implements IDepth {
    private DepthLevels level;

    public DepthCapability(){

    }

    @Override
    public void tick(PlayerEntity playerEntity) {
        if (playerEntity instanceof ServerPlayerEntity){
            level = DepthLevels.from(((int) playerEntity.getPosY()));
        }
    }

    @Override
    public DepthLevels getLevel() {
        return this.level;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("depth", level.name());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        level = DepthLevels.valueOf(nbt.getString("depth"));
    }
}
