package com.nicecow.app;

public class CreatingUser {
    private String email;
    private String password;
    private String enterPasswordAgain;
    public CreatingUser() {}
    public CreatingUser(String email, String password, String enterPasswordAgain) {
        this.email = email;
        this.password = password;
        this.enterPasswordAgain = enterPasswordAgain;
    }
    public String getEmail() { return this.email; }
    public String getPassword() {  return this.password; }
    public String getEnterPasswordAgain() { return this.enterPasswordAgain; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setEnterPasswordAgain(String enterPasswordAgain) { this.enterPasswordAgain = enterPasswordAgain; }
}
