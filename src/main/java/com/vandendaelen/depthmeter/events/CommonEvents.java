package com.vandendaelen.depthmeter.events;

import com.vandendaelen.depthmeter.DepthMeter;
import com.vandendaelen.depthmeter.capabilities.DepthCapability;
import com.vandendaelen.depthmeter.capabilities.IDepth;
import com.vandendaelen.depthmeter.items.DepthMeterItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DepthMeter.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {
    public static final ResourceLocation DEPTH_CAP = new ResourceLocation(DepthMeter.MODID, "depth");

    @SubscribeEvent
    public static void attachItemStackCap(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() == DepthMeterItems.DEPTHMETER.get())
            event.addCapability(DEPTH_CAP, new IDepth.Provider(new DepthCapability()));
    }


}
