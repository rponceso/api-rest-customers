package com.nttdata.apirestcustomers.config;

import com.nttdata.apirestcustomers.events.Event;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private final String bootstrapAddress = "localhost:9092";

    @Bean
    public ProducerFactory<String, Event<?>> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress); //direccion del bus de me mensaje de Kafka
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, //la llave con la que vamos a serializar ese mensaje y de que tipo va a ser
                StringSerializer.class); //y que serializador vamos a usar para guardar esa llave en el bus de mensaje y utilizaremos StringSerializer y ene este caso llave sera una cadena
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, //la configuracion del objeto que vamos a serializar
                JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Event<?>> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
