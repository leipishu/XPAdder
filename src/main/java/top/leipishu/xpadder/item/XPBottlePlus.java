package top.leipishu.xpadder.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.item.Items;

public class XPBottlePlus extends Item {
    public XPBottlePlus(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            // 直接添加 1000 点经验值
            player.addExperience(1000);
            player.sendMessage(Text.translatable("command.xp_adder.xpbottleplus.success", 1000), true);

            // 播放经验音效
            world.playSound(null, BlockPos.ofFloored(player.getPos()), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);

            // 消耗经验瓶并返还一个空的玻璃瓶
            ItemStack stack = player.getStackInHand(hand);
            if (!player.isCreative()) {
                stack.decrement(1); // 减少物品数量
                if (!player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE))) {
                    player.dropItem(new ItemStack(Items.GLASS_BOTTLE), false); // 如果背包满了，掉落玻璃瓶
                }
            }

            return new TypedActionResult<>(ActionResult.SUCCESS, stack);
        }
        return new TypedActionResult<>(ActionResult.PASS, player.getStackInHand(hand));
    }
}