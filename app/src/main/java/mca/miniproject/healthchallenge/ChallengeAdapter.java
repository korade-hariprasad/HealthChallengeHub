package mca.miniproject.healthchallenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder> {

    private List<Challenge> challenges;
    private Context context;

    public ChallengeAdapter(List<Challenge> challenges, Context context) {
        this.challenges = challenges;
        this.context = context;
    }

    public static class ChallengeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewChallengeType;
        TextView textViewChallengeName;
        TextView textViewChallengeDescription;
        Button buttonAction;
        ImageView iv;
        public ChallengeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewChallengeType = itemView.findViewById(R.id.textViewChallengeType);
            textViewChallengeName = itemView.findViewById(R.id.textViewChallengeName);
            textViewChallengeDescription = itemView.findViewById(R.id.textViewChallengeDescription);
            buttonAction = itemView.findViewById(R.id.btnStart);
            iv = itemView.findViewById(R.id.imageViewChallengeIcon);
        }
    }

    @NonNull
    @Override
    public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_challenge, parent, false);
        return new ChallengeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeViewHolder holder, int position) {
        Challenge challenge = challenges.get(position);

        // Bind data to views
        holder.textViewChallengeType.setText(challenge.getType());
        holder.textViewChallengeName.setText(challenge.getName());
        holder.textViewChallengeDescription.setText(challenge.getDescription());
        int drawableResourceId = context.getResources().getIdentifier("c" + challenge.getId(), "drawable", context.getPackageName());
        holder.iv.setImageResource(drawableResourceId);
        holder.buttonAction.setOnClickListener(v -> {handleButtonClick(challenge);});
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    private void handleButtonClick(Challenge challenge) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Action");
        builder.setMessage("Adding a new challenge will overwrite the previous one. Are you sure?");
        builder.setPositiveButton("Yes, add new challenge", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addToFile(challenge);
                Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Added", Snackbar.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No, keep old challenge", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Operation canceled", Snackbar.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addToFile(Challenge challenge) {
        if ("Monthly".equals(challenge.getType())) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MonthlyChallenges", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("id", challenge.getId());
            editor.putString("type", challenge.getType());
            editor.putInt("days", 0);
            editor.putString("name", challenge.getName());
            editor.putString("description", challenge.getDescription());
            editor.apply();
        } else {
            SharedPreferences sharedPreferences = context.getSharedPreferences("DailyChallenges", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("id", challenge.getId());
            editor.putString("type", challenge.getType());
            editor.putString("name", challenge.getName());
            editor.putString("description", challenge.getDescription());
            editor.apply();
        }
    }
}
