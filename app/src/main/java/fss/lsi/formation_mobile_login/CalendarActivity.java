package fss.lsi.formation_mobile_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;


public class CalendarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    CalendarView calendarView;

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    //Variables
    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //Hooks
        menuIcon = findViewById(R.id.menu_icon_calendar);
        contentView = findViewById(R.id.contentt_calendar);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawable_layout_calndr);
        navigationView = findViewById(R.id.navigation_view_calendar);
        navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);


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


        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Context context = getApplicationContext();
                Toast.makeText(context, "No events Planned for that day", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Navigations Drawers Functions
    private void navigationDrawer() {
        // Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.calendrier);

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