package com.vFranco.vFranco.request;

public class RecoverRequest {
    
    String password1;
    String password2;
    String recoverCode;
    
    public String getPassword1() {
        return password1;
    }
    public String getPassword2() {
        return password2;
    }
    public String getRecoverCode() {
        return recoverCode;
    }
    public void setPassword1(String password1) {
        this.password1 = password1;
    }
    public void setPassword2(String password2) {
        this.password2 = password2;
    }
    public void setRecoverCode(String recoverCode) {
        this.recoverCode = recoverCode;
    }

    
}
