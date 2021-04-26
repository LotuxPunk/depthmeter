package com.vandendaelen.depthmeter;

import com.vandendaelen.depthmeter.capabilities.DepthCapability;
import com.vandendaelen.depthmeter.capabilities.DepthMeterCapabilities;
import com.vandendaelen.depthmeter.capabilities.IDepth;
import com.vandendaelen.depthmeter.items.DepthMeterItems;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DepthMeter.MODID)
public class DepthMeter {

    public static final String MODID = "depthmeter";

    public DepthMeter() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        DepthMeterItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IDepth.class, new IDepth.Storage(), DepthCapability::new);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ItemModelsProperties.registerProperty(DepthMeterItems.DEPTHMETER.get(), new ResourceLocation("depth"), (stack, world, entity) -> {
            IDepth cap = stack.getCapability(DepthMeterCapabilities.DEPTH).orElse(null);
            if (cap == null){
                return 0f;
            }
            return (float) cap.getDepth().ordinal();
        });
    }
}
