package io.github.underthinker.utdonate.thesieutoc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.underthinker.utdonate.core.entity.card.Card;
import io.github.underthinker.utdonate.core.entity.topup.TopUp;

import java.io.IOException;

public class TheSieuTocTopUp implements TopUp {

    private final TheSieuTocApi api;

    public TheSieuTocTopUp(TheSieuTocApi api) {
        this.api = api;
    }

    @Override
    public boolean submitAndCheck(Card card) {
        try {
            String response = api.submit(card.getSerial(), card.getPin(), card.getProvider(), String.valueOf(card.getDenomination()));
            JsonObject jsonResponse = new JsonParser().parse(response).getAsJsonObject();
            TheSieuTocApi.Status status = TheSieuTocApi.Status.from(jsonResponse.get("status").getAsString());
            return status.isSuccess();
        } catch (IOException e) {
            return false;
        }
    }

}
