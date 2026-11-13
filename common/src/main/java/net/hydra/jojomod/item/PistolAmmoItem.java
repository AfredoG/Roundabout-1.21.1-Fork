package net.hydra.jojomod.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PistolAmmoItem extends AmmoItem {
    public PistolAmmoItem(Properties $$0) {
        super($$0);
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flag) {
        Level level = tooltipContext.level();
        tooltip.add(Component.translatable("text.roundabout.bullets.pistol").withStyle(ChatFormatting.YELLOW));
    }
}