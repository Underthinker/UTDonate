package io.github.underthinker.utdonate.thesieutoc;

import io.github.underthinker.utdonate.core.entity.expansion.TopUpExpansion;
import lombok.Getter;

public class TheSieuToc extends TopUpExpansion {
    @Getter
    private final TheSieuTocConfig config = createConfig("thesieutoc", TheSieuTocConfig.class);

    @Override
    public void onEnable() {
        TheSieuTocApi api;
        switch (config.getApiVersion()) {
            case V1:
                api = new TheSieuTocApi(config.getApiKey(), config.getApiSecret());
                break;
            case V2:
                api = new TheSieuTocApi(config.getApiV2Key());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + config.getApiVersion());
        }
        TheSieuTocTopUp topUp = new TheSieuTocTopUp(this, api);
        registerTopUp("TheSieuToc", topUp);

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
