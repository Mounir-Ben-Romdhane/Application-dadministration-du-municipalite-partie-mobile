package fss.lsi.formation_mobile_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MiseAjourActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    //Variables
    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;

    private DatabaseReference reference;

    //Blogger
    private RecyclerView postsRv;
    private Button loadMorebtn;
    private EditText searchEt;
    private ImageButton searchBtn;

    private  String url = "";
    private String nextToken = "";
    private boolean isSearch = false;
    private ArrayList<ModolPost> postArrayList;
    private AdapterPost adapterPost;
    private ProgressDialog progressDialog;

    private static final String TAG = "MAIN_TAG";



    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second);


        //Hooks
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.contentt);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawable_layout);
        navigationView = findViewById(R.id.navigation_view);
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
                    break;
                case R.id.roundez_vous:
                    drawerLayout.closeDrawer(GravityCompat.END);
                    startActivity(new Intent(getApplicationContext(),RendezVous.class));
                    break;
            }

            return true;
        });


        postsRv = findViewById(R.id.postsRV);
        loadMorebtn= findViewById(R.id.loadMorebtn);
        searchEt = findViewById(R.id.searchEt);
        searchBtn = findViewById(R.id.searchBtn);

        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("الرجاء الإنتظار....");


        postArrayList = new ArrayList<>();
        postArrayList.clear();
        loadPosts();

        loadMorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String query = searchEt.getText().toString().trim();
                if (TextUtils.isEmpty(query)){
                    loadPosts();
                }
                else {
                    searchPosts(query);
                }
            }
        });


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextToken = "";
                url = "";

                postArrayList= new ArrayList<>();
                postArrayList.clear();


                String query = searchEt.getText().toString().trim();
                if (TextUtils.isEmpty(query)){
                    loadPosts();
                }
                else {
                    searchPosts(query);
                }
            }
        });



    }

    // Navigations Drawers Functions
    private void navigationDrawer() {
        // Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.mise_a_jour);

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

    private void searchPosts(String query) {
        isSearch = false;
        Log.d(TAG,"loadPosts: isSearch: "+isSearch);

        progressDialog.show();



        if (nextToken.equals("")){
            Log.d(TAG,"searchPosts: Next Page token is empty , no more posts");
            url = "https://www.googleapis.com/blogger/v3/blogs/"
                    +Constants.BLOG_ID
                    +"/posts/search?q=" + query
                    +"&key="+Constants.API_KEY;
        }
        else if (nextToken.equals("end")){
            Log.d(TAG, "searchPosts: Next Token is empty/end,no more posts");
            Toast.makeText(this,"No more posts...", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        else {
            Log.d(TAG,"searchPosts: Next token:"+nextToken);
            url = "https://www.googleapis.com/blogger/v3/blogs/"
                    +Constants.BLOG_ID
                    +"/posts/search?q=" + query
                    +"&pageToken=" +nextToken
                    +"&key="+Constants.API_KEY;
        }
        Log.d(TAG,"searchPosts: URL: "+url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d(TAG, "onResponse:" + response);

                try {
                    JSONObject jsonObject  = new JSONObject(response);

                    try {
                        nextToken = jsonObject.getString("nextPageToken");
                        Log.d(TAG,"onResponse: NextPageToken: "+nextToken);
                    }
                    catch (Exception e){
                        Toast.makeText(MiseAjourActivity.this,"وصلت إلى نهاية الصفحة ...", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"onResponse: Reached end of page..."+e.getMessage());
                        nextToken = "end";

                    }
                    JSONArray jsonArray =jsonObject.getJSONArray("items");
                    for (int i=0; i<jsonArray.length(); i++){
                        try {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String id = jsonObject1.getString("id");
                            String title = jsonObject1.getString("title");
                            String content = jsonObject1.getString("content");
                            String published = jsonObject1.getString("published");
                            String updated = jsonObject1.getString("updated");
                            String url = jsonObject1.getString("url");
                            String selfLink = jsonObject1.getString("selfLink");
                            String authorName = jsonObject1.getJSONObject("author").getString("displayName");
                            //String image = jsonObject1.getJSONObject("author").getString("image");


                            ModolPost modolPost = new ModolPost(""+authorName,
                                    ""+content,
                                    ""+id,
                                    ""+published,
                                    ""+selfLink,
                                    ""+title,
                                    ""+updated,
                                    ""+url);

                            postArrayList.add(modolPost);


                        }catch (Exception e){
                            Log.d(TAG,"onResponse: 1: "+e.getMessage());
                            Toast.makeText(MiseAjourActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();


                        }

                    }
                    adapterPost = new AdapterPost(MiseAjourActivity.this,postArrayList);

                    postsRv.setAdapter(adapterPost);
                    progressDialog.dismiss();
                }
                catch (Exception e){
                    Log.d(TAG,"onResponse: 2: "+e.getMessage());
                    Toast.makeText(MiseAjourActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"onErrorResponse:"+error.getMessage());
                Toast.makeText(MiseAjourActivity.this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void loadPosts() {

        isSearch = true;
        Log.d(TAG,"searchPosts: isSearch: "+isSearch);

        progressDialog.show();



        if (nextToken.equals("")){
            Log.d(TAG,"loadPosts: Next Page token is empty , no more posts");
            url = "https://www.googleapis.com/blogger/v3/blogs/"
                    +Constants.BLOG_ID
                    +"/posts?maxResults="+Constants.MAX_POST_RESULTS
                    +"&key="+Constants.API_KEY;
        }
        else if (nextToken.equals("end")){
            Log.d(TAG, "loadPosts: Next Token is empty/end,no more posts");
            Toast.makeText(this,"No more posts...", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }
        else {
            Log.d(TAG,"loadPosts: Next token:"+nextToken);
            url = "https://www.googleapis.com/blogger/v3/blogs/"
                    +Constants.BLOG_ID
                    +"/posts?maxResults="+Constants.MAX_POST_RESULTS
                    +"&pageToken=" +nextToken
                    +"&key="+Constants.API_KEY;
        }
        Log.d(TAG,"loadPosts: URL: "+url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d(TAG, "onResponse:" + response);

                try {
                    JSONObject jsonObject  = new JSONObject(response);

                    try {
                        nextToken = jsonObject.getString("nextPageToken");
                        Log.d(TAG,"onResponse: NextPageToken: "+nextToken);
                    }
                    catch (Exception e){
                        Toast.makeText(MiseAjourActivity.this,"وصلت إلى نهاية الصفحة ...", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"onResponse: Reached end of page..."+e.getMessage());
                        nextToken = "end";

                    }
                    JSONArray jsonArray =jsonObject.getJSONArray("items");
                    for (int i=0; i<jsonArray.length(); i++){
                        try {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String id = jsonObject1.getString("id");
                            String title = jsonObject1.getString("title");
                            String content = jsonObject1.getString("content");
                            String published = jsonObject1.getString("published");
                            String updated = jsonObject1.getString("updated");
                            String url = jsonObject1.getString("url");
                            String selfLink = jsonObject1.getString("selfLink");
                            String authorName = jsonObject1.getJSONObject("author").getString("displayName");
                            //String image = jsonObject1.getJSONObject("author").getString("image");


                            ModolPost modolPost = new ModolPost(""+authorName,
                                    ""+content,
                                    ""+id,
                                    ""+published,
                                    ""+selfLink,
                                    ""+title,
                                    ""+updated,
                                    ""+url);

                            postArrayList.add(modolPost);


                        }catch (Exception e){
                            Log.d(TAG,"onResponse: 1: "+e.getMessage());
                            Toast.makeText(MiseAjourActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();


                        }

                    }
                    adapterPost = new AdapterPost(MiseAjourActivity.this,postArrayList);

                    postsRv.setAdapter(adapterPost);
                    progressDialog.dismiss();
                }
                catch (Exception e){
                    Log.d(TAG,"onResponse: 2: "+e.getMessage());
                    Toast.makeText(MiseAjourActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"onErrorResponse:"+error.getMessage());
                Toast.makeText(MiseAjourActivity.this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}