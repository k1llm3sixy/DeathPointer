package com.k1llm3sixy.deathpointer;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeathPointerClient implements ClientModInitializer {
    private static final String MOD_ID = "deathpointer";
    private static final Logger logger = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        logger.info("Mod started...");
    }
}