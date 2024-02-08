package mca.miniproject.healthchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.transition.Fade;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class NewChallengeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewChallenges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_challenge);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Challenge");
        getWindow().setEnterTransition(new Fade().setDuration(1000));
        getWindow().setExitTransition(new Fade().setDuration(1000));

        recyclerViewChallenges = findViewById(R.id.recyclerViewChallenges);
        recyclerViewChallenges.setLayoutManager(new LinearLayoutManager(this));

        // Load challenges.json from JSON (you need to implement this part)
        List<Challenge> challenges = loadChallengesFromJson();
        ChallengeAdapter adapter = new ChallengeAdapter(challenges, this);
        recyclerViewChallenges.setAdapter(adapter);
    }

    private List<Challenge> loadChallengesFromJson() {
        List<Challenge> challenges = new ArrayList<>();

        try {
            // Read challenges.json file
            InputStream inputStream = getAssets().open("challenges.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonString = new String(buffer, StandardCharsets.UTF_8);

            // Parse JSON
            JSONObject jsonChallenges = new JSONObject(jsonString);

            // Parse daily challenges
            JSONArray jsonDailyChallenges = jsonChallenges.getJSONArray("dailyChallenge");
            for (int i = 0; i < jsonDailyChallenges.length(); i++) {
                JSONObject jsonChallenge = jsonDailyChallenges.getJSONObject(i);
                int id = jsonChallenge.getInt("id");
                String name = jsonChallenge.getString("name");
                String description = jsonChallenge.getString("description");
                String type = "Daily"; // You can set a constant value for daily challenges
                Challenge challenge = new Challenge(id, name, description, type);
                challenges.add(challenge);
            }

            // Parse monthly challenges
            JSONArray jsonMonthlyChallenges = jsonChallenges.getJSONArray("monthlyChallenge");
            for (int i = 0; i < jsonMonthlyChallenges.length(); i++) {
                JSONObject jsonChallenge = jsonMonthlyChallenges.getJSONObject(i);
                int id = jsonChallenge.getInt("id");
                String name = jsonChallenge.getString("name");
                String description = jsonChallenge.getString("description");
                String type = "Monthly"; // You can set a constant value for monthly challenges
                Challenge challenge = new Challenge(id, name, description, type);
                challenges.add(challenge);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return challenges;
    }

}