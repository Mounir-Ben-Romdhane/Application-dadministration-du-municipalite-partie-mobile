package fss.lsi.formation_mobile_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forget_password extends AppCompatActivity {

    private TextView passwordEmail;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

    passwordEmail=(TextView)findViewById(R.id.etEmailResetPassword);
    resetPassword=(Button)findViewById(R.id.btnResetPassword);


        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = passwordEmail.getText().toString().trim();

                if(useremail.equals("")){
                    Toast.makeText(forget_password.this,"Please enter your registered email id",Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(forget_password.this,"Password reset email sent!",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forget_password.this, HomeActivity.class));
                            }else {
                                Toast.makeText(forget_password.this,"error in sending Password reset email!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }

    public void callPreviosActivity(View view) {
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
    }
}