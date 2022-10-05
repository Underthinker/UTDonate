package io.github.underthinker.utdonate.thesieutoc;

import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.entity.topup.TopUp;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class TheSieuTocTopUp implements TopUp {

    private final TheSieuToc addon;
    private final TheSieuTocApi api;

    public TheSieuTocTopUp(TheSieuToc addon, TheSieuTocApi api) {
        this.addon = addon;
        this.api = api;
    }

    @Override
    public boolean submitAndCheck(Card card) {
        try {
            String response = api.submit(card.getSerial(), card.getPin(), card.getProvider(), String.valueOf(card.getDenomination()));
            Map<String, Object> map = addon.getCore().getJsonFactory().deserialize(response);
            TheSieuTocApi.Status status = TheSieuTocApi.Status.from(Objects.toString(map.get("status"), "UNKNOWN"));
            return status.isSuccess();
        } catch (IOException e) {
            return false;
        }
    }

}
