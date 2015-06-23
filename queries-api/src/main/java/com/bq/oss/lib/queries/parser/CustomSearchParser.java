package com.bq.oss.lib.queries.parser;

import java.io.IOException;
import java.util.Optional;

import com.bq.oss.lib.queries.exception.MalformedJsonQueryException;
import com.bq.oss.lib.queries.request.Search;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

/**
 * @author Rubén Carrasco
 *
 */
public class CustomSearchParser implements SearchParser {

    private final ObjectMapper mapper;

    public CustomSearchParser(ObjectMapper mapper) {
        this.mapper = mapper;
        mapper.registerModule(new Jdk8Module());
    }

    @Override
    public Search parse(String searchString) throws MalformedJsonQueryException {
        try {
            Search search = mapper.readValue(searchString, Search.class);
            if (search.getFields() == null) {
                search.setFields(Optional.empty());
            }
            return search;
        } catch (IOException e) {
            throw new MalformedJsonQueryException("Malformed search object");
        }
    }
}
