package com.vandendaelen.depthmeter.items;

import com.vandendaelen.depthmeter.capabilities.DepthMeterCapabilities;
import com.vandendaelen.depthmeter.misc.DepthLevels;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DepthMeterItem extends Item {
    public DepthMeterItem() {
        super(new Properties().maxStackSize(1).group(DepthMeterItemGroups.MAIN));
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isRemote()){
            if (entityIn instanceof PlayerEntity){
                stack.getCapability(DepthMeterCapabilities.DEPTH).ifPresent(cap -> cap.tick((PlayerEntity) entityIn));
            }
        }
    }
}
