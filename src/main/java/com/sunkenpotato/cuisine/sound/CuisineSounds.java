package com.sunkenpotato.cuisine.sound;

import com.sunkenpotato.cuisine.Cuisine;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class CuisineSounds {

    public static final SoundEvent FINISH_COOKING = registerSoundEvent("finish_cooking");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(Cuisine.MOD_ID, name);

        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        Cuisine.LOGGER.info("Registering Sounds");
    }

}
