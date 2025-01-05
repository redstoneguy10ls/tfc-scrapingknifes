package com.redstoneguy10ls.tfcscraping;

import net.dries007.tfc.common.blockentities.ScrapingBlockEntity;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ScrapingHelper {
    private static final Vec3 PLANE_NORMAL = new Vec3(0.0, 1.0, 0.0);
    public static void SdoParticles(Level level, BlockPos pos, ScrapingBlockEntity scraping, Vec3 point)
    {
        if (level instanceof ServerLevel server)
        {
            scraping.getCapability(Capabilities.ITEM).ifPresent(cap -> {
                server.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, cap.getStackInSlot(0)), pos.getX() + point.x, pos.getY() + 0.0625, pos.getZ() + point.z, 2, Helpers.triangle(level.random) / 2.0D, level.random.nextDouble() / 4.0D, Helpers.triangle(level.random) / 2.0D, 0.15f);
            });
        }
    }
    public static Vec3 ScalculatePoint(Vec3 rayVector, Vec3 rayPoint)
    {
        return rayPoint.subtract(rayVector.scale(rayPoint.dot(PLANE_NORMAL) / rayVector.dot(PLANE_NORMAL)));
    }

    public static int calculateQuadrant(double x, double z)
    {
        if(x < 0.5 && z < 0.5)
        {
            return 1;
        }
        if(x >= 0.5 && z < 0.5)
        {
            return 2;
        }
        if(z >= 0.5 && x < 0.5)
        {
            return 3;
        }
        return 4;
    }
}
