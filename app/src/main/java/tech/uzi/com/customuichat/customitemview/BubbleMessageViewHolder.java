package tech.uzi.com.customuichat.customitemview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qiscus.nirmana.Nirmana;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.ui.adapter.OnItemClickListener;
import com.qiscus.sdk.ui.adapter.OnLongItemClickListener;
import com.qiscus.sdk.ui.adapter.viewholder.QiscusBaseMessageViewHolder;
import com.qiscus.sdk.ui.view.QiscusCircularImageView;

import org.json.JSONObject;

import tech.uzi.com.customuichat.R;

/**
 * Created by uzi on 10/10/17.
 * Email : fauzisholichin@gmail.com
 */

public class BubbleMessageViewHolder extends QiscusBaseMessageViewHolder<QiscusComment> {
    private Context context;
    private LinearLayout message;
    private QiscusCircularImageView avatar;
    private TextView dateSurvey;
    private TextView titleView;
    private ImageView imageView;
    private String link;


    public BubbleMessageViewHolder(View itemView, OnItemClickListener itemClickListener, OnLongItemClickListener longItemClickListener, Context context) {
        super(itemView, itemClickListener, longItemClickListener);
        this.context = context;
        imageView = (ImageView) itemView.findViewById(R.id.ic_image);
        titleView = (TextView) itemView.findViewById(R.id.name_survey);
        dateSurvey = (TextView) itemView.findViewById(R.id.date_survey);
        avatar = (QiscusCircularImageView) itemView.findViewById(R.id.avatar);
        message = (LinearLayout) itemView.findViewById(R.id.message);
        message.setOnClickListener(v -> openLink());
    }

    private void openLink() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        context.startActivity(browserIntent);
    }

    @Nullable
    @Override
    protected ImageView getFirstMessageBubbleIndicatorView(View itemView) {
        return null;
    }

    @NonNull
    @Override
    protected View getMessageBubbleView(View itemView) {
        return itemView.findViewById(R.id.message);
    }

    @Nullable
    @Override
    protected TextView getDateView(View itemView) {
        return (TextView) itemView.findViewById(R.id.date);
    }

    @Nullable
    @Override
    protected TextView getTimeView(View itemView) {
        return (TextView) itemView.findViewById(R.id.time);
    }

    @Nullable
    @Override
    protected ImageView getMessageStateIndicatorView(View itemView) {
        return (ImageView) itemView.findViewById(R.id.icon_read);
    }

    @Nullable
    @Override
    protected ImageView getAvatarView(View itemView) {
        return (ImageView) itemView.findViewById(R.id.avatar);
    }

    @Nullable
    @Override
    protected TextView getSenderNameView(View itemView) {
        return null;
    }

    @Override
    protected void showMessage(QiscusComment qiscusComment) {
        String imageuser;
        String imageurl;
        String title;
        String date;

        try {
            JSONObject payload = new JSONObject(qiscusComment.getExtraPayload());
            imageurl = payload.optJSONObject("content").optString("sticker_url");
            imageuser = payload.optJSONObject("content").optString("profile_url");
            title = payload.optJSONObject("content").optString("title_survey");
            date = payload.optJSONObject("content").optString("date");
            link = payload.optJSONObject("content").optString("link_url");
        } catch (Exception e) {
            imageurl = "";
            imageuser = "";
            title = "";
            date = "";
            link = "";
        }

        dateSurvey.setText(date);
        titleView.setText(title);
        Nirmana.getInstance().get()
                .load(imageuser)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(com.qiscus.sdk.R.drawable.qiscus_image_placeholder)
                .error(com.qiscus.sdk.R.drawable.qiscus_image_placeholder)
                .into(avatar);

        Nirmana.getInstance().get()
                .load(imageurl)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(com.qiscus.sdk.R.drawable.qiscus_image_placeholder)
                .error(com.qiscus.sdk.R.drawable.qiscus_image_placeholder)
                .into(imageView);
    }

    @Override
    protected void setUpColor() {
        if (dateView != null) {
            dateView.setTextColor(dateColor);
        }
    }
}
