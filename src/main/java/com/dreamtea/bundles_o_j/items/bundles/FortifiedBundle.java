package com.dreamtea.bundles_o_j.items.bundles;

import com.dreamtea.bundles_o_j.mixins.invokers.BundleItemInvoker;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class FortifiedBundle extends BundleItem implements IRedstoneCompatibleBundle, IBundle{
  public static final int MAX_STORAGE = 64 * 4;
  public FortifiedBundle(Settings settings) {
    super(settings);
  }

  @Override
  public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
    if (clickType != ClickType.RIGHT) {
      return false;
    }
    ItemStack itemStack = slot.getStack();
    if (itemStack.isEmpty()) {
      BundlesHelper.getFirstStack(stack).ifPresent(i -> {
        ItemStack output =  BundlesHelper.toLargestAllowedStack(i);
        BundlesHelper.removeItems(stack, output);
        ((BundleItemInvoker)this).playRemoveOneSound(player);
        slot.insertStack(output);
      });
    } else if (itemStack.getItem().canBeNested() && matchesContent(stack, itemStack)) {
      int i = (getMaxCapacity() - BundleItemInvoker.getBundleOccupancy(stack)) / BundleItemInvoker.getItemOccupancy(itemStack);
      int j = BundleItemInvoker.addToBundle(stack, slot.takeStackRange(itemStack.getCount(), i, player));
      if (j > 0) {
        ((BundleItemInvoker)this).playInsertSound(player);
      }
    }
    return true;
  }

  @Override
  public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
    if (clickType != ClickType.RIGHT || !slot.canTakePartial(player)) {
      return false;
    }
    if (otherStack.isEmpty()) {
      BundlesHelper.getFirstStack(stack).ifPresent(itemStack -> {
        ItemStack output =  BundlesHelper.toLargestAllowedStack(itemStack);
        BundlesHelper.removeItems(stack, output);
        ((BundleItemInvoker)this).playRemoveOneSound(player);
        cursorStackReference.set(output);
      });
    } else if(matchesContent(stack, otherStack)){
      int i = BundleItemInvoker.addToBundle(stack, otherStack);
      if (i > 0) {
        ((BundleItemInvoker)this).playInsertSound(player);
        otherStack.decrement(i);
      }
    }
    return true;
  }

  @Override
  public Function<ItemStack,ItemStack> getItemWithdrawn() {
    return (bundle) -> BundlesHelper.getFirstStack(bundle).map(BundlesHelper::toStackOf1).orElse(ItemStack.EMPTY);
  }

  @Override
  public Function<ItemStack,Boolean> intakeItem(ItemStack stack) {
    return BundlesHelper.addToBundle(stack);
  }

  public static boolean matchesContent(ItemStack bundle, ItemStack adding) {
    ItemStack bundleContents = BundlesHelper.getFirstStack(bundle).orElse(null);
    if(bundleContents == null) return true;
    if(!bundleContents.isOf(adding.getItem())) return false;

    if(adding.getItem() instanceof PotionItem || adding.getItem() instanceof SuspiciousStewItem){
      return potionMatchesContent(bundleContents, adding);
    }
    if(bundleContents.getEnchantments().size() != 0){
      return enchantMatchesContent(bundleContents, adding);
    }
    return true;
  }
  private static boolean potionMatchesContent(ItemStack stack, ItemStack adding) {
    if(!(stack.getItem() instanceof PotionItem || stack.getItem() instanceof SuspiciousStewItem)) return false;
    List<StatusEffectInstance> bundle = PotionUtil.getPotion(stack).getEffects();
    List<StatusEffectInstance> add = PotionUtil.getPotion(adding).getEffects();
    if(bundle.size() != add.size()) return false;
    for(StatusEffectInstance eff: bundle){
      if(!add.contains(eff)){
        return false;
      }
    }
    return true;
  }

  private static boolean enchantMatchesContent(ItemStack stack, ItemStack adding) {
    Set<Enchantment> bundle = EnchantmentHelper.get(stack).keySet();
    Set<Enchantment> add = EnchantmentHelper.get(adding).keySet();
    if(bundle.size() != add.size()) return false;
    for(Enchantment eff: bundle){
      if(!add.contains(eff)){
        return false;
      }
    }
    return true;
  }

  public static float getAmountFilled(ItemStack stack) {
    return (float) BundleItemInvoker.getBundleOccupancy(stack) / MAX_STORAGE;
  }

  @Override
  public int getMaxCapacity() {
    return MAX_STORAGE;
  }
}
