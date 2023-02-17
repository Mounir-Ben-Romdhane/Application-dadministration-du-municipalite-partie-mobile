package fss.lsi.formation_mobile_login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);
        TextView forgetPassword = (TextView) findViewById(R.id.tvForgetPassword);
        TextView tvRegister = (TextView) findViewById(R.id.tvNewUser);
        remember = findViewById(R.id.remamberMe);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        //FirebaseUser user = firebaseAuth.getCurrentUser();

        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String chechbox = preferences.getString("remember","");
        if (chechbox.equals("true")){
            startActivity(new Intent(HomeActivity.this, PrincipalActivity.class));
        }else if (chechbox.equals("false")){
            Toast.makeText(HomeActivity.this, "الرجاء التسجيل!", Toast.LENGTH_SHORT).show();
        }

        btnLogin.setOnClickListener(v -> {
                if(email.getText().toString().isEmpty() || !email.getText().toString().contains("@")){
                    showError(email,"الإسم غير صالح!");
                }else if (password.getText().toString().isEmpty() || password.getText().toString().length()<8){
                    showError(password,"كلمة المرور الخاصة غير صحيحة!");
            } else {
                validate(email.getText().toString(), password.getText().toString());
            }
        });

        forgetPassword.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, forget_password.class)));

        tvRegister.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, registre.class)));

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    //Toast.makeText(HomeActivity.this, "Checked", Toast.LENGTH_SHORT).show();

                }else if(!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    //Toast.makeText(HomeActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    private void validate(String userName, String userPassword) {

        progressDialog.setMessage("الرجاء الانتظار!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                progressDialog.dismiss();
                checkEmailVerification();
            } else {
                Toast.makeText(HomeActivity.this, "الرجاء التحقق من صحة بياناتك!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }

        });

    }

    private void checkEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        boolean emailflag = firebaseUser.isEmailVerified();

        //startActivity(new Intent(MainActivity.this, SecondActivity.class));
        if (emailflag) {
            finish();
            startActivity(new Intent(HomeActivity.this, PrincipalActivity.class));
        } else {
            Toast.makeText(this, "الرجاء التحقق من بريدك الإلكتروني!", Toast.LENGTH_LONG).show();
            firebaseAuth.signOut();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        startActivity(new Intent(HomeActivity.this, HomeActivity.class));
    }

}