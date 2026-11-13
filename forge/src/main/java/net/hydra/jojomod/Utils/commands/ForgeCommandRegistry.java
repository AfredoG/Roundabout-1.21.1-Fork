package net.hydra.jojomod.Utils.commands;

import net.hydra.jojomod.event.commands.RoundaboutCom;
import net.hydra.jojomod.event.commands.RoundaboutDiscCommand;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class ForgeCommandRegistry{
    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent e) {
        RoundaboutCom.register(e.getDispatcher());
        RoundaboutDiscCommand.register(e.getDispatcher());
    }
}
