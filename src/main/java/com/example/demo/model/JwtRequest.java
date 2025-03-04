package com.example.demo.model;

public class JwtRequest {

    private String inputData;
    private String clientName;

    public JwtRequest() {
    }

    public JwtRequest(String inputData, String clientName) {
        this.inputData = inputData;
        this.clientName = clientName;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
