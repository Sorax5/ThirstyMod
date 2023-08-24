package fr.soraxdubbing.thirsty.mixin;

import fr.soraxdubbing.thirsty.IMixinEntityPlayerWaterBar;
import fr.soraxdubbing.thirsty.ThirstyMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemPotion.class)
public class MixinItemPotion extends Item {
    public MixinItemPotion() {
        super();
    }

    @Inject(method = "onItemUseFinish", at = @At("HEAD"), cancellable = true)
    private void onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving, CallbackInfoReturnable<ItemStack> cir) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            IMixinEntityPlayerWaterBar playerWaterBar = (IMixinEntityPlayerWaterBar) player;

            if (!player.capabilities.isCreativeMode) {
                int newWaterLevel = Math.min(playerWaterBar.getWaterLevel() + 2, 20); // Augmentez la barre de soif de 5 (ou ajustez comme nécessaire)
                playerWaterBar.setWaterLevel(newWaterLevel);
                stack.shrink(1); // Diminue la quantité d'items dans la pile de la fiole d'eau
            }
        }
    }
}
