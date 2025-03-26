package ai.conbo.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class FolderWatcherScheduler {
    private final FolderWatcher folderWatcher;

    public FolderWatcherScheduler(FolderWatcher folderWatcher) {
        this.folderWatcher = folderWatcher;
    }

    @Scheduled(fixedDelay = Long.MAX_VALUE)
    public void startWatching() {
        try {
            folderWatcher.watchFolder();
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Folder watcher failed", e);
        }
    }
}