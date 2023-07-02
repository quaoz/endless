package com.github.quaoz.endless;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class Endless implements ModInitializer {
	public static final String MODID = "endless";
	private static Endless INSTANCE;
	public final Logger logger = LogManager.getLogger(MODID);
	public final EndlessConfig config = new EndlessConfig(this);

	public static Endless get() {
		return INSTANCE;
	}

	@Override
	public void onInitialize() {
		INSTANCE = this;
		this.config.load();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal(MODID)
				.requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(3))
				.then(literal("set")
						.then(argument("option", StringArgumentType.string()).suggests((context, builder) -> {
									builder.suggest("verbose", () -> "Whether to log when a player attempts to use a portal")
											.suggest("ender_eye_activation", () -> "Whether ender eyes can be used to activate portals");

									return builder.buildFuture();
								}).then(argument("value", BoolArgumentType.bool()).executes(context -> {
											String option = StringArgumentType.getString(context, "option");
											boolean value = BoolArgumentType.getBool(context, "value");

											if (option.equalsIgnoreCase("verbose")) {
												config.setVerbose(value);
												context.getSource().sendFeedback(() -> Text.literal("Set verbose to " + value).formatted(Formatting.BOLD), true);
											} else if (option.equalsIgnoreCase("ender_eye_activation")) {
												config.enderEyeActivation(value);
												context.getSource().sendFeedback(() -> Text.literal("Set ender eye activation to " + value).formatted(Formatting.BOLD), true);
											} else {
												context.getSource().sendFeedback(() -> Text.literal("Unknown option " + option).formatted(Formatting.BOLD), false);
											}

											return Command.SINGLE_SUCCESS;
										})
								)
						))));
	}

	public void log(String info) {
		this.logger.info("[Endless/INFO] " + info);
	}

	public void warn(String info) {
		this.logger.warn("[Endless/WARN] " + info);
	}
}

