package mca.miniproject.healthchallenge;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.ViewHolder> {

    private List<Badge> badgeList;
    Context context;

    public BadgeAdapter(List<Badge> badgeList, Context c) {
        this.badgeList = badgeList;
        this.context = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_badge, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Badge badge = badgeList.get(position);
        holder.tvBadgeName.setText(badge.getName());
        holder.tvBadgeDescription.setText(badge.getDescription());

        int id = badge.getId();
        SharedPreferences sp = context.getSharedPreferences("BadgeInfo", Context.MODE_PRIVATE);
        if (sp.getInt(String.valueOf(id),-1)==1) {
            holder.itemView.setBackgroundResource(R.drawable.bg_completed_badge);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.bg_incomplete_badge);
        }

    }

    @Override
    public int getItemCount() {
        return badgeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBadgeName;
        TextView tvBadgeDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBadgeName = itemView.findViewById(R.id.tvBadgeName);
            tvBadgeDescription = itemView.findViewById(R.id.tvBadgeDescription);
        }
    }
}
