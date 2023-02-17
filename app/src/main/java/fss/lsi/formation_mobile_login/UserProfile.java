package fss.lsi.formation_mobile_login;

import android.widget.EditText;

public class UserProfile {

    public String userNumber;
    public String userEmail;
    public String userName;
    public String userPassword;
    public String userCin;

    public UserProfile(){
    }
    public UserProfile(String number, String email, String name, String password, String cin) {
        this.userNumber=number;
        this.userEmail=email;
        this.userName=name;
        this.userPassword=password;
        this.userCin = cin;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserCin() {
        return userCin;
    }

    public void setUserCin(String userCin) {
        this.userCin = userCin;
    }
}
