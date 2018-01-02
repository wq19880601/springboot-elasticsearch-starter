package com.dianwoba.middleware.elastic.querydsl.jackson;

import com.dianwoba.middleware.elastic.querydsl.ElasticsearchQuery;
import com.dianwoba.middleware.elastic.visitor.ElasticsearchSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.Function;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.annotation.Nullable;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;

/**
 * JacksonElasticsearchQueries is a factory to provide ElasticsearchQuery basic implementation.
 *
 * @author Kevin Leturc
 */
public  class JacksonElasticsearchQueries {

    private final Client client;

    /**
     * Default constructor.
     *
     * @param client The elasticsearch2 client.
     */
    public JacksonElasticsearchQueries(Client client) {
        this.client = client;
    }

    public ElasticsearchQuery query(String index, String type,Class<?> entityClass) {
        return query(index, type, null,entityClass, new ElasticsearchSerializer());
    }

    public ElasticsearchQuery query(String index, String type,String routing,Class<?> entityClass) {
        return query(index, type, routing,entityClass, new ElasticsearchSerializer());
    }

    public  ElasticsearchQuery query(String index, String type,String routing, Class<?> entityClass, ElasticsearchSerializer serializer) {
        return query(index, type, routing,serializer, defaultTransformer(entityClass));
    }

    public  ElasticsearchQuery query(String index, String type, String routing,Function<SearchHit, Object> transformer) {
        return query(index, type,routing, new ElasticsearchSerializer(), transformer);
    }

    public  ElasticsearchQuery query(final String index, final String type,String routing, ElasticsearchSerializer serializer, Function<SearchHit, Object> transformer) {
        return new ElasticsearchQuery(client, transformer, serializer, index, type, routing );
    }

    /**
     * Returns the default transformer.
     *
     * @param entityClass The entity class.
     * @return The default transformer.
     */
    private  Function<SearchHit, Object> defaultTransformer(final Class entityClass) {
        final ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(
            PropertyNamingStrategy.SNAKE_CASE).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(sf);

        return new Function<SearchHit, Object>() {

            /**
             * {@inheritDoc}
             */
            @Nullable
            @Override
            public Object apply(@Nullable SearchHit input) {
                try {
                    Object bean = mapper.readValue(input.getSourceAsString(), entityClass);

//                    Field idField = null;
//                    Class<?> target = entityClass;
//                    while (idField == null && target != Object.class) {
//                        for (Field field : target.getDeclaredFields()) {
//                            if ("id".equals(field.getName())) {
//                                idField = field;
//                            }
//                        }
//                        target = target.getSuperclass();
//                    }
//                    if (idField != null) {
//                        idField.setAccessible(true);
//                        idField.set(bean, input.getId());
//                    }

                    return bean;
                } catch (SecurityException se) {
                    throw new MappingException("Unable to lookup id field, may be use a custom transformer ?", se);
                } catch (IOException e) {
                    throw new MappingException("Unable to read the Elasticsearch response.", e);
                }
            }
        };
    }
}