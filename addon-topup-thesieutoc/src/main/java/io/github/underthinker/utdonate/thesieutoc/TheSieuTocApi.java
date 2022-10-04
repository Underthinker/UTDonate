package io.github.underthinker.utdonate.thesieutoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static io.github.underthinker.utdonate.thesieutoc.TheSieuTocApi.Constants.*;

// TODO: Using design pattern to make this class more flexible
public class TheSieuTocApi {
    private final Version version;
    private final String submitUrl;
    private final String checkUrl;
    private final String key;
    private final String secret;
    private final List<Consumer<String>> callbackListeners = new ArrayList<>();

    public TheSieuTocApi(String key, String secret) {
        this.version = Version.V1;
        this.submitUrl = HOST + V1_SUBMIT_ENDPOINT;
        this.checkUrl = HOST + V1_CHECK_ENDPOINT;
        this.key = key;
        this.secret = secret;
    }

    public TheSieuTocApi(String key) {
        this.version = Version.V2;
        this.submitUrl = HOST + V2_SUBMIT_ENDPOINT;
        this.checkUrl = HOST + V2_CHECK_ENDPOINT;
        this.key = key;
        this.secret = null;
    }

    private URL createUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    public Version getVersion() {
        return version;
    }

    public String submit(String serial, String pin, String provider, String price, String... data) throws IOException {
        String urlString = MessageFormat.format(submitUrl, key, secret, serial, pin, provider, price, data);
        URL url = createUrl(urlString);
        return request(url);
    }

    // TODO: v2
    public void addCallbackListener(Consumer<String> listener) {
        callbackListeners.add(listener);
    }

    // TODO: v2
    public CompletableFuture<Void> listenCallback(int port) {
        return CompletableFuture.runAsync(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (!serverSocket.isClosed()) {
                    Socket socket = serverSocket.accept();
                    try (InputStream inputStream = socket.getInputStream()) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder builder = new StringBuilder();
                        @SuppressWarnings("unused") String headers;
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                            if (line.isEmpty()) { // end of headers
                                headers = builder.toString();
                                builder.setLength(0); // reset builder
                            }
                        }
                        String response = builder.toString();
                        for (Consumer<String> listener : callbackListeners) {
                            listener.accept(response);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public String check(String transactionId) throws IOException {
        String urlString = MessageFormat.format(checkUrl, key, secret, transactionId);
        URL url = createUrl(urlString);
        return request(url);
    }

    private String request(URL url) throws IOException {
        return request(url, null);
    }

    private String request(URL url, InputStream data) throws IOException {
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        if (data != null) {
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(data.available()));
            while (data.available() > 0) {
                connection.getOutputStream().write(data.read());
            }
        }

        connection.setDoInput(true);
        try (
                InputStream inputStream = connection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(streamReader)
        ) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    public enum Version {
        V1, V2;

        public static Version from(String version) {
            return Version.valueOf(version.toUpperCase());
        }
    }

    public enum Status {
        UNKNOWN(""),
        SUCCESS("00"),
        MISSING_API_KEY("54"),
        WRONG_API_INFO("1"),
        ACCOUNT_BANNED("3"),
        CARD_TYPE_MAINTENANCE("-1089"),
        CARD_IS_USED("2"),
        MISSING_SERIAL("56"),
        MISSING_PIN("55"),
        MISSING_TYPE("52"),
        UNKNOWN_ERROR("47");

        private final String code;

        Status(String code) {
            this.code = code;
        }

        public static Status from(String code) {
            for (Status status : values()) {
                if (status.getCode().equals(code)) {
                    return status;
                }
            }
            return UNKNOWN;
        }

        public String getCode() {
            return code;
        }

        public boolean isSuccess() {
            return this == SUCCESS;
        }
    }

    public static final class Constants {
        public static final String HOST = "https://thesieutoc.net";
        public static final String V1_SUBMIT_ENDPOINT = "/API/transaction?APIkey={0}&APIsecret={1}&seri={2}&mathe={3}&type={4}&menhgia={5}";
        public static final String V1_CHECK_ENDPOINT = "/API/get_status_card.php?APIkey={0}&APIsecret={1}&transaction_id={2}";
        public static final String V2_SUBMIT_ENDPOINT = "/chargingws/v2?APIkey={0}&APIsecret={1}&seri={2}&mathe={3}&type={4}&menhgia={5}";
        public static final String V2_CHECK_ENDPOINT = "/API/get_status_card.php?APIkey={0}&APIsecret={1}&transaction_id={2}"; // TODO: ask for the endpoint

        private Constants() {
            throw new UnsupportedOperationException();
        }
    }

}
