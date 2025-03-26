package ai.conbo;

import ai.conbo.model.Config;
import ai.conbo.model.DetectionEvent;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Random;

public class Main {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true)
              .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.findAndRegisterModules();
    }

    public static void main(String[] args)
            throws IOException, InterruptedException {


        Path dir = Path.of(Config.PATH_TO_DIR);
        if (!Files.exists(dir)) {
            Files.createDirectory(dir);

        }
        while (true) {
            DetectionEvent event = createEvent();
            String json = mapper.writeValueAsString(event);
            Path resolved = dir.resolve(event.getTime()
                    .getLong(ChronoField.NANO_OF_DAY) + ".json");

            if (!Files.exists(resolved)) {
                Files.createFile(resolved);

                Path path = resolved.toRealPath();
                Files.writeString(path, json);
            }

            Thread.sleep(Config.DELAY_MS);
        }
    }

    static long ID = 0L;
    static final Random random =new Random();
    private static synchronized DetectionEvent createEvent() {
        ID += 1;
        return new DetectionEvent(LocalDateTime.now(), "home", ID, random.nextLong(), random.nextLong(), "empty", 1L);
    }

}
