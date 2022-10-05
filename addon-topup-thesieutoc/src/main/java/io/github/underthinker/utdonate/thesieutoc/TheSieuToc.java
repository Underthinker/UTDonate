package io.github.underthinker.utdonate.thesieutoc;

import io.github.underthinker.utdonate.core.entity.addon.TopUpAddon;
import lombok.Getter;

public class TheSieuToc extends TopUpAddon {
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
                api = new TheSieuTocApi(config.getApiKey());
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
