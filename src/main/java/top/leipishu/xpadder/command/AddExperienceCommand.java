package top.leipishu.xpadder.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import top.leipishu.xpadder.XPAdder;

public class AddExperienceCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    CommandManager.literal("xp-adder")
                            .then(CommandManager.literal("add")
                                    .requires(source -> source.hasPermissionLevel(2)) // 确认权限等级
                                    .then(CommandManager.argument("amount", IntegerArgumentType.integer(0))
                                            .executes(AddExperienceCommand::addExperienceToGlobalStorage))
                            )
            );
        });
    }

    private static int addExperienceToGlobalStorage(CommandContext<ServerCommandSource> context) {
        int amount = IntegerArgumentType.getInteger(context, "amount");
        XPAdder.experienceList.add(amount); // 将每次添加的值记录到列表中
        context.getSource().sendFeedback(() -> Text.translatable("command.xp_adder.add.success", amount), false);
        return 1;
    }
}