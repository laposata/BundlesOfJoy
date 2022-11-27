package com.dreamtea.bundles_o_j.mixins.invokers;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;

@Mixin(Items.class)
public interface ItemsInvoker {

  @Invoker("register")
  public static Item register(Identifier id, Item item) {
    return null;
  }
  @Invoker("createEmptyOptional")
  public static <T> Optional<T> createEmptyOptional(T of) {
    return Optional.empty();
  }
}
