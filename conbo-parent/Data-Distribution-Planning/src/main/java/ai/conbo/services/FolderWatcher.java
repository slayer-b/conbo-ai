package ai.conbo.services;

import ai.conbo.model.DetectionEvent;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.*;

@Service
public class FolderWatcher {
    private final Path watchPath;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper mapper = new ObjectMapper();

    {
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.findAndRegisterModules();
    }

    public FolderWatcher(
            @Value("${app.folder.path}") String folderPath,
            KafkaProducer kafkaProducerService) {
        this.watchPath = Paths.get(folderPath);
        this.kafkaProducer = kafkaProducerService;
    }

    public void watchFolder() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        watchPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        while (!Thread.currentThread().isInterrupted()) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    System.out.println("process file");
                    Path newFile = watchPath.resolve((Path) event.context());
                    processNewFile(newFile);
                }
            }
            key.reset();
        }
    }

    private void processNewFile(Path file) throws IOException {
        DetectionEvent detectionEvent = mapper.readValue(file.toFile(), DetectionEvent.class);

        kafkaProducer.sendDetectionEvent(detectionEvent);
    }
}