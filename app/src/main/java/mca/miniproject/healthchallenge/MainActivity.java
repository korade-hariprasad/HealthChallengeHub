package mca.miniproject.healthchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.transition.Fade;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Health Challenge Hub");
        getWindow().setEnterTransition(new Fade().setDuration(1000));
        getWindow().setExitTransition(new Fade().setDuration(1000));
        findViewById(R.id.btn_toBadge).setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, BadgeActivity.class));
        });
        findViewById(R.id.btn_toDailyChallenge).setOnClickListener(v->{
            SharedPreferences sharedPreferences = getSharedPreferences("DailyChallenges", MODE_PRIVATE);
            if(sharedPreferences.getInt("id", -1)==-1){ gotoNewPage("Daily"); }else {
                startActivity(new Intent(MainActivity.this, DailyChallengeActivity.class));
            }
        });
        findViewById(R.id.btn_toMonthlyChallenge).setOnClickListener(v->{
            SharedPreferences sharedPreferences = getSharedPreferences("MonthlyChallenges", MODE_PRIVATE);
            if(sharedPreferences.getInt("id", -1)==-1) gotoNewPage("Monthly"); else
            startActivity(new Intent(MainActivity.this, MonthlyChallengeActivity.class));
        });
        findViewById(R.id.btn_toNewChallenge).setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, NewChallengeActivity.class));
        });
    }

    private void gotoNewPage(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No "+s+" Challenges Set\nPlease select a new challenge from the new challenge list").setTitle("Set Challenge")
                .setPositiveButton("To List", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(MainActivity.this, NewChallengeActivity.class));
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}