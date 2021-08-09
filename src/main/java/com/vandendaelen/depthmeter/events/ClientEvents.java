package com.vandendaelen.depthmeter.events;

import com.vandendaelen.depthmeter.DepthMeter;
import com.vandendaelen.depthmeter.capabilities.DepthMeterCapabilities;
import com.vandendaelen.depthmeter.capabilities.IDepth;
import com.vandendaelen.depthmeter.items.DepthMeterItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = DepthMeter.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    @SubscribeEvent
    public static void register(FMLClientSetupEvent event) {
        ItemProperties.register(DepthMeterItems.DEPTHMETER.get(), new ResourceLocation("depth"), (stack, world, entity, idontknow) -> {
            IDepth cap = stack.getCapability(DepthMeterCapabilities.DEPTH).orElse(null);
            if (cap == null){
                return 0f;
            }
            return (float) cap.getDepth().ordinal();
        });
    }
}
