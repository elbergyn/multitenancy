package br.tec.dig.app.utils.serializers;

import br.tec.dig.app.exception.EnumException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import springfox.documentation.schema.Enums;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.AllowableValues;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumDeserializer extends JsonDeserializer<Object>
        implements ContextualDeserializer {
    private Class<?> targetClass;
    private String acceptedString;

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        String targetClassName = ctxt.getContextualType().toCanonical();
        try {
            targetClass = Class.forName(targetClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        AllowableValues list = Enums.allowableValues(targetClass);
        acceptedString = ((AllowableListValues) list).getValues().toString();
        return this;
    }

    @Override
    public Enum deserialize(@NotNull JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getText();

        if (StringUtils.isEmpty(value)) {
            return null;
        }

        Class<?> objectClass = jsonParser.getCurrentValue().getClass();
        String field = jsonParser.getCurrentName();

        List items = Arrays.stream(targetClass.getEnumConstants())
                .filter(o -> ((Enum) o).name().equals(value))
                .collect(Collectors.toList());

        if(items.size() == 0){
            throw new EnumException(jsonParser, field, "valores aceitos "+acceptedString, objectClass);
        }

        return (Enum) items.get(0);
    }
}
