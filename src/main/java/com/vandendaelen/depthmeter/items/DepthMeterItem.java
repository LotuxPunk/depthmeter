package com.vandendaelen.depthmeter.items;

import com.vandendaelen.depthmeter.capabilities.DepthMeterCapabilities;
import com.vandendaelen.depthmeter.misc.DepthLevels;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class DepthMeterItem extends Item {
    private static final String DEPTH = "depth";

    public DepthMeterItem() {
        super(new Properties().maxStackSize(1).group(ItemGroup.TOOLS));
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isRemote()){
            if (entityIn instanceof ServerPlayerEntity){
                stack.getCapability(DepthMeterCapabilities.DEPTH).ifPresent(cap -> cap.tick((PlayerEntity) entityIn));
            }
            if (worldIn.getGameTime() % 20 == 0) {
                syncCapability(stack);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        stack.getCapability(DepthMeterCapabilities.DEPTH).ifPresent(cap -> {

            int posSeaLevel = cap.getPosSeaLevel();
            if (posSeaLevel >= 0){
                tooltip.add(new TranslationTextComponent("depthmeter.sea_level_above", String.valueOf(Math.abs(posSeaLevel))));
            }
            else{
                tooltip.add(new TranslationTextComponent("depthmeter.sea_level_below", String.valueOf(Math.abs(posSeaLevel))));
            }
        });
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    public static void syncCapability(ItemStack stack) {
        if (stack.getShareTag() != null) {
            stack.getOrCreateTag().merge(stack.getShareTag());
        }
    }

    public static void readCapability(ItemStack stack) {
        if (stack.getShareTag() != null) {
            stack.readShareTag(stack.getOrCreateTag());
        }
    }

    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateTag();
        stack.getCapability(DepthMeterCapabilities.DEPTH).ifPresent(handler -> tag.put("cap_sync", handler.serializeNBT()));
        return tag;

    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        super.readShareTag(stack, nbt);
        if (nbt != null) {
            if (nbt.contains("cap_sync")) {
                stack.getCapability(DepthMeterCapabilities.DEPTH).ifPresent(handler -> handler.deserializeNBT(nbt.getCompound("cap_sync")));
            }
        }
    }

}
