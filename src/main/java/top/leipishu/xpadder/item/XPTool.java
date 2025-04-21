package top.leipishu.xpadder.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ItemUsage;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import top.leipishu.xpadder.XPAdder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class XPTool extends Item {
    private static final Map<UUID, Integer> playerLastExtractedIndex = new HashMap<>();

    public XPTool(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            UUID playerUUID = player.getUuid();
            int lastIndex = playerLastExtractedIndex.getOrDefault(playerUUID, -1);
            int totalExperience = 0;

            // 累加所有未领取的新增经验值
            for (int i = lastIndex + 1; i < XPAdder.experienceList.size(); i++) {
                totalExperience += XPAdder.experienceList.get(i);
            }

            if (totalExperience > 0) {
                player.addExperience(totalExperience);
                player.sendMessage(Text.translatable("command.xp_adder.extract.success", totalExperience), true);
                playerLastExtractedIndex.put(playerUUID, XPAdder.experienceList.size() - 1); // 更新玩家的提取记录

                // 播放经验音效
                world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
                return TypedActionResult.success(player.getStackInHand(hand));
            } else {
                player.sendMessage(Text.translatable("command.xp_adder.extract.failure"), true);

                // 播放拉杆音效
                world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.PLAYERS, 1.0F, 1.0F);
                return TypedActionResult.fail(player.getStackInHand(hand));
            }
        }
        return TypedActionResult.pass(player.getStackInHand(hand));
    }
}
