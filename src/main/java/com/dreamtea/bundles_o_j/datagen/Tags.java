package com.dreamtea.bundles_o_j.datagen;

import com.dreamtea.bundles_o_j.utils.TagUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;

import static com.dreamtea.bundles_o_j.BundlesOfJoy.NAMESPACE;

public class Tags extends FabricTagProvider.ItemTagProvider {
  public static final TagKey<Item> ENDER_POUCH_CHARGE = TagUtils.createItemTag(new Identifier(NAMESPACE, "ender_pouch_charge"));

  public Tags(FabricDataGenerator dataGenerator) {
    super(dataGenerator);
  }

  @Override
  protected void generateTags() {
    getOrCreateTagBuilder(ENDER_POUCH_CHARGE).add(Items.ENDER_EYE);
  }
}
