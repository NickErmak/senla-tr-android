package com.senla.gson_bolts.models.responses;

public class ResponseLogin extends Response{
    private String token;

    public ResponseLogin(){
        super();
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
