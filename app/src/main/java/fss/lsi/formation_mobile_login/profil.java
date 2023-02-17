package fss.lsi.formation_mobile_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class profil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private EditText pfName,pfNumber,pfEmail,pfCin;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser user;
    private DatabaseReference reference;
    private Button btnOut,btnEdit;

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    //Variables
    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        //Hooks
        menuIcon = findViewById(R.id.menu_icon_profil);
        contentView = findViewById(R.id.contentt_profil);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawable_layout_profil);
        navigationView = findViewById(R.id.navigation_view_profil);
        navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);


        btnOut = findViewById(R.id.btnOut);
        btnEdit = findViewById(R.id.btnEdit);

        pfName = findViewById(R.id.etNameProfil);
        pfEmail = findViewById(R.id.etEmailProfil);
        pfNumber = findViewById(R.id.etPhoneProfil);
        pfCin = findViewById(R.id.etCinProfil);


        firebaseAuth = FirebaseAuth.getInstance() ;
        firebaseDatabase = FirebaseDatabase.getInstance();

        navigationDrawer();

        navigationView.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.billet:
                    drawerLayout.closeDrawer(GravityCompat.END);
                    startActivity(new Intent(getApplicationContext(),BilletElectrique.class));
                    break;
                case R.id.problem:
                    drawerLayout.closeDrawer(GravityCompat.END);
                    startActivity(new Intent(getApplicationContext(),ProblemActivity.class));
                    break;
                case R.id.nev_home:
                    drawerLayout.closeDrawer(GravityCompat.END);
                    startActivity(new Intent(getApplicationContext(),PrincipalActivity.class));
                    break;
                case R.id.calendrier:
                    drawerLayout.closeDrawer(GravityCompat.END);
                    startActivity(new Intent(getApplicationContext(),CalendarActivity.class));
                    break;
                case R.id.avis_et_questions:
                    drawerLayout.closeDrawer(GravityCompat.END);
                    startActivity(new Intent(getApplicationContext(),AvisQuestionsActivity1.class));
                    break;
                case R.id.nev_demande:
                    drawerLayout.closeDrawer(GravityCompat.END);
                    startActivity(new Intent(getApplicationContext(),DemandeActivity1.class));
                    break;
                case R.id.avis:
                    drawerLayout.closeDrawer(GravityCompat.END);
                    if (reference.child("title").get().toString() != null){
                        startActivity(new Intent(getApplicationContext(), SondageExist.class));
                        break;
                    }else {
                        startActivity(new Intent(getApplicationContext(), SondageActivity.class));
                        break;}
                case R.id.guide:
                    drawerLayout.closeDrawer(GravityCompat.END);
                    startActivity(new Intent(getApplicationContext(),GuideActivity.class));
                    break;
                case R.id.mise_a_jour:
                    drawerLayout.closeDrawer(GravityCompat.END);
                    startActivity(new Intent(getApplicationContext(),MiseAjourActivity.class));
                    break;
                case R.id.roundez_vous:
                    drawerLayout.closeDrawer(GravityCompat.END);
                    startActivity(new Intent(getApplicationContext(),RendezVous.class));
                    break;
            }

            return true;
        });



        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Utilisateurs").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fullName = snapshot.child("userName").getValue().toString();
                String email = snapshot.child("userEmail").getValue().toString();
                String number = snapshot.child("userNumber").getValue().toString();
                String cin = snapshot.child("userCin").getValue().toString();


                pfName.setText(fullName);
                pfEmail.setText(email);
                pfNumber.setText(number);
                pfCin.setText(cin);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profil.this, "يوجد خطئ !", Toast.LENGTH_SHORT).show();
            }
        });

        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                Toast.makeText(profil.this, "تم تسجيل الخروج!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String name =  pfName.getText().toString();
               String editEmail =  pfEmail.getText().toString();
               String editNumber = pfNumber.getText().toString();
               String editCin =  pfCin.getText().toString();

                reference.child("userName").setValue(name);
                reference.child("userEmail").setValue(editEmail);
                reference.child("userNumber").setValue(editNumber);
                reference.child("userCin").setValue(editCin);
                Toast.makeText(profil.this, "تم تعديل بياناتك بنجاح!", Toast.LENGTH_SHORT).show();
                pfName.clearFocus();
                pfEmail.clearFocus();
                pfNumber.clearFocus();
                pfCin.clearFocus();
            }
        });

    }

    // Navigations Drawers Functions
    private void navigationDrawer() {
        // Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        menuIcon.setOnClickListener(v -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.END))
                drawerLayout.closeDrawer(GravityCompat.END);
            else drawerLayout.openDrawer(GravityCompat.END);
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.setScrimColor(getResources().getColor(R.color.colorProjet));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else
            super.onBackPressed();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}