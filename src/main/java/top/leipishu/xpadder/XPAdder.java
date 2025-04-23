package top.leipishu.xpadder;

import net.fabricmc.api.ModInitializer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // 确保导入 LoggerFactory

import top.leipishu.xpadder.item.ModItems;
import top.leipishu.xpadder.item.ModItemGroups;
import top.leipishu.xpadder.command.AddExperienceCommand;
import top.leipishu.xpadder.command.CycleExperienceCommand;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.List;


public class XPAdder implements ModInitializer {
    public static final String MOD_ID = "xp_adder";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static List<Integer> experienceList = new ArrayList<>(); // 用于记录每次添加的经验值
    public static AtomicInteger globalExperience = new AtomicInteger(0); // 全局经验存储

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
        ModItemGroups.registerItemGroups();
        AddExperienceCommand.register(); // 注册自定义指令
        CycleExperienceCommand.register();
    }
}