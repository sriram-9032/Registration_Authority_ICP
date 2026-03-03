package ug.daes.ra.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import ug.daes.ra.request.entity.LogModel;

@Service
public class KafkaSender {
    static Logger logger = LoggerFactory.getLogger(KafkaSender.class);
    private static final String CLASS = "KafkaSender";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    public KafkaSender(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${com.dt.kafka.topic.central}")
    private String topic;

    @Value("${com.dt.kafka.ra.topic}")
    private String raTopic;

    public void send(LogModel logmodel) {
        kafkaTemplate.send(topic, logmodel);
        kafkaTemplate.send(raTopic, logmodel);
        logger.info("Kafka messages sent to topics: {} , {}", topic, raTopic);
        logger.info("{} :: Kafka messages sent to topics: {} , {}", CLASS, topic, raTopic);
    }
}
