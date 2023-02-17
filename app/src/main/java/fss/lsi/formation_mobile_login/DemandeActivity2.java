package fss.lsi.formation_mobile_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DemandeActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    //Variables
    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;

    //insert data
    Button next;
    EditText t3;
    String champ1, champ2, champ3;
    RadioGroup radioGroup;
    RadioButton radioButton;

    FirebaseFirestore dbroot;
    FirebaseAuth firebaseAuth;

    private DatabaseReference reference;

    static private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande2);

        //Hooks
        menuIcon = findViewById(R.id.menu_icon_demande2);
        contentView = findViewById(R.id.content_demande2);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawable_layout_demande2);
        navigationView = findViewById(R.id.navigation_view_demande2);
        navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        //insert data
        next = findViewById(R.id.next_btn_demande2);
        t3 = findViewById(R.id.d2_et1);
        radioGroup = findViewById(R.id.rgdemande);



        navigationDrawer();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                //startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                dbroot = FirebaseFirestore.getInstance();
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference node = db.getReference("Demandes");
                firebaseAuth.getCurrentUser().getEmail().toLowerCase(Locale.ROOT);

                champ1 = getIntent().getExtras().getString("champ1");
                champ2 = getIntent().getExtras().getString("champ2");
                champ3 = t3.getText().toString().trim();
                dataHolderDemande obj = new dataHolderDemande(""+firebaseAuth.getCurrentUser().getEmail().toLowerCase(Locale.ROOT),champ1,
                champ2,radioButton.getText().toString(),champ3);
                node.child("Demande "+i).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        t3.setText("");
                    }
                });

                startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
                Toast.makeText(getApplicationContext(), "تم الارسال بنجاح", Toast.LENGTH_SHORT).show();
                finish();

            }
            //insertData();
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.billet:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        startActivity(new Intent(getApplicationContext(), BilletElectrique.class));
                        break;
                    case R.id.problem:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        startActivity(new Intent(getApplicationContext(), ProblemActivity.class));
                        break;
                    case R.id.nev_home:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
                        break;
                    case R.id.calendrier:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                        break;
                    case R.id.avis_et_questions:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        startActivity(new Intent(getApplicationContext(), AvisQuestionsActivity1.class));
                        break;
                    case R.id.nev_demande:
                        drawerLayout.closeDrawer(GravityCompat.END);
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
                        startActivity(new Intent(getApplicationContext(), GuideActivity.class));
                        break;
                    case R.id.mise_a_jour:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        startActivity(new Intent(getApplicationContext(), MiseAjourActivity.class));
                        break;
                    case R.id.roundez_vous:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        startActivity(new Intent(getApplicationContext(),RendezVous.class));
                        break;
                }

                return true;
            }
        });
    }

    // Navigations Drawers Functions
    private void navigationDrawer() {
        // Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nev_demande);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.END))
                    drawerLayout.closeDrawer(GravityCompat.END);
                else drawerLayout.openDrawer(GravityCompat.END);
            }
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

    public void callPreviosDemande(View view) {
        startActivity(new Intent(getApplicationContext(), DemandeActivity1.class));
    }

    /*
    void insertData() {
        champ1 = getIntent().getExtras().getString("champ1");
        champ2 = getIntent().getExtras().getString("champ2");
        champ3 = t3.getText().toString().trim();
        dataHolder obj = new dataHolder(champ1, champ2, champ3, radioButton.getText().toString());
        dbroot.collection("Users").document(firebaseAuth.getUid()).collection("Problem : ").add(obj).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                t3.setText("");
                i++;
            }
        });
        startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
        Toast.makeText(getApplicationContext(), "تم الارسال بنجاح", Toast.LENGTH_SHORT).show();
        finish();
    }
     */

}