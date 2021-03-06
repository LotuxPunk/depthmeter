package com.vandendaelen.depthmeter.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class DepthMeterConfig {
    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    private static final int MIN_VALUE = -64;
    private static final int MAX_VALUE = 320;

    static {
        Pair<Common, ForgeConfigSpec> specClientPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specClientPair.getRight();
        COMMON = specClientPair.getLeft();
    }

    public static class Common {
        public final ForgeConfigSpec.IntValue limitVoidToLava;
        public final ForgeConfigSpec.IntValue limitLavaToCave;
        public final ForgeConfigSpec.IntValue limitCaveToSurface;
        public final ForgeConfigSpec.IntValue limitSurfaceToSky;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Limits settings");
            limitVoidToLava = builder
                    .comment("Y level for Void/Lava junction")
                    .defineInRange("limitVoidToLava", -64, MIN_VALUE, MAX_VALUE);
            limitLavaToCave = builder
                    .comment("Y level for Lava/Cave junction", "Constraint : limitLavaToCave > limitVoidToLava")
                    .defineInRange("limitLavaToCave", 0, MIN_VALUE, MAX_VALUE);
            limitCaveToSurface = builder
                    .comment("Y level for Cave/Surface junction", "Constraint : limitCaveToSurface > limitLavaToCave")
                    .defineInRange("limitCaveToSurface", 51, MIN_VALUE, MAX_VALUE);
            limitSurfaceToSky = builder
                    .comment("Y level for Surface/Sky junction", "Constraint : limitSurfaceToSky > limitCaveToSurface")
                    .defineInRange("limitSurfaceToSky", 191, MIN_VALUE, MAX_VALUE);
            builder.pop();
        }
    }

    public static int getLimitVoidToLava() {
        return COMMON.limitVoidToLava.get();
    }

    public static int getLimitLavaToCave() {
        return COMMON.limitLavaToCave.get();
    }

    public static int getLimitCaveToSurface() {
        return COMMON.limitCaveToSurface.get();
    }

    public static int getLimitSurfaceToSky() {
        return COMMON.limitSurfaceToSky.get();
    }
}
