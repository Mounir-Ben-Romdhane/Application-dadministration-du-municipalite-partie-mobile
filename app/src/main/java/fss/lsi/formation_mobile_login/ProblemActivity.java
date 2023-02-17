package fss.lsi.formation_mobile_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ProblemActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Spinner sp_parent, sp_child;

    ArrayList<String> arrayList_parent;
    ArrayAdapter<String> arrayAdapter_parent;

    ArrayList<String> arrayList_0,arrayList_1,arrayList_2,arrayList_3,arrayList_4,arrayList_5,arrayList_6,arrayList_7;
    ArrayAdapter<String> arrayAdapter_child;

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private DatabaseReference reference;


    //Variables
    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;

    //Variables problem
    Button next;
    String sp_champ1,sp_champ2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        sp_parent = (Spinner) findViewById(R.id.sp_parent);
        sp_child = (Spinner) findViewById(R.id.sp_child);

        //Hooks
        menuIcon = findViewById(R.id.menu_icon_prob);
        contentView = findViewById(R.id.content_prob);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawable_layout_prob);
        navigationView = findViewById(R.id.navigation_view_prob);
        navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        //Hooks Problem
        next = findViewById(R.id.next_btn_prob);

        navigationDrawer();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_champ1 = sp_parent.getSelectedItem().toString();
                sp_champ2 = sp_child.getSelectedItem().toString();
                Intent i = new Intent(ProblemActivity.this, Problem2endClass.class);
                i.putExtra("sp_champ1", sp_champ1);
                i.putExtra("sp_champ2", sp_champ2);
                startActivity(i);
                sp_parent.setSelection(0);
                sp_child.setSelection(0);
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





        arrayList_parent = new ArrayList<>();
        arrayList_parent.add("الإنارة العمومية");
        arrayList_parent.add("التراتيب العمرانية");
        arrayList_parent.add(" الصحة و البيئة ");
        arrayList_parent.add("الطرقات");
        arrayList_parent.add("المخالفات الإقصادية");
        arrayList_parent.add("المناطق الخضراء و البستنة");
        arrayList_parent.add("النظافة و مقاومة الحشرات");
        arrayList_parent.add("تشكيات عامة");

        arrayAdapter_parent = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_parent);

        sp_parent.setAdapter(arrayAdapter_parent);

        // child spinner procces start

        arrayList_0 = new ArrayList<>();
        arrayList_0.add("نقطة ضوئية معطبة");
        arrayList_0.add("إشتغال الإنارة في النهار");
        arrayList_0.add("إنعدام الإنارة بكامل النهج / الحي");
        arrayList_0.add("سقوط عمود إنارة");
        arrayList_0.add("أسلاك إنارة غير محمية");
        arrayList_0.add("أخرى");


        arrayList_1 = new ArrayList<>();
        arrayList_1.add("أشغال مخالفة للتراتيب العمرانة");
        arrayList_1.add("بناء متداعي للسقوط");
        arrayList_1.add("أشغال بالطريق العام بدون رخصة");
        arrayList_1.add("أشغال بناء بدون رخصة");
        arrayList_1.add("أشغال بناء بدون رخصة");
        arrayList_1.add("الإستلاء على الملك العمومي");
        arrayList_1.add("الإستغال المفرط للرصيف");
        arrayList_1.add("إشغال الطريق العام بمواد بناء");
        arrayList_1.add("أخرى");

        arrayList_2 = new ArrayList<>();
        arrayList_2.add("حرق الفضلات");
        arrayList_2.add("إلقاك مواد خطرة");
        arrayList_2.add("إلقاك الفضلات بمكان غير مخصص");
        arrayList_2.add("عدم إحترام التراتيب الصحية بمحل");
        arrayList_2.add("بيع مواد منتهية الصلوحية");
        arrayList_2.add("ضجيج و إقلاق راحة المتساكنين");
        arrayList_2.add("إنتشار الكلاب السائبة");
        arrayList_2.add("تواجد حيوانات برية");
        arrayList_2.add("أخرى");

        sp_parent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_spinner_item,arrayList_0);
                }
                if (position==1){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_spinner_item,arrayList_1);
                }
                if (position==2){
                    arrayAdapter_child = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_spinner_item,arrayList_2);
                }
                sp_child.setAdapter(arrayAdapter_child);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
}