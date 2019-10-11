package com.zetcode.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VersionService implements IVersionService {

    @Value("${app.version}")
    private String appVersion;

    @Override
    public String getVersion() {

        return appVersion;
    }
}