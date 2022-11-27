package com.dreamtea.bundles_o_j.color_provider;

import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;
import net.minecraft.item.DyeableItem;

import static com.dreamtea.bundles_o_j.items.ItemsRegistry.SEALED_BUNDLE;

public class BundleColorProvider {
  public static void registerColors(){
    ColorProviderRegistryImpl.ITEM.register(
      (stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem)((Object)stack.getItem())).getColor(stack),
      SEALED_BUNDLE
    );
  }
}
