package top.leipishu.xpadder.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import top.leipishu.xpadder.XPAdder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup XP_TOOLS = Registry.register(Registries.ITEM_GROUP,
            new Identifier(XPAdder.MOD_ID, "xp_kit"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup." + XPAdder.MOD_ID + ".xp_kit"))
                    .icon(() -> new ItemStack(ModItems.XP_TOOL))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.XP_TOOL);
                        entries.add(ModItems.XP_BOTTLE_PLUS);
                    })
                    .build());

    public static void registerItemGroups() {
        XPAdder.LOGGER.info("Registering Item Group for " + XPAdder.MOD_ID);
    }
}