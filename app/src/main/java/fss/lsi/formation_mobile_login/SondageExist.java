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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SondageExist extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    //Variables
    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;
    private TextView titleSondage,choix1,choix2,choix3,timeSondage;
    private Button btnSondage;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sondage_exist);

        //Hooks
        menuIcon = findViewById(R.id.menu_icon_sondageExist);
        contentView = findViewById(R.id.content_sondageExist);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawable_layout_sondageExist);
        navigationView = findViewById(R.id.navigation_view_sondageExist);
        navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        titleSondage = findViewById(R.id.titleSondage);
        choix1 = findViewById(R.id.sondagechoix1);
        choix2 = findViewById(R.id.sondagechoix2);
        choix3 = findViewById(R.id.sondagechoix3);
        timeSondage = findViewById(R.id.timeSondage);
        btnSondage = findViewById(R.id.btnSondageExist);

        btnSondage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
                Toast.makeText(getApplicationContext(), "تم ارسال التصويت بنجاح", Toast.LENGTH_SHORT).show();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Sondage").child("-N4JxuvQSvxSwJR0Mrez");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String title1 = snapshot.child("title").getValue().toString();
                String choix11 = snapshot.child("choix1").getValue().toString();
                String choix22 = snapshot.child("choix2").getValue().toString();
                String choix33 = snapshot.child("choix3").getValue().toString();
                String timeS = snapshot.child("Datepicker").getValue().toString();

                titleSondage.setText(title1);
                choix1.setText(choix11);
                choix2.setText(choix22);
                choix3.setText(choix33);
                timeSondage.setText(timeS);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SondageExist.this, "يوجد خطئ !", Toast.LENGTH_SHORT).show();
            }
        });


        navigationDrawer();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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
                        break;
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
            }
        });

    }

    // Navigations Drawers Functions
    private void navigationDrawer() {
        // Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.avis);

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


}