package challenge.RHO.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class JSONDeserializerTest<T> implements Deserializer<T> {
    private ObjectMapper mapper = new ObjectMapper();
    private Class<T> className;

    public static final String KEY_CLASS_NAME_CONFIG = "key.class.name";
    public static final String VALUE_CLASS_NAME_CONFIG = "value.class.name";

    public JSONDeserializerTest(){}

    public void configure(Map<String,?> props,boolean isKey){
        if(isKey)
            className=(Class<T>) props.get(KEY_CLASS_NAME_CONFIG);
        else
            className=(Class<T>) props.get(VALUE_CLASS_NAME_CONFIG);
    }

    public T deserialize(String topic,byte[] data){
        if (data ==null)
            return null;
        try{
            return mapper.readValue(data,className);
        }catch (Exception e){
            throw  new SerializationException("Problems on desialization.",e);
        }
    }

    public void close(){}
}
