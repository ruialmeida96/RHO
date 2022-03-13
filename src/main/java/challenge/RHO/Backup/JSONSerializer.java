package challenge.RHO.Backup;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.sql.rowset.serial.SerialException;
import java.util.Map;

public class JSONSerializer<T> implements Serializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JSONSerializer() {
    }

    public void configure(Map<String, ?> config, boolean isKey) {
    }

    public byte[] serialize(String topic, T data) {
        if (data == null)
            return null;
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error serialization JSON message", e);
        }
    }

    public void close() {
    }
}
