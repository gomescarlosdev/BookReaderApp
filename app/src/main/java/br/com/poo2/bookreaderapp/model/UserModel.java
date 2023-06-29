package br.com.poo2.bookreaderapp.model;

public class UserModel {

    private String uid;
    private String name;
    private String profile;
    private String email;
    private String userType;

    public UserModel() {

    }

    public UserModel(String uid, String name, String profile, String email, String userType) {
        this.uid = uid;
        this.name = name;
        this.profile = profile;
        this.email = email;
        this.userType = userType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
