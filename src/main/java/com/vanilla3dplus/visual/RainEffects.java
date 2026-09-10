package com.vanilla3dplus.visual;

import com.vanilla3dplus.config.Vanilla3DPlusConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;

public final class RainEffects {
    private RainEffects() {}

        public static void updateRain(Minecraft client, VisualState state) {

            if (!Vanilla3DPlusConfig.weatherEffects) {
                return;
            }

            ClientLevel level = client.level;
            Player player = client.player;

            var random = level.getRandom();

            if (!level.isRaining()
                    || state.tick % 2 != 0) {
                return;
            }

            float strength =
                    level.isThundering()
                            ? 1.0f
                            : 0.62f;

            if (random.nextFloat() > strength) {
                return;
            }

            BlockPos center =
                    player.blockPosition();

            int radius = 6;

            BlockPos pos =
                    center.offset(
                            random.nextInt(radius * 2 + 1)
                                    - radius,
                            0,
                            random.nextInt(radius * 2 + 1)
                                    - radius
                    );

            if (!level.getBlockState(pos)
                    .isSolidRender()) {
                return;
            }

            double x =
                    pos.getX()
                            + random.nextDouble();
    double y =
                    pos.getY()
                            + 1.01;

            double z =
                    pos.getZ()
                            + random.nextDouble();

            level.addParticle(
                    ParticleTypes.SPLASH,
                    x,
                    y,
                    z,
                    0,
                    0.02,
                    0
            );

            if (random.nextFloat() < 0.25f) {

                level.addParticle(
                        ParticleTypes.DRIPPING_WATER,
                        x,
                        y + 0.12,
                        z,
                        0,
                        -0.04,
                        0
                );
            }
        }
}
