package com.dreamtea.bundles_o_j.items.bundles;

import net.minecraft.item.ItemStack;

import java.util.function.Function;

public interface IRedstoneCompatibleBundle {
  Function<ItemStack,ItemStack> getItemWithdrawn();
  Function<ItemStack,Boolean> intakeItem(ItemStack stack);

  static ItemStack getItemWithdrawn(ItemStack bundle){
    if(bundle.getItem() instanceof IRedstoneCompatibleBundle ircb){
      return ircb.getItemWithdrawn().apply(bundle);
    }
    return ItemStack.EMPTY;
  }

  static ItemStack withdrawItem(ItemStack bundle){
    if(bundle.getItem() instanceof IRedstoneCompatibleBundle ircb){
      ItemStack output = getItemWithdrawn(bundle);
      if(BundlesHelper.removeItems(bundle, output)){
        return output;
      } else {
        throw new IllegalStateException(String.format(
          "Somehow failed to remove randomly selected it \n Selected: %s \n Bundle: %s",
          output.getNbt(), bundle.getOrCreateNbt()
        ));
      }
    }
    return ItemStack.EMPTY;
  }

  static Boolean intakeItem(ItemStack bundle, ItemStack input){
    if(bundle.getItem() instanceof IRedstoneCompatibleBundle ircb){
      return ircb.intakeItem(input).apply(bundle);
    }
    return false;
  }
}
