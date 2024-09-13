package com.vurbin.fancyexperience.item;

import com.vurbin.fancyexperience.FancyExperience;
import com.vurbin.fancyexperience.customItems.GrandExperienceBottleItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item GRAND_EXPERIENCE_BOTTLE = registerItem("grand_experience_bottle", new GrandExperienceBottleItem(new FabricItemSettings()));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(GRAND_EXPERIENCE_BOTTLE);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(FancyExperience.MOD_ID, name), item);
    }

    public static void registerModItems(){
        FancyExperience.LOGGER.info("Registering mod items for " + FancyExperience.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
