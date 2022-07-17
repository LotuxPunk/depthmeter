package com.vandendaelen.depthmeter.items;

import com.vandendaelen.depthmeter.capabilities.DepthMeterCapabilities;
import com.vandendaelen.depthmeter.capabilities.IDepth;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class DepthMeterItem extends Item {
    private static final String DEPTH = "depth";

    public DepthMeterItem() {
        super(new Properties().stacksTo(1).tab(CreativeModeTab.TAB_TOOLS));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);

        if (!worldIn.isClientSide()){
            if (entityIn instanceof ServerPlayer){
                stack.getCapability(DepthMeterCapabilities.DEPTH).ifPresent(cap -> cap.tick((Player) entityIn));
            }
            if (worldIn.getGameTime() % 20 == 0) {
                syncCapability(stack);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        stack.getCapability(DepthMeterCapabilities.DEPTH).ifPresent(cap -> {
            tooltip.add(getDepthInformation(cap).withStyle(ChatFormatting.GRAY));
        });
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        playerIn.getItemInHand(handIn).getCapability(DepthMeterCapabilities.DEPTH).ifPresent(cap -> {
            playerIn.displayClientMessage(getDepthInformation(cap), true);
        });
        return super.use(worldIn, playerIn, handIn);
    }

    public MutableComponent getDepthInformation(IDepth cap) {
        int posSeaLevel = cap.getPosSeaLevel();
        if (posSeaLevel >= 0) {
            return Component.translatable("depthmeter.sea_level_above", String.valueOf(Math.abs(posSeaLevel)));
        }
        return Component.translatable("depthmeter.sea_level_below", String.valueOf(Math.abs(posSeaLevel)));
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
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        stack.getCapability(DepthMeterCapabilities.DEPTH).ifPresent(handler -> tag.put("cap_sync", handler.serializeNBT()));
        return tag;

    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
        if (nbt != null) {
            if (nbt.contains("cap_sync")) {
                stack.getCapability(DepthMeterCapabilities.DEPTH).ifPresent(handler -> handler.deserializeNBT((CompoundTag) nbt.get("cap_sync")));
            }
        }
    }

}
