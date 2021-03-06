package tech.uzi.com.customuichat.customitemview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.ui.adapter.QiscusChatAdapter;
import com.qiscus.sdk.ui.fragment.QiscusBaseChatFragment;
import com.qiscus.sdk.ui.fragment.QiscusChatFragment;
import com.qiscus.sdk.ui.view.QiscusAudioRecorderView;
import com.qiscus.sdk.ui.view.QiscusMentionSuggestionView;
import com.qiscus.sdk.ui.view.QiscusRecyclerView;
import com.qiscus.sdk.ui.view.QiscusReplyPreviewView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tech.uzi.com.customuichat.R;

/**
 * Created by uzi on 10/10/17.
 * Email : fauzisholichin@gmail.com
 */

public class BubbleChatFragment extends QiscusBaseChatFragment {
    protected QiscusChatFragment.UserTypingListener userTypingListener;
    private ImageView mAttachButton;
    private LinearLayout mAddPanel;
    private ImageView button_send_survey;
    private ImageView button_send_list;

    public static BubbleChatFragment newInstance(QiscusChatRoom qiscusChatRoom) {
        BubbleChatFragment fragment = new BubbleChatFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CHAT_ROOM_DATA, qiscusChatRoom);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_sticker_chat;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (activity instanceof QiscusChatFragment.UserTypingListener) {
            userTypingListener = (QiscusChatFragment.UserTypingListener) activity;
        }
    }

    @Override
    protected void onLoadView(View view) {
        super.onLoadView(view);
        mAttachButton = (ImageView) view.findViewById(R.id.button_attach);
        mAddPanel = (LinearLayout) view.findViewById(R.id.add_panel);
        button_send_survey = (ImageView) view.findViewById(R.id.button_send_survey);
        button_send_list = (ImageView) view.findViewById(R.id.button_send_list);

        mAttachButton.setOnClickListener(v -> {
            if (mAddPanel.getVisibility() == View.GONE) {
                mAddPanel.startAnimation(animation);
                mAddPanel.setVisibility(View.VISIBLE);
            } else {
                mAddPanel.setVisibility(View.GONE);
            }
        });

        //Simulate send custom survey
        button_send_survey.setOnClickListener(v -> {
            sendSurvey();
            mAddPanel.setVisibility(View.GONE);
        });

        button_send_list.setOnClickListener(v -> {
            sendList();
            mAddPanel.setVisibility(View.GONE);
        });
    }

    private void sendList() {
        //data manual (bisa dari backend)
        String message = "List Qiscus";
        JSONObject payloadList = new JSONObject();
        JSONArray payloadLists = new JSONArray();
        JSONObject payload = new JSONObject();

        try {
            payloadLists.put(payloadList);
            payloadList.put("name", "find me");
            payloadLists.put(payloadList);
            payloadList.put("name", "find me");
            payloadLists.put(payloadList);
            payloadList.put("name", "find me");
            payloadLists.put(payloadList);
            payloadList.put("name", "find me");
            payloadLists.put(payloadList);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            payload.put("data", payloadLists);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        QiscusComment comment = QiscusComment.generateCustomMessage(message, "qiscuslist", payload,
                qiscusChatRoom.getId(), qiscusChatRoom.getLastTopicId());
        sendQiscusComment(comment);
    }

    private void sendSurvey() {
        String message = "Survey Qiscus";
        JSONObject payload = new JSONObject();
        try {
            payload.put("sticker_url", "https://res.cloudinary.com/qiscus/image/upload/fxwzBRPcdz/Bubble-Pup-Yup.gif");
            payload.put("profile_url", "http://res.cloudinary.com/diufvqwbr/image/upload/v1507608923/logo_gb4lzy.png");
            payload.put("title_survey", "Qiscus@ Selles Survey");
            payload.put("date", "2017-10-29");
            payload.put("link_url", "https://www.qiscus.com/");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        QiscusComment comment = QiscusComment.generateCustomMessage(message, "survey", payload,
                qiscusChatRoom.getId(), qiscusChatRoom.getLastTopicId());
        sendQiscusComment(comment);
    }

    @Override
    protected void onApplyChatConfig() {
        super.onApplyChatConfig();
        if (addImageButton != null) {
            addImageButton.setBackground(ContextCompat.getDrawable(Qiscus.getApps(),
                    R.drawable.bt_qiscus_selector_grey));
        }
        if (takeImageButton != null) {
            takeImageButton.setBackground(ContextCompat.getDrawable(Qiscus.getApps(),
                    R.drawable.bt_qiscus_selector_grey));
        }
        if (addFileButton != null) {
            addFileButton.setBackground(ContextCompat.getDrawable(Qiscus.getApps(),
                    R.drawable.bt_qiscus_selector_grey));
        }
        if (recordAudioButton != null) {
            recordAudioButton.setBackground(ContextCompat.getDrawable(Qiscus.getApps(),
                    R.drawable.bt_qiscus_selector_grey));
        }
    }

    @NonNull
    @Override
    protected ViewGroup getRootView(View view) {
        return (ViewGroup) view.findViewById(R.id.root_view);
    }

    @Nullable
    @Override
    protected ViewGroup getEmptyChatHolder(View view) {
        return (ViewGroup) view.findViewById(R.id.empty_chat);
    }

    @NonNull
    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout(View view) {
        return (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
    }

    @NonNull
    @Override
    protected QiscusRecyclerView getMessageRecyclerView(View view) {
        return (QiscusRecyclerView) view.findViewById(R.id.list_message);
    }

    @Nullable
    @Override
    protected ViewGroup getMessageInputPanel(View view) {
        return (ViewGroup) view.findViewById(R.id.input_panel);
    }

    @Nullable
    @Override
    protected ViewGroup getMessageEditTextContainer(View view) {
        return null;
    }

    @NonNull
    @Override
    protected EditText getMessageEditText(View view) {
        return (EditText) view.findViewById(R.id.field_message);
    }

    @NonNull
    @Override
    protected ImageView getSendButton(View view) {
        return (ImageView) view.findViewById(R.id.button_send);
    }

    @NonNull
    @Override
    protected QiscusMentionSuggestionView getMentionSuggestionView(View view) {
        return null;
    }

    @Nullable
    @Override
    protected View getNewMessageButton(View view) {
        return view.findViewById(R.id.button_new_message);
    }

    @NonNull
    @Override
    protected View getLoadMoreProgressBar(View view) {
        return view.findViewById(R.id.progressBar);
    }

    @Nullable
    @Override
    protected ImageView getEmptyChatImageView(View view) {
        return (ImageView) view.findViewById(R.id.empty_chat_icon);
    }

    @Nullable
    @Override
    protected TextView getEmptyChatTitleView(View view) {
        return (TextView) view.findViewById(R.id.empty_chat_title);
    }

    @Nullable
    @Override
    protected TextView getEmptyChatDescView(View view) {
        return (TextView) view.findViewById(R.id.empty_chat_desc);
    }

    @Nullable
    @Override
    protected ViewGroup getAttachmentPanel(View view) {
        return null;
    }

    @Nullable
    @Override
    protected View getAddImageLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getAddImageButton(View view) {
        return (ImageView) view.findViewById(R.id.button_add_image);
    }

    @Nullable
    @Override
    protected View getTakeImageLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getTakeImageButton(View view) {
        return (ImageView) view.findViewById(R.id.button_pick_picture);
    }

    @Nullable
    @Override
    protected View getAddFileLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getAddFileButton(View view) {
        return (ImageView) view.findViewById(R.id.button_add_file);
    }

    @Nullable
    @Override
    protected View getRecordAudioLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getRecordAudioButton(View view) {
        return (ImageView) view.findViewById(R.id.button_add_audio);
    }

    @Nullable
    @Override
    protected View getAddContactLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getAddContactButton(View view) {
        return null;
    }

    @Nullable
    @Override
    protected View getAddLocationLayout(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getAddLocationButton(View view) {
        return null;
    }

    @Nullable
    @Override
    public ImageView getHideAttachmentButton(View view) {
        return null;
    }

    @Nullable
    @Override
    protected ImageView getToggleEmojiButton(View view) {
        return (ImageView) view.findViewById(R.id.button_emoji);
    }

    @Nullable
    @Override
    protected QiscusAudioRecorderView getRecordAudioPanel(View view) {
        return (QiscusAudioRecorderView) view.findViewById(R.id.record_panel);
    }

    @Nullable
    @Override
    protected QiscusReplyPreviewView getReplyPreviewView(View view) {
        return null;
    }

    @Nullable
    @Override
    protected View getGotoBottomButton(View view) {
        return null;
    }

    @Override
    protected QiscusChatAdapter onCreateChatAdapter() {
        return new BubbleChatAdapter(getActivity(), qiscusChatRoom.isGroup());
    }

    @Override
    public void onUserTyping(String user, boolean typing) {
        if (userTypingListener != null) {
            userTypingListener.onUserTyping(user, typing);
        }
    }

    protected void recordAudio() {
        super.recordAudio();
        mAddPanel.setVisibility(View.GONE);
    }
}