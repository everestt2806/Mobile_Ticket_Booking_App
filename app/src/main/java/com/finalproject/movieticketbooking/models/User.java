package com.finalproject.movieticketbooking.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.List;

@IgnoreExtraProperties
public class User implements Parcelable {
    private String id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String password;
    private String avatar;
    private List<String> ticketHistory;

    public User() {
        // Required empty constructor for Firebase
    }

    public User(String id, String email, String fullName, String phoneNumber,
                String password, String avatar, List<String> ticketHistory) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.avatar = avatar;
        this.ticketHistory = ticketHistory;
    }

    protected User(Parcel in) {
        id = in.readString();
        email = in.readString();
        fullName = in.readString();
        phoneNumber = in.readString();
        password = in.readString();
        avatar = in.readString();
        ticketHistory = in.createStringArrayList();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // Getters
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPassword() { return password; }
    public String getAvatar() { return avatar; }
    public List<String> getTicketHistory() { return ticketHistory; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setPassword(String password) { this.password = password; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public void setTicketHistory(List<String> ticketHistory) { this.ticketHistory = ticketHistory; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(fullName);
        dest.writeString(phoneNumber);
        dest.writeString(password);
        dest.writeString(avatar);
        dest.writeStringList(ticketHistory);
    }
}
