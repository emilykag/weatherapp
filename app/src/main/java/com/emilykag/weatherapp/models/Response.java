package com.emilykag.weatherapp.models;

public class Response {

    private String response;
    private int code;

    public Response(String response, int code) {
        this.response = response;
        this.code = code;
    }

    public String getResponse() {
        return response;
    }

    public int getCode() {
        return code;
    }
}
