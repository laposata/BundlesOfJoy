package com.dreamtea.bundles_o_j.items.bundles;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

import javax.annotation.Nullable;
import java.util.List;

import static com.dreamtea.bundles_o_j.items.ItemsRegistry.SEALED_BUNDLE;

public class SealedBundle extends Item implements DyeableItem {

  public static final String BUNDLE_KEY = "bundle";
  public static final String SEALED_BY = "sealed";


  public SealedBundle(Settings settings) {
    super(settings);
  }

  @Override
  public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
    if (clickType != ClickType.RIGHT) {
      return false;
    }
    slot.setStack(getBundle(stack));
    return true;
  }

  public static ItemStack getBundle(ItemStack stack){
    NbtCompound nbt = stack.getOrCreateNbt();
    if(nbt.contains(BUNDLE_KEY)){
      return ItemStack.fromNbt(nbt.getCompound(BUNDLE_KEY));
    }
    return ItemStack.EMPTY;
  }

  public static ItemStack createSealedBundle(ItemStack bundle, List<DyeItem> colors, @Nullable PlayerEntity player){
    ItemStack result = new ItemStack(SEALED_BUNDLE);
    NbtCompound nbt = result.getOrCreateNbt();
    nbt.put(BUNDLE_KEY, bundle.getNbt());
    DyeableItem.blendAndSetColor(result, colors);
    if (player != null) {
      nbt.putString(SEALED_BY, player.getDisplayName().getString());
    }
    return result;
  }

  public static ItemStack createSealedBundle(ItemStack sealedBundle, List<DyeItem> colors){
    return DyeableItem.blendAndSetColor(sealedBundle, colors);
  }

}
