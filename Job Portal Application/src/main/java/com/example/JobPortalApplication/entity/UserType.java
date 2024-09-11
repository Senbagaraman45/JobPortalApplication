package com.example.JobPortalApplication.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users_type")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTypeId;
    private String userTypeName;
    @OneToMany(targetEntity = Users.class,mappedBy = "userTypeId",cascade = CascadeType.ALL)
    private List<Users> usersList;

    public UserType() {
    }

    public UserType(int userTypeId, String usertypeName, List<Users> usersList) {
        this.userTypeId = userTypeId;
        this.userTypeName = usertypeName;
        this.usersList = usersList;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUsertypeName() {
        return userTypeName;
    }

    public void setUsertypeName(String usertypeName) {
        this.userTypeName = usertypeName;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "userTypeId=" + userTypeId +
                ", userTypeName='" + userTypeName + '\'' +
                '}';
    }
}
