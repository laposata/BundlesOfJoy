package com.dreamtea.bundles_o_j.items.bundles;

import com.dreamtea.bundles_o_j.mixins.invokers.BundleItemInvoker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

import static com.dreamtea.bundles_o_j.datagen.Tags.ENDER_POUCH_CHARGE;

public class EnderBundle extends Item {
  public static final String CHARGE_KEY = "charge";
  public static final int usePerChargeItem = 64;
  public EnderBundle(Settings settings) {
    super(settings);
  }

  @Override
  public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference){
    if (clickType != ClickType.RIGHT) {
      return false;
    }
    if(otherStack.isEmpty()){
      ItemStack output = takeStackFromEnderChest(stack, player);
      cursorStackReference.set(output);
      return true;
    } else if(otherStack.isIn(ENDER_POUCH_CHARGE)){
      int added = adjustCharge(stack, otherStack.getCount() * usePerChargeItem);
      if(added > 0){
        float used = ((float)added) / usePerChargeItem;
        int consumed = (int)Math.ceil(used);
        stack.decrement(consumed);
        return true;
      }
    }
    int added = addToEnderChest(player, otherStack, getCharge(stack));
    adjustCharge(stack, -added * BundleItemInvoker.getItemOccupancy(otherStack));
    return true;
  }

  public static int getMaxCharge(){
    return 27 * usePerChargeItem;
  }

  public static int getCharge(ItemStack stack){
    return stack.getOrCreateNbt().getInt(CHARGE_KEY);
  }

  public static int adjustCharge(ItemStack stack, int charge){
    NbtCompound nbt = stack.getOrCreateNbt();
    int chargeInit = nbt.getInt(CHARGE_KEY);
    int chargeOut = Math.min(charge + chargeInit, getMaxCharge());
    chargeOut = Math.max(chargeOut, 0);
    nbt.putInt(CHARGE_KEY, chargeOut);
    return chargeOut - chargeInit;
  }

  public static int addToEnderChest(PlayerEntity player, ItemStack otherStack, int charges){
    EnderChestInventory echest = player.getEnderChestInventory();
    ItemStack echestItem;
    int weight = BundleItemInvoker.getItemOccupancy(otherStack);
    int scaledCharges = (int)Math.ceil((double)charges/weight);
    int added = 0;
    for(int i = 0; i < echest.size() && otherStack.getCount() > 0 && scaledCharges > 0; i ++){
      echestItem = echest.getStack(i);
      if(echestItem.isEmpty()){
        int inc = Math.min(scaledCharges , otherStack.getCount());
        ItemStack placing = otherStack.copy();
        placing.setCount(inc);
        otherStack.decrement(inc);
        echest.setStack(i, placing);
        return inc;
      }else if(ItemStack.canCombine(echestItem, otherStack) && echestItem.getMaxCount() < echestItem.getCount()){
        int inc = Math.min(otherStack.getCount(), echestItem.getMaxCount() - echestItem.getCount());
        inc = Math.min(scaledCharges , inc);
        echestItem.increment(inc);
        otherStack.decrement(inc);
        added += inc;
        scaledCharges -= inc;
      }
    }
    return added;
  }

  private static ItemStack takeStackFromEnderChest(ItemStack bundle, PlayerEntity player){
    int charges = getCharge(bundle);
    if(charges == 0){
      return ItemStack.EMPTY;
    }
    EnderChestInventory echest = player.getEnderChestInventory();
    ItemStack found;
    int i, weight, scaledCharges;
    for(i = 0; i < echest.size(); i++){
      found = echest.getStack(i);
      weight = BundleItemInvoker.getItemOccupancy(found);
      scaledCharges = (int)Math.floor((double)charges/weight);
      if(scaledCharges > 0){
        int inc = Math.min(scaledCharges, found.getCount());
        ItemStack output = found.copy();
        found.decrement(inc);
        output.setCount(inc);
        adjustCharge(bundle, -inc * weight);
        return output;
      }
    }
    return  ItemStack.EMPTY;
  }
}
