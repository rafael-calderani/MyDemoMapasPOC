package com.example.logonpf.demomapaspoc.api;

public class ApiUtils {

    public static final String BASE_URL = "http://10.3.1.12:3000";

    public static MetroAPI getMetroAPI() {
        return RetrofitClient.getClient(BASE_URL).create(MetroAPI.class);
    }
}