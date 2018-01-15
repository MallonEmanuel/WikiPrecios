package unpsjb.wikiprecios.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ema on 31/10/2015.
 */
public class User implements Parcelable {
    private int id;
    private String name;
    private String surname;
    private String mail;
    private String password;
    private int qualification;
    private int accumulated;
    private String facebookId;
    private int activeAcount;

    public User(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public User(int id, String name, String surname, String mail, String password, int qualification, int accumulated, String facebook_id, int active_acount) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.password = password;
        this.qualification = qualification;
        this.accumulated = accumulated;
        this.facebookId = facebook_id;
        this.activeAcount = active_acount;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", accumulated=" + accumulated +
                ", qualification=" + qualification+
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getQualification() {
        return qualification;
    }

    public void setQualification(int qualification) {
        this.qualification = qualification;
    }

    public int getAccumulated() {
        return accumulated;
    }

    public void setAccumulated(int accumulated) {
        this.accumulated = accumulated;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public int getActiveAcount() {
        return activeAcount;
    }

    public void setActiveAcount(int activeAcount) {
        this.activeAcount = activeAcount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeString(this.mail);
        dest.writeString(this.password);
        dest.writeInt(this.accumulated);
        dest.writeInt(this.qualification);
        dest.writeInt(this.activeAcount);
        dest.writeString(this.facebookId);
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.surname = in.readString();
        this.mail = in.readString();
        this.password = in.readString();
        this.accumulated = in.readInt();
        this.qualification = in.readInt();
        this.activeAcount = in.readInt();
        this.facebookId = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
