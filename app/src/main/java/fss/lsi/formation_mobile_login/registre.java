package fss.lsi.formation_mobile_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registre extends AppCompatActivity {

    private EditText name,email,number,password,cin;
    private FirebaseAuth firebaseAuth;
    String number1, email1, name1,password1,cin1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);
        name = (EditText)findViewById(R.id.etNameRegister);
        email = (EditText)findViewById(R.id.etEmailRegistre);
        number = (EditText)findViewById(R.id.etnumberregistre);
        cin = findViewById(R.id.etCinregistre);
        password = (EditText)findViewById(R.id.etPassword);
        Button btnlogin = (Button) findViewById(R.id.btnLoginRegister);
        TextView haveacc = (TextView) findViewById(R.id.haveaccRegister);

        firebaseAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(v -> {
            if(validate()){
                //upload date to database
                String user_email = email.getText().toString().trim();
                String user_password = password.getText().toString().trim();

                firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        sendEmailVerification();
                    }else {
                        Toast.makeText(registre.this,"فشل في التسجيل!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        haveacc.setOnClickListener(v -> startActivity(new Intent(registre.this, HomeActivity.class)));
    }

    private Boolean validate(){
        boolean result = false;
        name1 = name.getText().toString();
        email1 = email.getText().toString();
        password1 = password.getText().toString();
        number1 = number.getText().toString();
        cin1 = cin.getText().toString();

        if(name1.isEmpty() || name1.length()<7){
            showError(name,"الإسم غير صالح!");
        }else if (email1.isEmpty() || !email1.contains("@")){
            showError(email,"بريدك الالكتروني خاطئ!");
        }else if (number1.isEmpty() || number1.length()<8){
            showError(number,"رقمك غير صالح!!");
        }
        else if (password1.isEmpty() || password1.length()<8){
            showError(password,"كلمة المرور الخاصة غير كافية!");
        }
        else {
            result=true;
        }
        return result;
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    sendUserDate();
                    Toast.makeText(registre.this, "التسجيل تم ، يرجى التحقق من البريد إرسال لتحميل معلوماتك!" , Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(registre.this, HomeActivity.class));
                }else {
                    Toast.makeText(registre.this, "فشل في التسجيل!!", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void sendUserDate(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        DatabaseReference myRef = firebaseDatabase.getReference("Utilisateurs");
        UserProfile userProfile = new UserProfile(number1, email1, name1,password1,cin1);
        myRef.child(""+firebaseAuth.getUid()).setValue(userProfile);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}