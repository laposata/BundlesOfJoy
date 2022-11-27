package com.dreamtea.bundles_o_j.items.bundles;

import com.dreamtea.bundles_o_j.mixins.invokers.BundleItemInvoker;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.random.Random;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static net.minecraft.block.entity.ShulkerBoxBlockEntity.ITEMS_KEY;

public class BundlesHelper {

  public static Function<ItemStack,Boolean> addToBundle(ItemStack stack){
    return (bundle) -> BundleItemInvoker.addToBundle(bundle, stack) > 0;
  }

  public static Function<ItemStack,ItemStack> getRandomItem(){
    return (bundle) -> {
      Stream<ItemStack> contents = BundleItemInvoker.getBundledStacks(bundle);
      int size = contents.map(ItemStack::getCount).reduce(Integer::sum).orElse(0);
      if(size == 0){
        return ItemStack.EMPTY;
      }
      int i = Random.create().nextInt(size);
      contents = BundleItemInvoker.getBundledStacks(bundle);
      for(ItemStack content: contents.toList()){
        i -= content.getCount();
        if(i <= 0) {
          return content;
        }
      }
      return ItemStack.EMPTY;
    };
  }

  public static ItemStack toStackOf1(ItemStack stack){
    ItemStack copy = stack.copy();
    copy.setCount(1);
    return copy;
  }

  public static ItemStack toLargestAllowedStack(ItemStack stack){
    ItemStack copy = stack.copy();
    copy.setCount(Math.min(copy.getMaxCount(), copy.getCount()));
    return copy;
  }

  public static Function<ItemStack,ItemStack> toStackOf1(Function<ItemStack,ItemStack> stack){
    return (bundle) -> toStackOf1(stack.apply(bundle));
  }

  public static Function<ItemStack,ItemStack> toLargestAllowedStack(Function<ItemStack,ItemStack> stack){
    return (bundle) -> toLargestAllowedStack(stack.apply(bundle));
  }

  public static Optional<ItemStack> getFirstStack(ItemStack stack) {
    return BundleItemInvoker.getBundledStacks(stack).findFirst();
  }

  public static boolean removeItems(ItemStack bundle, ItemStack removing){
    NbtCompound nbtCompound = bundle.getOrCreateNbt();
    if (!nbtCompound.contains(ITEMS_KEY)) {
      return false;
    }
    NbtList nbtList = nbtCompound.getList(ITEMS_KEY, NbtElement.COMPOUND_TYPE);
    if (nbtList.isEmpty()) {
      return false;
    }
    boolean found = false;
    NbtCompound nbtCompound2;
    ItemStack itemStack;
    for(int i = 0; i < nbtList.size(); i++){
      nbtCompound2 = nbtList.getCompound(i);
      itemStack = ItemStack.fromNbt(nbtCompound2);
      if(itemStack.isOf(removing.getItem()) && itemStack.getCount() >= removing.getCount() && ItemStack.canCombine(itemStack, removing)){
        nbtList.remove(i);
        found = true;
        break;
      }
    }
    if (nbtList.isEmpty()) {
      bundle.removeSubNbt(ITEMS_KEY);
    }
    return found;
  }
}
