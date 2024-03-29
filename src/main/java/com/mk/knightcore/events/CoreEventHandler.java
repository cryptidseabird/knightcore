package com.mk.knightcore.events;

import com.mk.knightcore.Knightcore;

import com.mk.knightcore.item.CoreBowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Knightcore.MOD_ID)
public class CoreEventHandler {

    @SubscribeEvent
    public static void handleBowFOV(ComputeFovModifierEvent event) {
        if (event.getPlayer().isUsingItem() && event.getPlayer().getMainHandItem().getItem() instanceof CoreBowItem) {

            float fovModifier = event.getPlayer().getTicksUsingItem() / 20.0F;
            if (fovModifier > 1.0F) { fovModifier = 1.0F; }
            else { fovModifier *= fovModifier; }
            event.setNewFovModifier(event.getFovModifier() * (1.0F - fovModifier * 0.20F));
        }
    }

    @SubscribeEvent
    public static void handleBowDrawEvent(LivingEntityUseItemEvent.Start event) {

        int drawMod = 0;
        ItemStack item = event.getItem();

        if(item.getItem() instanceof CoreBowItem bowItem) { drawMod = bowItem.DRAW_MOD; }
        if(drawMod != 0 && event.getDuration() > item.getUseDuration() - 20) {
            event.setDuration(event.getDuration() - drawMod);
        }
    }
}
