package net.hydra.jojomod.item;

import net.hydra.jojomod.util.config.ConfigManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class WorthyArrowItem extends RoundaboutArrowItem {
    public WorthyArrowItem(Properties $$0) {
        super($$0);
    }

    @Override
    public void appendHoverText(ItemStack $$0, net.minecraft.world.item.Item.TooltipContext tooltipContext, List<Component> $$2, TooltipFlag $$3) {
        Level $$1 = tooltipContext.level();
        if (ConfigManager.getClientConfig().showCreativeTextOnWorthinessArrow) {
            $$2.add(
                    Component.translatable("item.roundabout.worthy_arrow.info").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC)
            );
            $$2.add(
                    Component.translatable("item.roundabout.worthy_arrow.info_2").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC)
            );
        }
    }
}
