package mca.miniproject.healthchallenge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Fade;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

public class MonthlyChallengeActivity extends AppCompatActivity {

    private TextView tvChallengeName, tvChallengeDescription, tvChallengeDays;
    private ImageView ivChallenge;
    private Button btnChallengeDone;
    private int id, days;
    private String name, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_challenge);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Monthly Challenge");
        getWindow().setEnterTransition(new Fade().setDuration(1000));
        getWindow().setExitTransition(new Fade().setDuration(1000));

        tvChallengeName = findViewById(R.id.tv_monthlyChallengeName);
        tvChallengeDescription = findViewById(R.id.tv_monthlyChallengeDescription);
        tvChallengeDays = findViewById(R.id.tv_monthlyDays);
        ivChallenge = findViewById(R.id.iv_monthlyChallenge);
        btnChallengeDone = findViewById(R.id.btn_monthlyChallengeDone);
        getChallengeFromSharedPreferences();
        //set imageview
        tvChallengeDescription.setText(description);
        tvChallengeName.setText(name);
        tvChallengeDays.setText("Day "+ days);
        SharedPreferences sp1 = getSharedPreferences("MonthlyChallenges", MODE_PRIVATE);
        int drawableResourceId = getResources().getIdentifier("c" + sp1.getInt("id", -1), "drawable", getPackageName());
        ivChallenge.setImageResource(drawableResourceId);
        btnChallengeDone.setOnClickListener(v->{
            clearMonthlyData();
        });
    }
    private void clearMonthlyData() {
        SharedPreferences sp1 = getSharedPreferences("MonthlyChallenges", MODE_PRIVATE);
        SharedPreferences.Editor spe = sp1.edit();
        int currentDays = sp1.getInt("days", -1);
        spe.putInt("days", currentDays + 1);
        spe.apply();
        SharedPreferences sp = getSharedPreferences("BadgeInfo", MODE_PRIVATE);
        SharedPreferences.Editor spe1 = sp.edit();
        spe1.putInt("daily", sp.getInt("daily", -1)+1);
        spe1.apply();
        tvChallengeDays.setText(String.valueOf("Day "+ (currentDays + 1)));
        if((currentDays + 1)>=30) finishMonthlyChallenge();
    }

    private void getChallengeFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MonthlyChallenges", MODE_PRIVATE);
        id = sharedPreferences.getInt("id", -1);
        name = sharedPreferences.getString("name", "");
        description = sharedPreferences.getString("description", "");
        days = sharedPreferences.getInt("days", 0);
    }

    private void finishMonthlyChallenge() {
        SharedPreferences sp = getSharedPreferences("BadgeInfo", MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putInt("monthly", sp.getInt("monthly", -1)+1);
        spe.putInt("1", 1);
        spe.apply();
        ShowBage(1);
        SharedPreferences sp1 = getSharedPreferences("MonthlyChallenges", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp1.edit();
        editor.clear();
        editor.apply();
        showChallengeCompleted();
    }

    private void showChallengeCompleted() {
        Snackbar.make(findViewById(android.R.id.content), "Challenge Completed", Snackbar.LENGTH_SHORT).show();
    }
    private void ShowBage(int i){
        BadgeDialog badgeDialog = new BadgeDialog(this, i);
        badgeDialog.show();
    }
}
