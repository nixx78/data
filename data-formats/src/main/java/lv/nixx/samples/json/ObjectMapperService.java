package lv.nixx.samples.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperService extends ObjectMapper {
	
	public ObjectMapperService() {
		configure(SerializationFeature.INDENT_OUTPUT, true);
		disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

}
