package com.vanilla3dplus.visual;

import com.vanilla3dplus.config.Vanilla3DPlusConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public final class HudVisualRenderer {
    private HudVisualRenderer() {}

    public static void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {

        Minecraft client = Minecraft.getInstance();

        if (client.player == null || client.level == null) {
            return;
        }

        int width = client.getWindow().getGuiScaledWidth();
        int height = client.getWindow().getGuiScaledHeight();

        Player player = client.player;
        ClientLevel level = client.level;

        /*
         * WATER EFFECT
         */
        if (Vanilla3DPlusConfig.waterEffects
                && player.isUnderWater()) {

            float time =
                    (client.level.getGameTime()
                            + deltaTracker.getGameTimeDeltaPartialTick(true))
                            * 0.045f;

            float wave =
                    (Mth.sin(time) + 1.0f) * 0.5f;

            int alpha =
                    42
                            + (int) (wave * 18.0f);

            int waterColor =
                    (alpha << 24)
                            | (35 << 16)
                            | (105 << 8)
                            | 170;

            /*
             * Main underwater tint.
             */
            graphics.fill(
                    0,
                    0,
                    width,
                    height,
                    waterColor
            );

            /*
             * Darker lower edge.
             */
            int bottomAlpha =
                    28
                            + (int) (wave * 12.0f);

            int bottomColor =
                    (bottomAlpha << 24)
                            | (8 << 16)
                            | (32 << 8)
                            | 65;

            graphics.fillGradient(
                    0,
                    height / 2,
                    width,
                    height,
                    0x00000000,
                    bottomColor
            );

            /*
             * Darker top edge.
             */
            graphics.fillGradient(
                    0,
                    0,
                    width,
                    height / 3,
                    0x35000A18,
                    0x00000000
            );
        }

        /*
         * WEATHER / DYNAMIC ATMOSPHERE
         */
        if (Vanilla3DPlusConfig.dynamicFog
                && !player.isUnderWater()) {

            float atmosphere = 0.0f;

            if (level.isRaining()) {
                atmosphere +=
                        level.isThundering()
                                ? 0.16f
                                : 0.08f;
            }

            /*
             * Night atmosphere.
             */
            long dayTime =
                    level.getGameTime() % 24000L;

            if (dayTime >= 13000L
                    && dayTime <= 23000L) {
                atmosphere += 0.055f;
            }

            /*
             * Biome/weather atmospheric overlay.
             */
            if (atmosphere > 0.0f) {

                int alpha =
                        (int) Mth.clamp(
                                atmosphere * 255.0f,
                                0.0f,
                                70.0f
                        );

                int fogColor;

                if (level.isThundering()) {
                    fogColor =
                            (alpha << 24)
                                    | (35 << 16)
                                    | (40 << 8)
                                    | 55;
                } else if (level.isRaining()) {
                    fogColor =
                            (alpha << 24)
                                    | (55 << 16)
                                    | (70 << 8)
                                    | 85;
                } else {
                    fogColor =
                            (alpha << 24)
                                    | (20 << 16)
                                    | (25 << 8)
                                    | 40;
                }

                graphics.fill(
                        0,
                        0,
                        width,
                        height,
                        fogColor
                );
            }
        }

        /*
         * RAINBOW
         *
         * Only after rain.
         */
        if (Vanilla3DPlusConfig.rainbow
                && !level.isRaining()
                && true) {

            float time =
                    (System.currentTimeMillis() % 5000L)
                            / 5000.0f;
    float pulse =
                    (Mth.sin(time * Mth.TWO_PI) + 1.0f)
                            * 0.5f;

            int alpha =
                    8
                            + (int) (pulse * 8.0f);

            /*
             * Very subtle rainbow atmospheric layer.
             */
            graphics.fillGradient(
                    0,
                    0,
                    width,
                    height / 3,
                    (alpha << 24)
                            | (70 << 16)
                            | (30 << 8)
                            | 90,

                    0x00000000
            );
        }

}

}
