package com.vandendaelen.depthmeter.capabilities;

import com.vandendaelen.depthmeter.misc.DepthLevels;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DepthCapability implements IDepth {
    private DepthLevels depth = DepthLevels.VOID;
    private int posSeaLevel = 0;

    public DepthCapability(){

    }

    @Override
    public void tick(Player playerEntity) {
        if (playerEntity instanceof ServerPlayer){
            ResourceLocation currentDimension = ((ServerPlayer)playerEntity).getLevel().dimension().location();
            int seaLevel = ((ServerPlayer)playerEntity).getLevel().getSeaLevel();

            if (currentDimension == Level.NETHER.location()){
                depth = DepthLevels.LAVA;
            }
            else if (currentDimension == Level.END.location()){
                depth = DepthLevels.VOID;
            }
            else{
                depth = DepthLevels.from((playerEntity.getBlockY()));
            }

            posSeaLevel = playerEntity.getBlockY() - seaLevel;
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
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("depth", (float)depth.ordinal());
        tag.putInt("pos_sea_level", posSeaLevel);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        depth = DepthLevels.values()[(int)nbt.getFloat("depth")];
        posSeaLevel = nbt.getInt("pos_sea_level");
    }
}
