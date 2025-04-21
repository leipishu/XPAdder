package top.leipishu.xpadder.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import top.leipishu.xpadder.XPAdder;

public class ModItems {
    public static final Item XP_TOOL = registerItem("xp_tool", new XPTool(new FabricItemSettings()));
    public static final Item XP_BOTTLE_PLUS = registerItem("xp_bottle_plus", new XPBottlePlus(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(XPAdder.MOD_ID, name), item);
    }

    public static void registerModItems() {
        XPAdder.LOGGER.info("Registering Mod Item for " + XPAdder.MOD_ID);
    }
}