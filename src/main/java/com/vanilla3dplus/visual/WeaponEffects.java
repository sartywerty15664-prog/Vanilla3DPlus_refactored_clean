package com.vanilla3dplus.visual;

import com.vanilla3dplus.config.Vanilla3DPlusConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import java.util.Locale;

public final class WeaponEffects {
    private WeaponEffects() {}

        public static void updateHeldWeaponEffects(
                Minecraft client, VisualState state
        ) {

            if (!Vanilla3DPlusConfig.weaponEffects) {
                return;
            }

            Player player = client.player;
            ClientLevel level = client.level;

            var random = level.getRandom();

            ItemStack held =
                    player.getMainHandItem();

            if (held.isEmpty()) {
                return;
            }

            String name =
                    held.getItem()
                            .toString()
                            .toLowerCase(Locale.ROOT);

            boolean mace =
                    name.contains("mace");

            boolean spear =
                    name.contains("spear");

            if (!mace && !spear) {
                return;
            }

            double speed =
                    player.getDeltaMovement()
                            .length();

            boolean falling =
                    !player.onGround()
                            && player.getDeltaMovement().y
                            < -0.08;

            /*
             * Spear movement trail.
             */
            if (spear
                    && speed > 0.10
                    && state.tick % 2 == 0) {

                level.addParticle(
                        ParticleTypes.CLOUD,

                        player.getX()
                                - player.getDeltaMovement().x
                                * 1.2,

                        player.getY()
                                + 1.0,

                        player.getZ()
                                - player.getDeltaMovement().z
                                * 1.2,

                        -player.getDeltaMovement().x
                                * 0.15,

                        0.005,

                        -player.getDeltaMovement().z
                                * 0.15
                );
            }

            /*
             * Mace falling effect.
             */
            if (mace
                    && falling
                    && player.fallDistance > 2.0
                    && state.tick % 2 == 0) {

                double strength =
                        VisualMath.clamp(
                                player.fallDistance / 12.0,
                                0.0,
                                1.0
                        );

                level.addParticle(
                        ParticleTypes.CRIT,

                        player.getX()
                                + (random.nextDouble() - 0.5)
                                * 0.4,

                        player.getY()
                                + 0.5
                                + random.nextDouble() * 0.7,

                        player.getZ()
                                + (random.nextDouble() - 0.5)
                                * 0.4,

                        (random.nextDouble() - 0.5)
                                * strength
                                * 0.12,

                        0.03
                                + strength * 0.08,

                        (random.nextDouble() - 0.5)
                                * strength
                                * 0.12
                );
            }
        }
}
