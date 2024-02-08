package mca.miniproject.healthchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Health Challenge Hub");
        getWindow().setEnterTransition(new Fade().setDuration(1000));
        getWindow().setExitTransition(new Fade().setDuration(1000));

        TextView tv = findViewById(R.id.tv_quote);
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("motivationalQuotes.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            StringBuilder jsonString = new StringBuilder();
            String line;

            // Read the file into a StringBuilder
            while ((line = reader.readLine()) != null) {
                jsonString.append(line).append('\n');
            }

            // Parse the JSON string
            JSONObject jsonObject = new JSONObject(jsonString.toString());
            JSONArray motivationalQuotes = jsonObject.getJSONArray("motivationalQuotes");

            int generatedId = new Random().nextInt(15) + 1;;

            // Find the quote for the generated ID
            for (int i = 0; i < motivationalQuotes.length(); i++) {
                JSONObject quoteObject = motivationalQuotes.getJSONObject(i);
                int id = quoteObject.getInt("id");
                if (id == generatedId) {
                    String quote = quoteObject.getString("quote");
                    // Now 'quote' contains the motivational quote for the generated ID
                    tv.setText(quote);
                    break;
                }
            }
            // Close the reader
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 5000);
    }
}