package com.vandendaelen.depthmeter.capabilities;

import com.vandendaelen.depthmeter.misc.DepthLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Dimension;

public class DepthCapability implements IDepth {
    private DepthLevels depth = DepthLevels.VOID;
    private int posSeaLevel = 0;

    public DepthCapability(){

    }

    @Override
    public void tick(PlayerEntity playerEntity) {
        if (playerEntity instanceof ServerPlayerEntity){
            ResourceLocation currentDimension = ((ServerPlayerEntity)playerEntity).getServerWorld().getDimensionKey().getLocation();
            int seaLevel = ((ServerPlayerEntity)playerEntity).getServerWorld().getSeaLevel();

            if (currentDimension == Dimension.THE_NETHER.getLocation()){
                depth = DepthLevels.LAVA;
            }
            else if (currentDimension == Dimension.THE_END.getLocation()){
                depth = DepthLevels.VOID;
            }
            else{
                depth = DepthLevels.from(((int) playerEntity.getPosY()));
            }

            posSeaLevel = (int)playerEntity.getPosY() - seaLevel;
            this.serializeNBT();
        }
    }

    @Override
    public DepthLevels getDepth() {
        return this.depth;
    }

    @Override
    public int getPosSeaLevel() {
        return this.posSeaLevel;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putFloat("depth", (float)depth.ordinal());
        tag.putInt("pos_sea_level", posSeaLevel);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        depth = DepthLevels.values()[(int)nbt.getFloat("depth")];
        posSeaLevel = nbt.getInt("pos_sea_level");
    }
}
