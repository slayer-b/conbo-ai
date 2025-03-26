package ai.conbo.model;

import java.time.LocalDateTime;

public class DetectionEvent {

    private LocalDateTime time;
    private String source;
    private Long id;
    private Long latitude;
    private Long longtitude;
    private String objectType;
    private Long score;

    public DetectionEvent() {
    }

    public DetectionEvent(LocalDateTime time, String source, Long id, Long latitude, Long longtitude, String objectType, Long score) {
        this.time = time;
        this.source = source;
        this.id = id;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.objectType = objectType;
        this.score = score;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public void setLongtitude(Long longtitude) {
        this.longtitude = longtitude;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getSource() {
        return source;
    }

    public Long getId() {
        return id;
    }

    public Long getLatitude() {
        return latitude;
    }

    public Long getLongtitude() {
        return longtitude;
    }

    public String getObjectType() {
        return objectType;
    }

    public Long getScore() {
        return score;
    }
}
