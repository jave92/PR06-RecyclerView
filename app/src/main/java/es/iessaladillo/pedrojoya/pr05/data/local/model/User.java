package es.iessaladillo.pedrojoya.pr05.data.local.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;

public class User implements Parcelable {

    private static long count=0;
    private long id;
    private final Avatar avatar;
    private final String name;
    private final String email;
    private final String phone;
    private final String address;
    private final String web;

    public User(Avatar avatar, String name, String email, String phone, String address, String web) {
        count++;
        this.id = count;
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.web = web;
    }

    protected User(Parcel in) {
        id = in.readLong();
        avatar = in.readParcelable(Avatar.class.getClassLoader());
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        address = in.readString();
        web = in.readString();
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getWeb() {
        return web;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(avatar, flags);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(web);
    }
}
