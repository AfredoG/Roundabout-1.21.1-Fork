package net.hydra.jojomod.entity.goals;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.hydra.jojomod.entity.visages.JojoNPC;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.MoveToSkySeeingSpot;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;
import java.util.List;

public class JojoNPCCelebrateVillagersSurvivedRaid extends Behavior<JojoNPC> {
    @Nullable
    private Raid currentRaid;

    public JojoNPCCelebrateVillagersSurvivedRaid(int $$0, int $$1) {
        super(ImmutableMap.of(), $$0, $$1);
    }

    protected boolean checkExtraStartConditions(ServerLevel $$0, JojoNPC $$1) {
        BlockPos $$2 = $$1.blockPosition();
        this.currentRaid = $$0.getRaidAt($$2);
        return this.currentRaid != null && this.currentRaid.isVictory() && MoveToSkySeeingSpot.hasNoBlocksAbove($$0, $$1, $$2);
    }

    protected boolean canStillUse(ServerLevel $$0, JojoNPC $$1, long $$2) {
        return this.currentRaid != null && !this.currentRaid.isStopped();
    }

    protected void stop(ServerLevel $$0, JojoNPC $$1, long $$2) {
        this.currentRaid = null;
        $$1.getBrain().updateActivityFromSchedule($$0.getDayTime(), $$0.getGameTime());
    }

    protected void tick(ServerLevel $$0, JojoNPC $$1, long $$2) {
        RandomSource $$3 = $$1.getRandom();
        if ($$3.nextInt(100) == 0) {
            $$1.playCelebrateSound();
        }

        if ($$3.nextInt(200) == 0 && MoveToSkySeeingSpot.hasNoBlocksAbove($$0, $$1, $$1.blockPosition())) {
            DyeColor $$4 = Util.getRandom(DyeColor.values(), $$3);
            int $$5 = $$3.nextInt(3);
            ItemStack $$6 = this.getFirework($$4, $$5);
            FireworkRocketEntity $$7 = new FireworkRocketEntity($$1.level(), $$1, $$1.getX(), $$1.getEyeY(), $$1.getZ(), $$6);
            $$1.level().addFreshEntity($$7);
        }
    }

    private ItemStack getFirework(DyeColor $$0, int $$1) {
        ItemStack $$2 = new ItemStack(Items.FIREWORK_ROCKET, 1);
        var explosion = new net.minecraft.world.item.component.FireworkExplosion(
                net.minecraft.world.item.component.FireworkExplosion.Shape.BURST,
                it.unimi.dsi.fastutil.ints.IntList.of($$0.getFireworkColor()),
                it.unimi.dsi.fastutil.ints.IntList.of(), false, false);
        $$2.set(net.minecraft.core.component.DataComponents.FIREWORKS,
                new net.minecraft.world.item.component.Fireworks($$1, java.util.List.of(explosion)));
        return $$2;
    }
}
