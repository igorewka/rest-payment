package eu.isakels.rest.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public abstract class TestUtil {

    public static final ObjectMapper objMapper = new ObjectMapper() {{
        registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        enable(SerializationFeature.INDENT_OUTPUT);
    }};
}
