package fr.soraxdubbing.thirsty.mixin;

import fr.soraxdubbing.thirsty.IMixinEntityPlayerWaterBar;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayerWaterBar extends EntityLivingBase implements IMixinEntityPlayerWaterBar {

    @Shadow public abstract FoodStats getFoodStats();
    @Shadow protected abstract int getExperiencePoints(EntityPlayer player);
    @Shadow protected abstract boolean isPlayer();
    @Shadow public abstract boolean isSpectator();

    @Shadow public abstract boolean isCreative();

    private int waterLevel = 20;
    private int waterTimer = 0;

    protected abstract void onDeathUpdate();

    public MixinEntityPlayerWaterBar(World worldIn) {
        super(worldIn);
    }

    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void onPlayerUpdate(CallbackInfo info) {
        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            // Le joueur ne perd pas d'eau en mode paisible
            waterLevel = 20;
        } else {
            // Gérez la perte d'eau ici
            if (!this.isCreative() && !this.isSpectator()) {
                if (waterTimer <= 0) {
                    if (waterLevel > 0) {
                        waterLevel--;
                    } else {
                        this.attackEntityFrom(DamageSource.STARVE, 1.0F);
                    }
                    waterTimer = 300; // Temps avant la prochaine perte d'eau (en ticks)
                } else {
                    waterTimer--;
                }
            }
        }
    }

    // Ajoutez des méthodes pour modifier et obtenir le niveau d'eau
    public void setWaterLevel(int level) {
        this.waterLevel = Math.min(level, 20);
    }

    public int getWaterLevel() {
        return waterLevel;
    }
}
