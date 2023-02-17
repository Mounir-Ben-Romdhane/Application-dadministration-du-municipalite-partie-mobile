package fss.lsi.formation_mobile_login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class Problem3endClass extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    //Variables
    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;

    private DatabaseReference reference;

    //Variables problem
    Button next3endClass;

    //Camera and Upload image
    int SELECT_IMAGE_CODE=1;
    TextView camera,upload;
    RelativeLayout relativeLayout;
    ImageView imageView;

    //insert data in real time
    FirebaseFirestore dbroot;
    FirebaseAuth firebaseAuth;
    String champ1,champ2,champ3,champ4,champ5;
    static private int i_prob=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem3end_class);

        //Hooks
        menuIcon = findViewById(R.id.menu_icon_prob3);
        contentView = findViewById(R.id.content_prob3);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawable_layout_prob3);
        navigationView = findViewById(R.id.navigation_view_prob3);
        navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        //Hookes Camera and Upload image
        camera = findViewById(R.id.cameraProblem);
        upload = findViewById(R.id.uploadProblem);
        relativeLayout = findViewById(R.id.layoutImageProblem);
        imageView = findViewById(R.id.cameraImageProblem);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open_camera,100);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Title"),SELECT_IMAGE_CODE);
            }
        });

        next3endClass = findViewById(R.id.next_3end_btn_prob);



        navigationDrawer();

        next3endClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbroot=FirebaseFirestore.getInstance();
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference node = db.getReference("Problems");
                champ1 = getIntent().getExtras().getString("champ4_prob2");
                champ2 = getIntent().getExtras().getString("champ5_prob2");
                champ3 = getIntent().getExtras().getString("champ1_prob2");
                champ4 = getIntent().getExtras().getString("champ2_prob2");
                champ5 = getIntent().getExtras().getString("champ3_prob2");
                i_prob++;
                dataHolderProblem obj = new dataHolderProblem(champ1,champ2,champ3,champ4,champ5,""+firebaseAuth.getCurrentUser().getEmail().toLowerCase(Locale.ROOT));
                node.child("Problem "+i_prob).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        relativeLayout.setVisibility(View.INVISIBLE);
                        camera.setVisibility(View.VISIBLE);
                        upload.setVisibility(View.VISIBLE);
                    }
                });

                startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
                Toast.makeText(getApplicationContext(), "تم ارسال المشكل بنجاح", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1){
            Uri uri = data.getData();
            imageView.setImageURI(uri);
            relativeLayout.setBackground(imageView.getDrawable());
            imageView.setVisibility(View.INVISIBLE);
            camera.setVisibility(View.INVISIBLE);
            upload.setVisibility(View.INVISIBLE);
        }else {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            relativeLayout.setBackground(imageView.getDrawable());
            imageView.setVisibility(View.INVISIBLE);
            camera.setVisibility(View.INVISIBLE);
            upload.setVisibility(View.INVISIBLE);
        }
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

    public void callPreviosProblem2(View view) {
        startActivity(new Intent(getApplicationContext(),Problem2endClass.class));
    }
}