package challenge.RHO.Backup;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class JSONDeserializing<T> implements Deserializer {
    private Class <T> type;

    /*public JSONDeserializing(Class type) {
        this.type = type;
    }*/

    public JSONDeserializing(Class<T> type) {
        this.type = type;
    }

    @Override
    public void configure(Map map, boolean b) {    }

    @Override
    public Object deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(bytes, type);
        } catch (Exception e) {
            throw new SerializationException("Error deserialization JSON message",e);
        }
    }

    @Override
    public void close() {

    }
}