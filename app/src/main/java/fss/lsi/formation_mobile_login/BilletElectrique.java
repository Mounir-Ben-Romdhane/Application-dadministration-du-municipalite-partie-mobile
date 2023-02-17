package fss.lsi.formation_mobile_login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BilletElectrique extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    //Variables
    static final float END_SCALE = 0.7f;
    ImageView menuIcon;
    LinearLayout contentView;
    Button button;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private int i,iA,iB,iC,currentA,currentB,currentC;
    private String iS,iAS,iBS,iCS,currentAS,currentBS,currentCS;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billet_electrique);

        button = findViewById(R.id.buttonBillet);
        radioGroup = findViewById(R.id.rgBillet);

        //Hooks
        menuIcon = findViewById(R.id.menu_icon_billet);
        contentView = findViewById(R.id.content_billet);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawable_layout_billet);
        navigationView = findViewById(R.id.navigation_view_billet);
        navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);


        reference = FirebaseDatabase.getInstance().getReference().child("CompteurBillet").child("Compteurs");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                iAS = snapshot.child("CompteurA").getValue().toString();
                iBS = snapshot.child("CompteurB").getValue().toString();
                iCS =  snapshot.child("CompteurC").getValue().toString();
                iS =  snapshot.child("Compteuri").getValue().toString();
                currentAS =  snapshot.child("Current1").getValue().toString();
                currentBS =  snapshot.child("CurrentB").getValue().toString();
                currentCS =  snapshot.child("CurrentC").getValue().toString();

                iA = Integer.parseInt(iAS);
                iB = Integer.parseInt(iBS);
                iC = Integer.parseInt(iCS);
                i = Integer.parseInt(iS);
                currentA = Integer.parseInt(currentAS);
                currentB = Integer.parseInt(currentBS);
                currentC = Integer.parseInt(currentCS);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BilletElectrique.this, "يوجد خطئ !", Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                String nameBillet = radioButton.getText().toString();
                try {
                    createPdf(nameBillet);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        navigationDrawer();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.billet:
                        drawerLayout.closeDrawer(GravityCompat.END);
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
                        startActivity(new Intent(getApplicationContext(),RendezVous.class));
                        break;
                }

                return true;
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createPdf(String name ) throws FileNotFoundException {
        i++;
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "BilletElectrique"+i+".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        iS = String.valueOf(i);
        reference.child("Compteuri").setValue(iS);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        pdfDocument.setDefaultPageSize(PageSize.A6);
        document.setMargins(0,0,0,0);

        Drawable d = getDrawable(R.drawable.haylahbibimage);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();

        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);

        Paragraph visitorTicket = new Paragraph("COMMUNE DE SFAX").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER);
        Paragraph groupe = new Paragraph("Municipalité Hay Lahbib").setBold().setTextAlignment(TextAlignment.CENTER).setFontSize(13);

        //Paragraph numbillet = new Paragraph("0"+i).setBold().setFontSize(17).setTextAlignment(TextAlignment.CENTER);
        //Paragraph nameBillet = new Paragraph(""+name).setBold().setFontSize(15).setTextAlignment(TextAlignment.CENTER);

        Paragraph numbillet;
        if (name.equals("التعريف بالإمضاء")) {
            iA++;
            numbillet = new Paragraph("A0" + iA).setBold().setFontSize(17).setTextAlignment(TextAlignment.CENTER);
            iAS = String.valueOf(iA);
            reference.child("CompteurA").setValue(iAS);
        }else if (name.equals("نسخ مطابقة للاصل")){
            iB++;
            numbillet = new Paragraph("B0" + iB).setBold().setFontSize(17).setTextAlignment(TextAlignment.CENTER);
            iBS = String.valueOf(iB);
            reference.child("CompteurB").setValue(iBS);
        }else {
            iC++;
            numbillet = new Paragraph("C0" + iC).setBold().setFontSize(17).setTextAlignment(TextAlignment.CENTER);
            iCS = String.valueOf(iC);
            reference.child("CompteurC").setValue(iCS);
        }

        Paragraph nameBillet;
        if (name.equals("التعريف بالإمضاء")) {
            nameBillet = new Paragraph("Legalisation des signatures").setFontSize(14).setTextAlignment(TextAlignment.CENTER);
        }else if (name.equals("نسخ مطابقة للاصل")){
            nameBillet = new Paragraph("Copies conformes").setFontSize(14).setTextAlignment(TextAlignment.CENTER);
        }else {
            nameBillet = new Paragraph("Documents de l'etat civil").setFontSize(14).setTextAlignment(TextAlignment.CENTER);
        }

        float[] width = {100f,100f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        table.addCell(new Cell().add(new Paragraph("Date")));
        table.addCell(new Cell().add(new Paragraph(LocalDate.now().format(dateFormatter).toString())));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        table.addCell(new Cell().add(new Paragraph("Temps")));
        table.addCell(new Cell().add(new Paragraph(LocalTime.now().format(timeFormatter).toString())));

        if (name.equals("التعريف بالإمضاء")) {
            table.addCell(new Cell().add(new Paragraph("Numéro actuel")));
            table.addCell(new Cell().add(new Paragraph("A0"+currentAS)));
        table.addCell(new Cell().add(new Paragraph("Temps estimé")));
            int pA = (iA-currentA)*150;
            int pA1 = pA%60;
            int pA2 = pA/60;
            int pA3 = pA2%60;
            pA2 = pA2/60;
            table.addCell(new Cell().add(new Paragraph(pA2+":"+pA3+":"+pA1+" Min")));
        }
        else if (name.equals("نسخ مطابقة للاصل")){
            table.addCell(new Cell().add(new Paragraph("Numéro actuel")));
            table.addCell(new Cell().add(new Paragraph("B0"+currentBS)));
            table.addCell(new Cell().add(new Paragraph("Temps estimé")));
            int pB = (iB-currentB)*150;
            int pB1 = pB%60;
            int pB2 = pB/60;
            int pB3 = pB2%60;
            pB2 = pB2/60;
            table.addCell(new Cell().add(new Paragraph(pB2+":"+pB3+":"+pB1+" Min")));
        }else {
            table.addCell(new Cell().add(new Paragraph("Numéro actuel")));
            table.addCell(new Cell().add(new Paragraph("C0"+currentCS)));
        table.addCell(new Cell().add(new Paragraph("Temps estimé")));
            int pC = (iC-currentC)*150;
            int pC1 = pC%60;
            int pC2 = pC/60;
            int pC3 = pC2%60;
            pC2 = pC2/60;
            table.addCell(new Cell().add(new Paragraph(pC2+":"+pC3+":"+pC1+" Min")));
        }

        BarcodeQRCode qrCode = new BarcodeQRCode(name+"\n"+LocalDate.now().format(dateFormatter)+"\n"+LocalTime.now().format(timeFormatter));
        PdfFormXObject qrCodeObject = qrCode.createFormXObject(ColorConstants.BLACK,pdfDocument);
        Image qrCodeImage = new Image(qrCodeObject).setWidth(80).setHorizontalAlignment(HorizontalAlignment.CENTER);

        document.add(image);
        document.add(visitorTicket);
        document.add(groupe);
        document.add(numbillet);
        document.add(nameBillet);
        document.add(table);
        document.add(qrCodeImage);

        document.close();
        startActivity(new Intent(getApplicationContext(),PrincipalActivity.class));
        Toast.makeText(this,"حفض التذكرة", Toast.LENGTH_LONG).show();




    }

    // Navigations Drawers Functions
    private void navigationDrawer() {
        // Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.billet);

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