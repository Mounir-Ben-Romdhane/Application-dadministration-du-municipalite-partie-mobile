package fss.lsi.formation_mobile_login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private OnBoardingAdapter onBoardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnBoardingIndicators);
        buttonOnboardingAction = findViewById(R.id.ButtonOnBoardingAction);

        setupOnboardingItems();

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onBoardingAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onboardingViewPager.getCurrentItem()+1 < onBoardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem()+1);
                }else {
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    finish();
                }
            }
        });
    }

    private void setupOnboardingItems() {
        List<OnBoardingItem> onBoardingItems = new ArrayList<>();

        OnBoardingItem itemBillet = new OnBoardingItem();
        itemBillet.setTitle("التذكرة الإلكترونية");
        itemBillet.setDescription("يمكنك حجز مكانك عبر الهاتف و الذهاب في الوقت المحدد وتفادي الصف والتزاحم");
        itemBillet.setImage(R.drawable.billet_electronique);

        OnBoardingItem itemDemande = new OnBoardingItem();
        itemDemande.setTitle("مطالب النفاذ إلى المعلومة");
        itemDemande.setDescription("يمكنك الحصول على وثيقة ادارية سواء كانت في شكل ورقي أو الكتروني اذا لم يتمكن طالب المعلومة");
        itemDemande.setImage(R.drawable.demandedocuments);

        OnBoardingItem itemProblem = new OnBoardingItem();
        itemProblem.setTitle("التبليغ عن مشكل");
        itemProblem.setDescription("يمكنك التبليغ عن مشكل في الحي وإعطاء حلول مناسبة");
        itemProblem.setImage(R.drawable.problemphoto);

        onBoardingItems.add(itemBillet);
        onBoardingItems.add(itemDemande);
        onBoardingItems.add(itemProblem);

        onBoardingAdapter = new OnBoardingAdapter(onBoardingItems);
    }

    private void setupOnboardingIndicators(){
        ImageView[] indicators = new ImageView[onBoardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0 ; i< indicators.length ; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    public void skip(View view){
        startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
        finish();
    }

    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i=0; i<childCount; i++){
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if (i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            }else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if (index == onBoardingAdapter.getItemCount() - 1){
            buttonOnboardingAction.setText("البدء");
        }else {
            buttonOnboardingAction.setText("التالي");
        }
    }
}