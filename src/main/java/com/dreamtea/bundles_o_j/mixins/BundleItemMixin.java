package com.dreamtea.bundles_o_j.mixins;

import com.dreamtea.bundles_o_j.items.bundles.BundlesHelper;
import com.dreamtea.bundles_o_j.items.bundles.IBundle;
import com.dreamtea.bundles_o_j.items.bundles.IRedstoneCompatibleBundle;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(BundleItem.class)
public abstract class BundleItemMixin extends Item implements IBundle, IRedstoneCompatibleBundle {
  @Shadow @Final public static int MAX_STORAGE;

  public BundleItemMixin(Settings settings) {
    super(settings);
  }

  @Override
  public Function<ItemStack, ItemStack> getItemWithdrawn() {
    return BundlesHelper.toStackOf1(BundlesHelper.getRandomItem());
  }

  @Override
  public Function<ItemStack, Boolean> intakeItem(ItemStack stack) {
    return BundlesHelper.addToBundle(stack);
  }

  public int getMaxStorage(){
    return 64;
  }

  @ModifyConstant(method="*", constant = {@Constant(intValue = 64)})
  public int sizeProper(int original){
    return getMaxStorage();
  }

  @Inject(method = "getItemOccupancy", at = @At("TAIL"), cancellable = true)
  private static void mapBundleOccupancy(ItemStack stack, CallbackInfoReturnable<Integer> cir){
    if(stack.isOf(Items.FILLED_MAP)){
      cir.setReturnValue(64);
    }
  }
}
