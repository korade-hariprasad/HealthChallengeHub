package mca.miniproject.healthchallenge;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class BadgeDialog extends Dialog {
    private int badgeIndex;

    public BadgeDialog(Context context, int badgeIndex) {
        super(context);
        this.badgeIndex = badgeIndex;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_badge);
        ImageView badgeImageView = findViewById(R.id.badgeImageView);
        String imageName = "b" + badgeIndex;
        int resourceId = getContext().getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());
        Drawable badgeDrawable = getContext().getResources().getDrawable(resourceId);
        badgeImageView.setImageDrawable(badgeDrawable);
    }
}
