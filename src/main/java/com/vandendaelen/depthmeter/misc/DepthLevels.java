package com.vandendaelen.depthmeter.misc;

import com.vandendaelen.depthmeter.config.DepthMeterConfig;

import java.util.Arrays;

public enum DepthLevels {
    VOID(Integer.MIN_VALUE, DepthMeterConfig.getLimitVoidToLava() - 1),
    LAVA(DepthMeterConfig.getLimitVoidToLava(), DepthMeterConfig.getLimitLavaToCave() - 1),
    CAVE(DepthMeterConfig.getLimitLavaToCave(), DepthMeterConfig.getLimitCaveToSurface() - 1),
    SURFACE(DepthMeterConfig.getLimitLavaToCave(),DepthMeterConfig.getLimitSurfaceToSky() - 1),
    SKY(DepthMeterConfig.getLimitSurfaceToSky(), Integer.MAX_VALUE);

    private final int minValue;
    private final int maxValue;

    private DepthLevels(int min, int max) {
        this.minValue = min;
        this.maxValue = max;
    }

    public static DepthLevels from(int y) {
        return Arrays.stream(DepthLevels.values())
                .filter(range -> y >= range.minValue && y <= range.maxValue)
                .findAny()
                .orElse(VOID);
    }
}
