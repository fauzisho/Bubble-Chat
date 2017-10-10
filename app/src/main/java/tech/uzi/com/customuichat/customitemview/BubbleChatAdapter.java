package tech.uzi.com.customuichat.customitemview;

import android.content.Context;
import android.view.ViewGroup;

import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.ui.adapter.QiscusChatAdapter;
import com.qiscus.sdk.ui.adapter.viewholder.QiscusBaseMessageViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import tech.uzi.com.customuichat.R;

/**
 * Created by uzi on 10/10/17.
 * Email : fauzisholichin@gmail.com
 */

public class BubbleChatAdapter extends QiscusChatAdapter {
    private static final int TYPE_STICKER_ME = 23323;
    private static final int TYPE_STICKER_OTHER = 23324;

    public BubbleChatAdapter(Context context, boolean groupChat) {
        super(context, groupChat);
    }

    @Override
    protected int getItemViewTypeCustomMessage(QiscusComment qiscusComment, int position) {
        try {
            JSONObject payload = new JSONObject(qiscusComment.getExtraPayload());
            if (payload.optString("type").equals("survey")) {
                return qiscusComment.getSenderEmail().equals(qiscusAccount.getEmail()) ? TYPE_STICKER_ME : TYPE_STICKER_OTHER;
            }
        } catch (JSONException ignored) {

        }
        return super.getItemViewTypeCustomMessage(qiscusComment, position);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        switch (viewType) {
            case TYPE_STICKER_ME:
                return R.layout.item_message_survey;
            case TYPE_STICKER_OTHER:
                return R.layout.item_message_survey;
            default:
                return super.getItemResourceLayout(viewType);
        }
    }

    @Override
    public QiscusBaseMessageViewHolder<QiscusComment> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_STICKER_ME:
            case TYPE_STICKER_OTHER:
                return new BubbleMessageViewHolder(getView(parent, viewType), itemClickListener, longItemClickListener,context);
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }
}
