package fss.lsi.formation_mobile_login;

public class dataHolder {
    String email,champ2,champ3,champ4,champ5;
    public dataHolder(String email, String champ2 , String champ3, String champ4, String champ5){
        this.email=email;
        this.champ2=champ2;
        this.champ3=champ3;
        this.champ4=champ4;
        this.champ5=champ5;
    }

    public String getChamp5() {
        return champ5;
    }

    public void setChamp5(String champ5) {
        this.champ5 = champ5;
    }

    public String getChamp4() {
        return champ4;
    }

    public void setChamp4(String champ4) {
        this.champ4 = champ4;
    }

    public String getChamp1() {
        return email;
    }

    public void setChamp1(String champ1) {
        this.email = champ1;
    }

    public String getChamp2() {
        return champ2;
    }

    public void setChamp2(String champ2) {
        this.champ2 = champ2;
    }

    public String getChamp3() {
        return champ3;
    }

    public void setChamp3(String champ3) {
        this.champ3 = champ3;
    }
}
