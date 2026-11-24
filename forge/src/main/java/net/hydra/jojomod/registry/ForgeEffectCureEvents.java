package net.hydra.jojomod.registry;

import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.util.MainUtil;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.EffectCures;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

/** Milk must not clear stand effects; expiry and other removal reasons remain native. */
@EventBusSubscriber(modid = Roundabout.MOD_ID)
public final class ForgeEffectCureEvents {
    private ForgeEffectCureEvents() {}

    @SubscribeEvent
    public static void preserveStandEffects(MobEffectEvent.Remove event) {
        if (event.getCure() == EffectCures.MILK && event.getEffectInstance() != null
                && MainUtil.isSpecialEffect(event.getEffectInstance())) {
            event.setCanceled(true);
        }
    }
}
