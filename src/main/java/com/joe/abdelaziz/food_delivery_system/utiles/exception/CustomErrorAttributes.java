package com.joe.abdelaziz.food_delivery_system.utiles.exception;

import java.util.Arrays;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
    Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

    errorAttributes.put("success", Boolean.FALSE);
    errorAttributes.put("status", errorAttributes.get("error"));
    errorAttributes.put("exception", errorAttributes.get("message"));
    errorAttributes.put("details", Arrays.asList(errorAttributes.get("message")));

    errorAttributes.remove("error");
    errorAttributes.remove("path");
    errorAttributes.remove("trace");

    return errorAttributes;

  }

}
