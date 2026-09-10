package com.vanilla3dplus.visual;

import com.vanilla3dplus.config.Vanilla3DPlusConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;

public final class RainEffects {

    private RainEffects() {
    }

    public static void updateRain(Minecraft client, VisualState state) {
        if (!Vanilla3DPlusConfig.weatherEffects) {
            return;
        }

        ClientLevel level = client.level;
        Player player = client.player;

        if (level == null || player == null) {
            return;
        }

        if (!level.isRaining()) {
            return;
        }

        var random = level.getRandom();

        int particlesPerTick =
                level.isThundering() ? 7 : 4;

        for (int i = 0; i < particlesPerTick; i++) {

            int radius = 8;

            int dx =
                    random.nextInt(radius * 2 + 1)
                            - radius;

            int dz =
                    random.nextInt(radius * 2 + 1)
                            - radius;

            int x = player.blockPosition().getX() + dx;
            int z = player.blockPosition().getZ() + dz;

            int topY =
                    level.getHeight(
                            net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING,
                            x,
                            z
                    );

            if (topY <= 0) {
                continue;
            }

            double px =
                    x + random.nextDouble();

            double py =
                    topY + 8.0
                            + random.nextDouble() * 5.0;

            double pz =
                    z + random.nextDouble();

            level.addParticle(
                    ParticleTypes.RAIN,
                    px,
                    py,
                    pz,
                    0.0,
                    -0.45,
                    0.0
            );
        }

        if (state.tick % 2 == 0) {

            BlockPos center =
                    player.blockPosition();

            int radius = 7;

            BlockPos splashPos =
                    center.offset(
                            random.nextInt(radius * 2 + 1) - radius,
                            0,
                            random.nextInt(radius * 2 + 1) - radius
                    );

            if (level.getBlockState(splashPos)
                    .isSolidRender()) {

                double x =
                        splashPos.getX()
                                + random.nextDouble();

                double y =
                        splashPos.getY()
                                + 1.01;

                double z =
                        splashPos.getZ()
                                + random.nextDouble();

                level.addParticle(
                        ParticleTypes.SPLASH,
                        x,
                        y,
                        z,
                        0.0,
                        0.025,
                        0.0
                );
            }
        }
    }
}
