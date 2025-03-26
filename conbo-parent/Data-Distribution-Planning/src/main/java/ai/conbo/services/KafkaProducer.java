package ai.conbo.services;

import ai.conbo.model.DetectionEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;

    public KafkaProducer(
            KafkaTemplate<String, Object> kafkaTemplate,
            @Value("${app.kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendDetectionEvent(DetectionEvent event) {
        kafkaTemplate.send(topic, event);
        System.out.println("Sent Kafka event: " + event);
    }
}
