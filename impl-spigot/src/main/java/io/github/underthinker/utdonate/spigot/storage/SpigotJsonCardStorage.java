package io.github.underthinker.utdonate.spigot.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.card.CardData;
import io.github.underthinker.utdonate.core.storage.MapBasedCardStorage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;

public class SpigotJsonCardStorage extends MapBasedCardStorage {
    private final Gson gson;

    public SpigotJsonCardStorage(UTDonateCore core, Input input) {
        super(core, input);
        boolean prettyPrint = Optional.ofNullable(input.getSettings().get("prettyPrint"))
                .map(Objects::toString)
                .map(Boolean::parseBoolean)
                .orElse(false);
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (prettyPrint) {
            gsonBuilder.setPrettyPrinting();
        }
        gson = gsonBuilder.create();
    }

    @Override
    protected String getFileExtension() {
        return "json";
    }

    @Override
    protected Map<Long, CardData> load(File file) {
        Type type = new TypeToken<Map<Long, CardData>>() { }.getType();
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, type);
        } catch (Exception e) {
            core.getLogger().log(Level.WARNING, e, () -> "Failed to load cards from " + file.getName());
            return Collections.emptyMap();
        }
    }

    @Override
    protected void save(File file, Map<Long, CardData> map) {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(map, writer);
        } catch (Exception e) {
            core.getLogger().log(Level.WARNING, e, () -> "Failed to save cards to " + file.getName());
        }
    }
}
