package com.vurbin.fancyexperience.item;

import com.vurbin.fancyexperience.FancyExperience;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup FANCYEXPERIENCE_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            new Identifier(FancyExperience.MOD_ID, "grand_experience_bottle"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.grand_experience_bottle"))
                    .icon(() -> new ItemStack(ModItems.GRAND_EXPERIENCE_BOTTLE)).entries((displayContext, entries) -> {
                        entries.add(ModItems.GRAND_EXPERIENCE_BOTTLE);
                    }).build());

    public static void registerItemGroups(){
        FancyExperience.LOGGER.info("Registering mod item groups for " + FancyExperience.MOD_ID);
    }
}
