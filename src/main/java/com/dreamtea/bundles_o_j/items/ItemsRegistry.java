package com.dreamtea.bundles_o_j.items;

import com.dreamtea.bundles_o_j.items.bundles.EnderBundle;
import com.dreamtea.bundles_o_j.items.bundles.FortifiedBundle;
import com.dreamtea.bundles_o_j.items.bundles.SealedBundle;
import com.dreamtea.bundles_o_j.items.bundles.SpaciousBundle;
import com.dreamtea.bundles_o_j.mixins.invokers.ItemsInvoker;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

import static com.dreamtea.bundles_o_j.BundlesOfJoy.NAMESPACE;

public class ItemsRegistry {
  public static final Identifier SEALED_BUNDLE_ID = new Identifier(NAMESPACE, "sealed_bundle");
  public static final Item SEALED_BUNDLE = ItemsInvoker.register(SEALED_BUNDLE_ID,
    new SealedBundle(new Item.Settings().maxCount(1).group(ItemsInvoker.createEmptyOptional(ItemGroup.TOOLS).orElse(null))));

  public static final Identifier FORTIFIED_BUNDLE_ID = new Identifier(NAMESPACE, "fortified_bundle");
  public static final Item FORTIFIED_BUNDLE = ItemsInvoker.register(FORTIFIED_BUNDLE_ID,
    new FortifiedBundle(new Item.Settings().maxCount(1).group(ItemsInvoker.createEmptyOptional(ItemGroup.TOOLS).orElse(null))));

  public static final Identifier SPACIOUS_BUNDLE_ID = new Identifier(NAMESPACE, "spacious_bundle");
  public static final Item SPACIOUS_BUNDLE = ItemsInvoker.register(SPACIOUS_BUNDLE_ID,
    new SpaciousBundle(new Item.Settings().maxCount(1).group(ItemsInvoker.createEmptyOptional(ItemGroup.TOOLS).orElse(null))));

  public static final Identifier ENDER_BUNDLE_ID = new Identifier(NAMESPACE, "ender_bundle");
  public static final Item ENDER_BUNDLE = ItemsInvoker.register(ENDER_BUNDLE_ID,
    new EnderBundle(new Item.Settings().maxCount(1).group(ItemsInvoker.createEmptyOptional(ItemGroup.TOOLS).orElse(null))));
}
