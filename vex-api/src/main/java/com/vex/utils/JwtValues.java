package com.vex.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vex.exceptions.ExceptionType;
import com.vex.exceptions.ServiceException;

public class JwtValues {

    public static final String AUTHORIZATION = "Authorization";

    public static synchronized String getParam(JwtParam jwtParam, String decodedToken) throws ServiceException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(decodedToken);

            // Get the value associated with the "username" key
            return jsonNode.get(jwtParam.getValue()).asText();
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_GETTING_PARAM_FROM_JWT, e.getMessage());
        }
    }
}
