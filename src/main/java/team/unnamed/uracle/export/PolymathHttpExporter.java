package team.unnamed.uracle.export;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.Nullable;
import team.unnamed.uracle.io.ResourcePackWriter;
import team.unnamed.uracle.io.Streams;
import team.unnamed.uracle.io.TreeOutputStream;
import team.unnamed.uracle.resourcepack.RemoteResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipOutputStream;

public class PolymathHttpExporter implements ResourceExporter {

    public static final String NAME = "polymath";

    private static final JsonParser JSON_PARSER = new JsonParser();

    private static final String BOUNDARY = "UracleBoundary";
    private static final String LINE_FEED = "\r\n";

    private final URL url;
    private final String userId;

    public PolymathHttpExporter(URL url, String userId) {
        this.url = url;
        this.userId = userId;
    }

    @Override
    public @Nullable RemoteResource export(
            ResourcePackWriter writer
    ) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Charset", "utf-8");
        connection.setRequestProperty("User-Agent", "Uracle");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        // write request data
        try (OutputStream output = connection.getOutputStream()) {

            //#region write id field
            Streams.writeUTF(
                    output,
                    "--" + BOUNDARY + LINE_FEED
                            + "Content-Disposition: form-data; name=\"id\""
                            + LINE_FEED + LINE_FEED
            );
            Streams.writeUTF(output, userId);
            Streams.writeUTF(
                    output,
                    LINE_FEED + "--" + BOUNDARY + "--" + LINE_FEED
            );
            //#endregion


            //#region write pack
            Streams.writeUTF(
                    output,
                    "--" + BOUNDARY + LINE_FEED
                            + "Content-Disposition: form-data; name=\"pack\"; filename=\"resource-pack.zip\""
                            + LINE_FEED + "Content-Type: application/zip" + LINE_FEED + LINE_FEED
            );

            TreeOutputStream tree = TreeOutputStream.forZip(new ZipOutputStream(output));
            try {
                writer.write(tree);
            } finally {
                tree.finish();
            }
            Streams.writeUTF(
                    output,
                    LINE_FEED + "--" + BOUNDARY + "--" + LINE_FEED
            );
            //#endregion
        }

        // execute and read response
        try (Reader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            JsonElement element = JSON_PARSER.parse(reader);
            if (!element.isJsonObject()) {
                throw new IOException("Invalid response: " + element);
            }

            JsonObject json = element.getAsJsonObject();

            if (json.has("error")) {
                throw new IOException("Cannot upload to polymath: "
                        + json.get("error").getAsString());
            }

            String url = json.get("url").getAsString();
            byte[] hash = Streams.getBytesFromHex(json.get("sha1").getAsString());

            return new RemoteResource(url, hash);
        }
    }

}