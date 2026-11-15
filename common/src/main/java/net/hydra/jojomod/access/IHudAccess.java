package net.hydra.jojomod.access;

public interface  IHudAccess {
    /**If you ever want to call the Hud Mixin directly, you can use this interface
     * as an access point.*/

    public void roundabout$setFlashAlpha(float flashAlpha);

    public void roundabout$setOtherFlashAlpha(float otherFlashAlpha);

    /** A replacement bar owns the level/icon area for the current HUD frame. */
    void roundabout$hideExperienceLevel();
}
