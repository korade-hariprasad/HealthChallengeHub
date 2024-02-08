package mca.miniproject.healthchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.transition.Fade;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BadgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Badges");
        getWindow().setEnterTransition(new Fade().setDuration(1000));
        getWindow().setExitTransition(new Fade().setDuration(1000));
        List<Badge> badgeList = loadBadgesFromJson();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BadgeAdapter badgeAdapter = new BadgeAdapter(badgeList, this);
        recyclerView.setAdapter(badgeAdapter);
    }

    private List<Badge> loadBadgesFromJson() {
        List<Badge> Badges = new ArrayList<>();
        try {
            // Read challenges.json file
            InputStream inputStream = getAssets().open("badges.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonString = new String(buffer, StandardCharsets.UTF_8);
            // Parse JSON
            JSONObject jsonBadges = new JSONObject(jsonString);
            JSONArray badges = jsonBadges.getJSONArray("badges");
            for (int i = 0; i < badges.length(); i++) {
                JSONObject jsonBadge = badges.getJSONObject(i);
                int id = jsonBadge.getInt("id");
                String name = jsonBadge.getString("name");
                String description = jsonBadge.getString("description");
                Badge badge = new Badge(id, name, description);
                Badges.add(badge);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Badges;
    }
}