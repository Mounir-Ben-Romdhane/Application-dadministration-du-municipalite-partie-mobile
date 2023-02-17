package fss.lsi.formation_mobile_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AvisQuestionsActivity1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    //Variables
    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;

    Button next;
    RadioGroup radioGroup;
    RadioButton radioButton;

    //spinner
    Spinner sp_parent_avis;
    ArrayList<String> arrayList_parent;
    ArrayAdapter<String> arrayAdapter_parent;
    String avis_champ2;

    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avis_questions1);

        //Hooks
        menuIcon = findViewById(R.id.menu_icon_avisQues1);
        contentView = findViewById(R.id.content_avisQues1);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawable_layout_avisQues1);
        navigationView = findViewById(R.id.navigation_view_avisQues1);
        navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        next = findViewById(R.id.next_btn_avisQues1);
        radioGroup = findViewById(R.id.rgavis);
        sp_parent_avis = (Spinner) findViewById(R.id.sp_parent_avisQues1);


        navigationDrawer();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(getApplicationContext(),AvisQuestionsActivity2.class));
                int radioId = radioGroup.getCheckedRadioButtonId();
                avis_champ2 = sp_parent_avis.getSelectedItem().toString();
                radioButton = findViewById(radioId);
                Intent i = new Intent(AvisQuestionsActivity1.this, AvisQuestionsActivity2.class);
                i.putExtra("champ1", radioButton.getText().toString());
                i.putExtra("avis_champ2",avis_champ2);
                startActivity(i);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.problem:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        startActivity(new Intent(getApplicationContext(),ProblemActivity.class));
                        break;
                    case R.id.nev_home:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        startActivity(new Intent(getApplicationContext(),PrincipalActivity.class));
                        break;
                    case R.id.billet:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        startActivity(new Intent(getApplicationContext(),BilletElectrique.class));
                        break;
                    case R.id.calendrier:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        startActivity(new Intent(getApplicationContext(),CalendarActivity.class));
                        break;
                    case R.id.avis_et_questions:
                        drawerLayout.closeDrawer(GravityCompat.END);
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

        arrayList_parent = new ArrayList<>();
        arrayList_parent.add("الكــاتب العـــام");
        arrayList_parent.add("مكتب الضبط المركزي");
        arrayList_parent.add("مكتب مراقبة التراتيب البلدية");
        arrayList_parent.add("مكتب التنظيم والإعلامية ");
        arrayList_parent.add("القسم الإجتماعي والثقافي");
        arrayList_parent.add("المصلحة الإدارية والمالية");
        arrayList_parent.add("المصلحة الفنيــــة");
        arrayList_parent.add("مصلحة النظافة والمحيط ");

        arrayAdapter_parent = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_parent);

        sp_parent_avis.setAdapter(arrayAdapter_parent);

    }
    // Navigations Drawers Functions
    private void navigationDrawer() {
        // Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.avis_et_questions);

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
