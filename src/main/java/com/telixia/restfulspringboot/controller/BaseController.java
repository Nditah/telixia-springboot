package com.telixia.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.telixia.springboot.property.ApiUrlProperties;
import com.telixia.springboot.util.UrlUtil;

@RestController
public class BaseController {
    @Autowired
    private ApiUrlProperties apiUrlProperties;

    protected String getApiUrl() {
        String apiUrl = apiUrlProperties.getApiUrl();
        if (apiUrl == null || apiUrl.isEmpty()) {
            apiUrl = UrlUtil.getBaseEnvLinkURL();
        }
        return apiUrl;
    }
}
