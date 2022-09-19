package com.sys.common.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;

/**
 * @author oxhainan
 */
public class XssStringJsonDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String source = jsonParser.getText().trim();
        //  富文本解码
        source.replace("&nbsp;"," ");
        return StringEscapeUtils.unescapeHtml4(source);
    }

}
