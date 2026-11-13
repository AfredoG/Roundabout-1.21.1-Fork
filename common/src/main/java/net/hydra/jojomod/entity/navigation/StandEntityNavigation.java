package net.hydra.jojomod.entity.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;

public class StandEntityNavigation extends WallClimberNavigation {

    private boolean avoidFire = true;

    public void setAvoidFire(boolean value) {
        this.avoidFire = value;
    }

    private boolean shouldAvoidFire(PathType blockPathTypes) {
        return blockPathTypes != PathType.DANGER_FIRE && this.avoidFire;
    }

    public StandEntityNavigation(Mob $$0, Level $$1) {
        super($$0, $$1);
    }

    public boolean canCutCorner(PathType blockPathTypes) {
        return this.shouldAvoidFire(blockPathTypes) && blockPathTypes != PathType.DANGER_OTHER && blockPathTypes != PathType.WALKABLE_DOOR;
    }

}
