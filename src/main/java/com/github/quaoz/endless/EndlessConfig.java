package com.github.quaoz.endless;

import com.electronwill.nightconfig.core.file.FileConfig;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class EndlessConfig {
	public static final Path CONFIG_FILE_PATH = FabricLoader.getInstance().getConfigDir().resolve("endless.toml");
	private static final boolean DEFAULT_VERBOSE = false;
	private static final boolean DEFAULT_ENDER_EYE_ACTIVATION = true;
	private final Endless mod;
	protected FileConfig config;

	// Reads in the config file
	public EndlessConfig(@NotNull Endless mod) {
		this.mod = mod;
		this.config = FileConfig.builder(CONFIG_FILE_PATH)
				.defaultResource("/endless.toml")
				.autoreload()
				.autosave()
				.build();
	}

	// Loads the config
	public void load() {
		this.config.load();
		this.mod.log("Config loaded");
	}

	public boolean isVerbose() {
		return this.config.getOrElse("verbose", DEFAULT_VERBOSE);
	}

	public void setVerbose(boolean enabled) {
		this.config.set("verbose", enabled);
	}

	public boolean enderEyeActivation() {
		return this.config.getOrElse("ender_eye_activation", DEFAULT_ENDER_EYE_ACTIVATION);
	}

	public void enderEyeActivation(boolean enabled) {
		this.config.set("ender_eye_activation", enabled);
	}
}
