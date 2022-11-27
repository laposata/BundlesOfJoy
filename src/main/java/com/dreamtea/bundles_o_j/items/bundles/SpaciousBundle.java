package com.dreamtea.bundles_o_j.items.bundles;

import com.dreamtea.bundles_o_j.mixins.invokers.BundleItemInvoker;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

public class SpaciousBundle extends BundleItem implements IRedstoneCompatibleBundle, IBundle {
  public static final int MAX_STORAGE = 64 * 2;

  public SpaciousBundle(Settings settings) {
    super(settings);
  }

  @Override
  public Function<ItemStack,ItemStack> getItemWithdrawn() {
    return BundlesHelper.getRandomItem();
  }

  @Override
  public Function<ItemStack,Boolean> intakeItem(ItemStack stack) {
    return BundlesHelper.addToBundle(stack);
  }

  public static float getAmountFilled(ItemStack stack) {
    return (float) BundleItemInvoker.getBundleOccupancy(stack) / MAX_STORAGE;
  }

  @Override
  public int getMaxCapacity() {
    return MAX_STORAGE;
  }
}
