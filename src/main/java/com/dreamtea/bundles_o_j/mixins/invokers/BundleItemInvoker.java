package com.dreamtea.bundles_o_j.mixins.invokers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;
import java.util.stream.Stream;

@Mixin(BundleItem.class)
public interface BundleItemInvoker {

  @Invoker("getBundledStacks")
  public static Stream<ItemStack> getBundledStacks(ItemStack stack) {
    throw new AssertionError();
  }

  @Invoker("dropAllBundledItems")
  public static boolean dropAllBundledItems(ItemStack stack, PlayerEntity player) {
    throw new AssertionError();
  }

  @Invoker("canMergeStack")
  public static Optional<NbtCompound> canMergeStack(ItemStack stack, NbtList items) {
    throw new AssertionError();
  }

  @Invoker("addToBundle")
  public static int addToBundle(ItemStack bundle, ItemStack items) {
    throw new AssertionError();
  }
  @Invoker("removeFirstStack")
  public static Optional<ItemStack> removeFirstStack(ItemStack stack) {
    throw new AssertionError();
  }

  @Invoker("getBundleOccupancy")
  public static int getBundleOccupancy(ItemStack stack) {
    throw new AssertionError();
  }

  @Invoker("playRemoveOneSound")
  public void playRemoveOneSound(Entity entity);

  @Invoker("playInsertSound")
  public void playInsertSound(Entity entity);

  @Invoker("playDropContentsSound")
  public void playDropContentsSound(Entity entity);

  @Invoker("getItemOccupancy")
  public static int getItemOccupancy(ItemStack stack) {
    throw new AssertionError();
  }
}
