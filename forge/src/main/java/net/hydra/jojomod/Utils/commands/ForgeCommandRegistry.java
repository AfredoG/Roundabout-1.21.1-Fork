package net.hydra.jojomod.Utils.commands;

import net.hydra.jojomod.event.commands.RoundaboutCom;
import net.hydra.jojomod.event.commands.RoundaboutDiscCommand;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.hydra.jojomod.Roundabout;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Roundabout.MOD_ID)
public class ForgeCommandRegistry{
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent e) {
        RoundaboutCom.register(e.getDispatcher());
        RoundaboutDiscCommand.register(e.getDispatcher());
    }
}
