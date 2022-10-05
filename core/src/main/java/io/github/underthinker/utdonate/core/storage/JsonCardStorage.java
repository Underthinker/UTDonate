package io.github.underthinker.utdonate.core.storage;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.card.CardData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class JsonCardStorage extends MapBasedCardStorage {
    public JsonCardStorage(UTDonateCore core, Input input) {
        super(core, input);
    }

    @Override
    protected String getFileExtension() {
        return "json";
    }

    @Override
    protected Map<Long, CardData> load(File file) {
        Map<Long, CardData> map = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            String json = String.join("", lines);
            Map<String, Object> rawMap = core.getJsonFactory().deserialize(json);
            for (Map.Entry<String, Object> entry : rawMap.entrySet()) {
                long id = Long.parseLong(entry.getKey());
                Map<String, Object> cardDataMap = new HashMap<>();
                for (Map.Entry<?, ?> cardDataEntry : ((Map<?, ?>) entry.getValue()).entrySet()) {
                    cardDataMap.put(Objects.toString(cardDataEntry.getKey()), cardDataEntry.getValue());
                }
                map.put(id, CardData.deserialize(cardDataMap));
            }
        } catch (IOException e) {
            core.getLogger().log(Level.WARNING, e, () -> "Failed to read file " + file.getName());
        }
        return map;
    }

    @Override
    protected void save(File file, Map<Long, CardData> map) {
        Map<String, Object> rawMap = new HashMap<>();
        for (Map.Entry<Long, CardData> entry : map.entrySet()) {
            rawMap.put(Long.toString(entry.getKey()), entry.getValue().serialize());
        }
        String json = core.getJsonFactory().serialize(rawMap);
        try {
            Files.write(file.toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            core.getLogger().log(Level.WARNING, e, () -> "Failed to write file " + file.getName());
        }
    }
}
