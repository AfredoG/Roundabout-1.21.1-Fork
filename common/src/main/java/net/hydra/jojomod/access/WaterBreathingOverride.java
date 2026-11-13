package net.hydra.jojomod.access;

/** Dynamic replacement for the canBreatheUnderwater override made final in 1.21.
 * Applied by StandUserEntity before vanilla's entity-type tag check. */
public interface WaterBreathingOverride {
    boolean roundabout$canBreatheUnderwater();
}
