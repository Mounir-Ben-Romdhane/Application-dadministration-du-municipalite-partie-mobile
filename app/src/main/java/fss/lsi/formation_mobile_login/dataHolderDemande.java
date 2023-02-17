package fss.lsi.formation_mobile_login;

public class dataHolderDemande {
    String email,champ1,champ2,champ3,champ4;

    public dataHolderDemande(String email, String champ1, String champ2, String champ3, String champ4) {
        this.email = email;
        this.champ1 = champ1;
        this.champ2 = champ2;
        this.champ3 = champ3;
        this.champ4 = champ4;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChamp1() {
        return champ1;
    }

    public void setChamp1(String champ1) {
        this.champ1 = champ1;
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

    public String getChamp4() {
        return champ4;
    }

    public void setChamp4(String champ4) {
        this.champ4 = champ4;
    }
}
