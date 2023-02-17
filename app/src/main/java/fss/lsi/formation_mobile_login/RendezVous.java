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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class RendezVous extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    //Variables
    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;
    private EditText t1,t2,t3,t4,t5;
    String champ1,champ2,champ3,champ4,champ5;
    Button next;

    private DatabaseReference reference;

    FirebaseFirestore dbroot;
    FirebaseAuth firebaseAuth;

    private int i_renderVous;
    private String i_RV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendez_vous);

        //Hooks
        menuIcon = findViewById(R.id.menu_icon_RendezVous);
        contentView = findViewById(R.id.content_RendezVous);

        //insert data
        next = findViewById(R.id.next_btn_rendez_vous);
        t1 = findViewById(R.id.roundez_vous_et1);
        t2 = findViewById(R.id.roundez_vous_et2);
        t3 = findViewById(R.id.roundez_vous_et3);
        t4 = findViewById(R.id.roundez_vous_et4);
        t5 = findViewById(R.id.roundez_vous_et5);

        reference = FirebaseDatabase.getInstance().getReference().child("CompteurBillet").child("CompteurRV");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                i_RV = snapshot.child("compteur").getValue().toString();
                i_renderVous = Integer.parseInt(i_RV);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RendezVous.this, "يوجد خطئ !", Toast.LENGTH_SHORT).show();
            }
        });

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawable_layout_RendezVous);
        navigationView = findViewById(R.id.navigation_view_RendezVous);
        navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                champ1 = t1.getText().toString().trim();
                champ2 = t2.getText().toString().trim();
                champ3 = t3.getText().toString().trim();
                champ4 = t4.getText().toString().trim();
                champ5 = t5.getText().toString().trim();

                if (champ1.isEmpty()) {
                    showError(t1, "الرجاء ملء الفراغ!");
                } else if (champ2.isEmpty()) {
                    showError(t2, "الرجاء ملء الفراغ!");
                } else if (champ3.isEmpty()) {
                        showError(t3, "الرجاء ملء الفراغ!");
                } else if (champ4.isEmpty()) {
                    showError(t4, "الرجاء ملء الفراغ!");
                }
                else if (champ5.isEmpty()) {
                        showError(t5, "الرجاء ملء الفراغ!");
                } else {
                    i_renderVous++;
                    dbroot = FirebaseFirestore.getInstance();
                    firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference node = db.getReference("Rendez_vous");

                    dataHolderRendezVous obj = new dataHolderRendezVous(champ1, champ2, champ3, champ4, champ5);
                    node.child("Rendez_vous " + i_renderVous).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            t1.setText("");
                            t2.setText("");
                            t3.setText("");
                            t4.setText("");
                            t5.setText("");
                        }
                    });
                    i_RV = String.valueOf(i_renderVous);
                    reference.child("compteur").setValue(i_RV);
                    startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
                    Toast.makeText(getApplicationContext(), "تم الارسال بنجاح", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        });


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
                    break;
            }

            return true;
        });

    }

    // Navigations Drawers Functions
    private void navigationDrawer() {
        // Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.roundez_vous);

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

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

}