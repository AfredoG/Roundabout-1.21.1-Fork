package net.hydra.jojomod.registry;

import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.client.ModItemModels;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;

@EventBusSubscriber(modid = Roundabout.MOD_ID, value = Dist.CLIENT)
public final class ForgeAdditionalModels {
    private ForgeAdditionalModels() {}

    @SubscribeEvent
    public static void register(ModelEvent.RegisterAdditional event) {
        event.register(ModItemModels.HARPOON_IN_HAND);
        event.register(ModItemModels.SNUBNOSE_REVOLVER_IN_HAND);
        event.register(ModItemModels.MISTA_REVOLVER_IN_HAND);
        event.register(ModItemModels.TOMMY_GUN_IN_HAND);
        event.register(ModItemModels.COLT_REVOLVER_IN_HAND);
        event.register(ModItemModels.JACKAL_RIFLE_IN_HAND);
        event.register(ModItemModels.BERETTA_PISTOL_IN_HAND);
        event.register(ModItemModels.ANUBIS_IN_HAND);
        event.register(ModItemModels.STAND_BOW);
        event.register(ModItemModels.STAND_BEETLE_BOW);
        event.register(ModItemModels.WORTHY_BOW);
        event.register(ModItemModels.STAND_WORTHY_CROSSBOW);
        event.register(ModItemModels.STAND_ARROW_CROSSBOW);
        event.register(ModItemModels.IRON_BALL_CROSSBOW_LOADED);
        event.register(ModItemModels.IRON_BALL_CROSSBOW_PULLING_0);
        event.register(ModItemModels.IRON_BALL_CROSSBOW_PULLING_1);
        event.register(ModItemModels.IRON_BALL_CROSSBOW_PULLING_2);
        event.register(ModItemModels.STAND_BEETLE_CROSSBOW);
        event.register(ModItemModels.DREAD_BOOK);
        event.register(ModItemModels.FOG_BLOCK_ICON);
        event.register(ModItemModels.STREET_SIGN_DIO_D);
        event.register(ModItemModels.STREET_SIGN_DIO_D2);
        event.register(ModItemModels.STREET_SIGN_DIO_HELD);
        event.register(ModItemModels.STREET_SIGN_DIO_HELD_D);
        event.register(ModItemModels.STREET_SIGN_DIO_HELD_D2);
        event.register(ModItemModels.STREET_SIGN_RIGHT_D);
        event.register(ModItemModels.STREET_SIGN_RIGHT_D2);
        event.register(ModItemModels.STREET_SIGN_RIGHT_HELD);
        event.register(ModItemModels.STREET_SIGN_RIGHT_HELD_D);
        event.register(ModItemModels.STREET_SIGN_RIGHT_HELD_D2);
        event.register(ModItemModels.STREET_SIGN_STOP_D);
        event.register(ModItemModels.STREET_SIGN_STOP_D2);
        event.register(ModItemModels.STREET_SIGN_STOP_HELD);
        event.register(ModItemModels.STREET_SIGN_STOP_HELD_D);
        event.register(ModItemModels.STREET_SIGN_STOP_HELD_D2);
        event.register(ModItemModels.STREET_SIGN_YIELD_D);
        event.register(ModItemModels.STREET_SIGN_YIELD_D2);
        event.register(ModItemModels.STREET_SIGN_YIELD_HELD);
        event.register(ModItemModels.STREET_SIGN_YIELD_HELD_D);
        event.register(ModItemModels.STREET_SIGN_YIELD_HELD_D2);
        event.register(ModItemModels.STREET_SIGN_DANGER_D);
        event.register(ModItemModels.STREET_SIGN_DANGER_D2);
        event.register(ModItemModels.STREET_SIGN_DANGER_HELD);
        event.register(ModItemModels.STREET_SIGN_DANGER_HELD_D);
        event.register(ModItemModels.STREET_SIGN_DANGER_HELD_D2);
        event.register(ModItemModels.ZOMBIE_HORSE_THREAD_BROKEN);
        event.register(ModItemModels.ZOMBIE_HORSE_THREAD);
    }
}
