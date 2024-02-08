package mca.miniproject.healthchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DailyChallengeActivity extends AppCompatActivity {

    private TextView tvChallengeName, tvChallengeDescription;
    private ImageView ivChallenge;
    private Button btnChallengeDone;
    private int id;
    private String name, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_challenge);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daily Challenge");
        getWindow().setEnterTransition(new Fade().setDuration(1000));
        getWindow().setExitTransition(new Fade().setDuration(1000));

        tvChallengeName = findViewById(R.id.tv_dailyChallengeName);
        tvChallengeDescription = findViewById(R.id.tv_dailyChallengeDescription);
        ivChallenge = findViewById(R.id.iv_dailyChallenge);
        btnChallengeDone = findViewById(R.id.btn_DailyChallengeDone);
        getChallengeFromSharedPreferences();
        //set imageview
        tvChallengeDescription.setText(description);
        tvChallengeName.setText(name);
        SharedPreferences sp1 = getSharedPreferences("DailyChallenges", MODE_PRIVATE);
        int drawableResourceId = getResources().getIdentifier("c" + sp1.getInt("id", -1), "drawable", getPackageName());
        ivChallenge.setImageResource(drawableResourceId);
        btnChallengeDone.setOnClickListener(v->{
            clearDailyData();
            showChallengeCompleted();
        });
    }

    private void clearDailyData() {
        SharedPreferences sp1 = getSharedPreferences("DailyChallenges", MODE_PRIVATE);
        SharedPreferences.Editor spe = sp1.edit();
        spe.clear();
        spe.apply();
        SharedPreferences sp2 = getSharedPreferences("AllData", MODE_PRIVATE);
        SharedPreferences.Editor spe2 = sp2.edit();
        spe2.putInt("DailyCount", (sp2.getInt("DailyCount", 0)+1));
        spe2.apply();
    }

    private void getChallengeFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("DailyChallenges", MODE_PRIVATE);
        id = sharedPreferences.getInt("id", -1);
        name = sharedPreferences.getString("name", "");
        description = sharedPreferences.getString("description", "");
    }
    private void showChallengeCompleted() {
        SharedPreferences sp = getSharedPreferences("BadgeInfo", MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putInt("0", 1);
        //show Starter badge
        ShowBage(0);
        if(id==7){
            spe.putInt("7", 1);
            ShowBage(7);
            //show healthy eater badge
        }
        if(id==2){
            spe.putInt("2", 1);
            ShowBage(2);
            //show hydration hero badge
        }
        spe.putInt("daily", sp.getInt("daily", -1)+1);
        if((sp.getInt("daily", -1)+1)==7){
            spe.putInt("3", 1);
            ShowBage(3);
            //show weekend warrior badge
        }
        spe.apply();
        Snackbar.make(findViewById(android.R.id.content), "Challenge Completed", Snackbar.LENGTH_SHORT).show();
    }

    private void ShowBage(int i){
        BadgeDialog badgeDialog = new BadgeDialog(this, i);
        badgeDialog.show();
    }
}