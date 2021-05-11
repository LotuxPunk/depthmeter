package com.vandendaelen.depthmeter;

import com.vandendaelen.depthmeter.capabilities.DepthCapability;
import com.vandendaelen.depthmeter.capabilities.DepthMeterCapabilities;
import com.vandendaelen.depthmeter.capabilities.IDepth;
import com.vandendaelen.depthmeter.config.DepthMeterConfig;
import com.vandendaelen.depthmeter.data.RecipeBuilder;
import com.vandendaelen.depthmeter.items.DepthMeterItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DepthMeter.MODID)
public class DepthMeter {

    public static final String MODID = "depthmeter";

    public DepthMeter() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onGatherData);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DepthMeterConfig.COMMON_SPEC);

        DepthMeterItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IDepth.class, new IDepth.Storage(), DepthCapability::new);
    }

    private void onGatherData(GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        generator.addProvider(new RecipeBuilder(generator));
    }
}
