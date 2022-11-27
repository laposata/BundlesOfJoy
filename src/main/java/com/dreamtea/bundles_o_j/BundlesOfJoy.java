package com.dreamtea.bundles_o_j;

import com.dreamtea.bundles_o_j.color_provider.BundleColorProvider;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BundlesOfJoy implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String NAMESPACE = "${mod_id}";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);

	@Override
	public void onInitialize() {
		BundleColorProvider.registerColors();
		LOGGER.info("Opening a bundle of Joy");
	}
}
