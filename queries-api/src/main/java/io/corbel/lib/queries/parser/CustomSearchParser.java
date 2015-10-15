package io.corbel.lib.queries.parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.corbel.lib.queries.exception.MalformedJsonQueryException;
import io.corbel.lib.queries.request.Search;

/**
 * @author Rubén Carrasco
 *
 */
public class CustomSearchParser implements SearchParser {

    private static final String INVALID_SEARCH_OBJECT = "Invalid Search Object";
    private static final String PARAMS = "params";
    private static final String TEMPLATE_NAME = "templateName";
    private final ObjectMapper mapper;

    public CustomSearchParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Search parse(String searchString, boolean binded) throws MalformedJsonQueryException {
        try {
            Map<String, Object> map = mapper.readValue(searchString, HashMap.class);
            return new Search(binded, getTemplateName(map), getTemplateParams(map));
        } catch (ClassCastException e) {
            throw new MalformedJsonQueryException(e);
        } catch (IOException e) {
            return new Search(binded, searchString);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getTemplateParams(Map<String, Object> map) throws MalformedJsonQueryException {
        Map<String, Object> params = (Map<String, Object>) map.get(PARAMS);
        if (params != null) {
            return params;
        } else {
            throw new MalformedJsonQueryException(INVALID_SEARCH_OBJECT);
        }
    }

    private String getTemplateName(Map<String, Object> map) throws MalformedJsonQueryException {
        String name = (String) map.get(TEMPLATE_NAME);
        if (name != null) {
            return name;
        } else {
            throw new MalformedJsonQueryException(INVALID_SEARCH_OBJECT);
        }
    }
}
