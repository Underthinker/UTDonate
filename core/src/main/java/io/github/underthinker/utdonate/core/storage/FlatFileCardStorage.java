package io.github.underthinker.utdonate.core.storage;

import io.github.underthinker.utdonate.core.UTDonateCore;
import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.entity.card.CardData;
import me.hsgamer.hscore.common.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.logging.Level;

/**
 * The {@link io.github.underthinker.utdonate.core.entity.storage.CardStorage} implementation using flat file (CSV)
 */
public class FlatFileCardStorage extends MapBasedCardStorage {
    public FlatFileCardStorage(UTDonateCore core, Input input) {
        super(core, input);
    }

    private static String serializeCardData(Pair<Long, CardData> cardDataPair) {
        long id = cardDataPair.getKey();
        CardData cardData = cardDataPair.getValue();
        List<String> list = new ArrayList<>();
        list.add(Long.toString(id));
        list.add(Objects.toString(cardData.getCard().getOwnerId(), ""));
        list.add(Objects.toString(cardData.getCard().getOwnerName(), ""));
        list.add(cardData.getCard().getSerial());
        list.add(cardData.getCard().getPin());
        list.add(Integer.toString(cardData.getCard().getDenomination()));
        list.add(cardData.getCard().getProvider());
        list.addAll(cardData.serializeData());
        return String.join(",", list);
    }

    private static Pair<Long, CardData> deserializeCardData(String string) {
        String[] split = string.split(",");
        long id = Long.parseLong(split[0]);
        UUID ownerId = Optional.ofNullable(split[1]).filter(s -> !s.isEmpty()).map(UUID::fromString).orElse(null);
        String ownerName = Optional.ofNullable(split[2]).filter(s -> !s.isEmpty()).orElse(null);
        String serial = split[3];
        String pin = split[4];
        int price = Integer.parseInt(split[5]);
        String provider = split[6];
        Map<String, String> data = CardData.deserializeData(Arrays.copyOfRange(split, 7, split.length));
        return Pair.of(id, new CardData(new Card(ownerId, ownerName, serial, pin, price, provider), data));
    }

    @Override
    protected String getFileExtension() {
        return "csv";
    }

    @Override
    protected Map<Long, CardData> load(File file) {
        Map<Long, CardData> map = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            for (String line : lines) {
                Pair<Long, CardData> cardDataPair = deserializeCardData(line);
                map.put(cardDataPair.getKey(), cardDataPair.getValue());
            }
        } catch (IOException e) {
            core.getLogger().log(Level.WARNING, e, () -> "Failed to read file " + file.getName());
        }
        return map;
    }

    @Override
    protected void save(File file, Map<Long, CardData> map) {
        try {
            List<String> list = new ArrayList<>();
            map.forEach((id, cardData) -> list.add(serializeCardData(Pair.of(id, cardData))));
            Files.write(file.toPath(), list, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            core.getLogger().log(Level.SEVERE, e, () -> "Failed to save file " + file.getName());
        }
    }
}
