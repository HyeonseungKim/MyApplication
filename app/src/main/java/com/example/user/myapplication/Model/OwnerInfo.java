package com.example.user.myapplication.Model;

public class OwnerInfo{

    String ownerName;
    String ownerAvatar;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAvatar() {
        return ownerAvatar;
    }

    public void setOwnerAvatar(String ownerAvatar) {
        this.ownerAvatar = ownerAvatar;
    }

    public OwnerInfo(String _ownerName, String _ownerAvatar){
        ownerName = _ownerName;
        ownerAvatar = _ownerAvatar;
    }
}
