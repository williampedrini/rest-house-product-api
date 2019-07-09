package com.custodio.resthouse.product.api.common.util;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;

/**
 * Class responsible for manipulating the data related to a JSON object.
 *
 * @author i505483
 */
public final class JSONUtil {

    private final ObjectMapper mapper;

    private JSONUtil(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public static JSONUtil.Builder builder() {
        return new JSONUtil.Builder();
    }

    /**
     * Read a certain JSON file and map its value to a certain bean type.
     *
     * @param path The of the file to be read.
     * @param type The {@link JavaType} used for the conversion.
     * @return The representation of the file content as a bean.
     * @throws IOException ProductValidationError while reading the json file.
     */
    public <T> T fileToBean(final String path, final Class<T> type) throws IOException {

        final File file = new File(JSONUtil.class.getResource(path).getPath());

        return this.mapper.readValue(file, TypeFactory.defaultInstance().constructType(type));
    }

    public static class Builder {

        private final ObjectMapper mapper = new ObjectMapper();

        Builder() {
        }

        public Builder withMixIn(final Class<?> target, final Class<?> mixinSource) {
            this.mapper.addMixIn(target, mixinSource);
            return this;
        }

        public Builder withKeyDeserializer(final Class<?> type, final KeyDeserializer keyDeserializer) {
            final SimpleModule attributeDescriptorModule = new SimpleModule();
            attributeDescriptorModule.addKeyDeserializer(type, keyDeserializer);
            this.mapper.registerModule(attributeDescriptorModule);
            return this;
        }

        public <T> Builder withDeserializer(final Class<T> type, final JsonDeserializer<? extends T> jsonDeserializer) {
            final SimpleModule attributeDescriptorModule = new SimpleModule();
            attributeDescriptorModule.addDeserializer(type, jsonDeserializer);
            this.mapper.registerModule(attributeDescriptorModule);
            return this;
        }

        public Builder withModule(final Module module) {
            this.mapper.registerModule(module);
            return this;
        }

        public JSONUtil build() {
            return new JSONUtil(this.mapper);
        }
    }
}