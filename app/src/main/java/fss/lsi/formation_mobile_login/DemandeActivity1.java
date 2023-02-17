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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DemandeActivity1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    //Variables
    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;

    //insert data
    Button next;
    EditText t1, t2;
    String champ1, champ2;

    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande1);

        //Hooks
        menuIcon = findViewById(R.id.menu_icon_demande1);
        contentView = findViewById(R.id.content_demande1);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawable_layout_demande1);
        navigationView = findViewById(R.id.navigation_view_demande1);
        navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        navigationDrawer();

        //Hookes insert data
        next = findViewById(R.id.next_btn_demande1);
        t1 = findViewById(R.id.d1_et1);
        t2 = findViewById(R.id.d1_et2);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //real time data base
                champ1 = t1.getText().toString().trim();
                champ2 = t2.getText().toString().trim();
                if (champ1.isEmpty()) {
                    showError(t1, "champ not be empty!");
                } else if (champ2.isEmpty()) {
                    showError(t2, "champ not be empty!");
                } else {
                    Intent i = new Intent(DemandeActivity1.this, DemandeActivity2.class);
                    i.putExtra("champ1", champ1);
                    i.putExtra("champ2", champ2);
                    t1.setText("");
                    t2.setText("");
                    startActivity(i);
                }


            }
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
                        startActivity(new Intent(getApplicationContext(), RendezVous.class));
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

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }


}