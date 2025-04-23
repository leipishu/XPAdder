package top.leipishu.xpadder.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import top.leipishu.xpadder.XPAdder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CycleExperienceCommand {
    private static ScheduledExecutorService scheduler;
    private static MinecraftServer server;
    private static int amount;
    private static int duration; // 现在以tick为单位
    private static boolean isCycling = false; // 用于控制周期性任务是否在运行

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    CommandManager.literal("xp-adder")
                            .then(CommandManager.literal("cycle")
                                    .requires(source -> source.hasPermissionLevel(2)) // 确认权限等级
                                    .then(CommandManager.argument("amount", IntegerArgumentType.integer(0))
                                            .then(CommandManager.argument("duration", IntegerArgumentType.integer(1))
                                                    .executes(CycleExperienceCommand::startCycle))))
                            .then(CommandManager.literal("stop-cycle")
                                    .requires(source -> source.hasPermissionLevel(2)) // 确认权限等级
                                    .executes(CycleExperienceCommand::stopCycle))
            );
        });
    }

    private static int startCycle(CommandContext<ServerCommandSource> context) {
        if (isCycling) {
            context.getSource().sendFeedback(() -> Text.translatable("command.xp_adder.cycle.already_running"), false);
            return 0;
        }

        amount = IntegerArgumentType.getInteger(context, "amount");
        duration = IntegerArgumentType.getInteger(context, "duration");

        server = context.getSource().getServer();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        isCycling = true;

        scheduler.scheduleAtFixedRate(() -> {
            if (server != null) {
                XPAdder.experienceList.add(amount);
                server.sendMessage(Text.translatable("command.xp_adder.cycle.added", amount));
            }
        }, 0, duration, TimeUnit.MILLISECONDS);

        context.getSource().sendFeedback(() -> Text.translatable("command.xp_adder.cycle.started", amount, duration), false);
        return 1;
    }

    private static int stopCycle(CommandContext<ServerCommandSource> context) {
        if (!isCycling) {
            context.getSource().sendFeedback(() -> Text.translatable("command.xp_adder.cycle.not_running"), false);
            return 0;
        }

        if (scheduler != null) {
            scheduler.shutdownNow();
            scheduler = null;
        }
        isCycling = false;

        context.getSource().sendFeedback(() -> Text.translatable("command.xp_adder.cycle.stopped"), false);
        return 1;
    }
}