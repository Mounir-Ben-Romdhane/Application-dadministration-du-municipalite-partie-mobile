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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;

public class Problem2endClass extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    //Variables
    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;

    private DatabaseReference reference;

    //Variables problem
    Button next2endClass;
    EditText t1,t2,t3;
    String champ1,champ2,champ3,champ4,champ5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem2end_class);

        //Hooks
        menuIcon = findViewById(R.id.menu_icon_prob2);
        contentView = findViewById(R.id.content_prob2);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawable_layout_prob2);
        navigationView = findViewById(R.id.navigation_view_prob2);
        navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        next2endClass = findViewById(R.id.next_2end_btn_prob);
        t1 = findViewById(R.id.champ1_prob2);
        t2 = findViewById(R.id.champ2_prob2);
        t3 = findViewById(R.id.champ3_prob2);

        navigationDrawer();

        next2endClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                champ1 = t1.getText().toString().trim();
                champ2 = t2.getText().toString().trim();
                champ3 = t3.getText().toString().trim();
                champ4 = getIntent().getExtras().getString("sp_champ1");
                champ5 = getIntent().getExtras().getString("sp_champ2");
                if (champ1.isEmpty()) {
                    showError(t1, "champ not be empty!");
                } else if (champ2.isEmpty()) {
                    showError(t2, "champ not be empty!");
                }else {
                    Intent i = new Intent(Problem2endClass.this, Problem3endClass.class);
                    i.putExtra("champ1_prob2", champ1);
                    i.putExtra("champ2_prob2", champ2);
                    i.putExtra("champ3_prob2", champ3);
                    i.putExtra("champ4_prob2", champ4);
                    i.putExtra("champ5_prob2", champ5);
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                    startActivity(i);
                }
            }
        });

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
            }
        });

    }

    // Navigations Drawers Functions
    private void navigationDrawer() {
        // Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.problem);

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

    public void callPreviosProblem1(View view) {
        startActivity(new Intent(getApplicationContext(),ProblemActivity.class));
    }
}