package fss.lsi.formation_mobile_login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Locale;

public class AvisQuestionsActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    //Variables
    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;

    Button next;
    EditText t1, t2;
    String champ1, champ2, champ3, champ4;
    static int i_avis = 0;
    FirebaseAuth firebaseAuth;

    private DatabaseReference reference;

    SharedPreferences sharedPreferences;

    //Camera and Upload image
    int SELECT_IMAGE_CODE = 1;
    TextView camera, upload;
    RelativeLayout relativeLayout;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avis_questions2);

        //Hooks
        menuIcon = findViewById(R.id.menu_icon_avisQues2);
        contentView = findViewById(R.id.content_avisQues2);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawable_layout_avisQues2);
        navigationView = findViewById(R.id.navigation_view_avisQues2);
        navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        //Hookes Camera and Upload image
        camera = findViewById(R.id.cameraAvis);
        upload = findViewById(R.id.uploadAvis);
        relativeLayout = findViewById(R.id.layoutImageAvis);
        imageView = findViewById(R.id.cameraImage);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open_camera, 100);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Title"), SELECT_IMAGE_CODE);
            }
        });


        next = findViewById(R.id.next_2end_btn_avisQues2);
        t1 = findViewById(R.id.etavis1);
        t2 = findViewById(R.id.etavis2);

        navigationDrawer();
        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("i_avis", i_avis++);
        editor.apply();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int new_i = preferences.getInt("i_avis", 0);
                champ1 = t1.getText().toString().trim();
                champ2 = t2.getText().toString().trim();

                /*
                if (champ1.isEmpty() || champ2.isEmpty()) {
                    Toast.makeText(AvisQuestionsActivity2.this, "please make all the details", Toast.LENGTH_SHORT).show();
                 */
                if (champ1.isEmpty()) {
                    showError(t1, "champ not be empty!");
                }
                else if (champ2.isEmpty()){
                    showError(t2,"champ not be empty!");
                } else {

                    firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference node = db.getReference("AvisEtQuestions");
                    champ3 = getIntent().getExtras().getString("champ1");
                    champ4 = getIntent().getExtras().getString("avis_champ2");

                    dataHolder obj = new dataHolder(" " + firebaseAuth.getCurrentUser().getEmail().toLowerCase(Locale.ROOT), champ1, champ2, champ3, champ4);


                    node.child("AvisEtQuestions" + new_i++).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            t1.setText("");
                            t2.setText("");
                        }
                    });

                    startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
                    Toast.makeText(getApplicationContext(), "تم ارسال المشاركة بنجاح", Toast.LENGTH_SHORT).show();
                    finish();

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
                        break;
                    case R.id.nev_demande:
                        drawerLayout.closeDrawer(GravityCompat.END);
                        startActivity(new Intent(getApplicationContext(), DemandeActivity1.class));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Uri uri = data.getData();
            imageView.setImageURI(uri);
            relativeLayout.setBackground(imageView.getDrawable());
            imageView.setVisibility(View.INVISIBLE);
            camera.setVisibility(View.INVISIBLE);
            upload.setVisibility(View.INVISIBLE);
        } else {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            relativeLayout.setBackground(imageView.getDrawable());
            imageView.setVisibility(View.INVISIBLE);
            camera.setVisibility(View.INVISIBLE);
            upload.setVisibility(View.INVISIBLE);
        }
    }

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

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    public void callPreviosAvis(View view) {
        startActivity(new Intent(getApplicationContext(), AvisQuestionsActivity1.class));
    }
}