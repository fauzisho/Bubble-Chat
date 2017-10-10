package tech.uzi.com.customuichat.customitemview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.qiscus.nirmana.Nirmana;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusAccount;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.model.QiscusRoomMember;
import com.qiscus.sdk.ui.QiscusBaseChatActivity;
import com.qiscus.sdk.ui.fragment.QiscusBaseChatFragment;
import com.qiscus.sdk.ui.view.QiscusCircularImageView;
import com.qiscus.sdk.util.QiscusDateUtil;

import java.util.Date;

import tech.uzi.com.customuichat.R;

/**
 * Created by uzi on 10/10/17.
 * Email : fauzisholichin@gmail.com
 */

public class BubbleChatActivity extends QiscusBaseChatActivity {
    protected Toolbar toolbar;
    protected TextView tvTitle;
    protected TextView tvSubtitle;
    protected QiscusCircularImageView ivAvatar;

    protected QiscusAccount qiscusAccount;

    public static Intent generateIntent(Context context, QiscusChatRoom qiscusChatRoom) {
        Intent intent = new Intent(context, BubbleChatActivity.class);
        intent.putExtra(CHAT_ROOM_DATA, qiscusChatRoom);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_sticker_chat;
    }

    @Override
    protected void onLoadView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvSubtitle = (TextView) findViewById(R.id.tv_subtitle);
        ivAvatar = (QiscusCircularImageView) findViewById(R.id.profile_picture);
        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
        setSupportActionBar(toolbar);
    }

    @Override
    protected QiscusBaseChatFragment onCreateChatFragment() {
        return BubbleChatFragment.newInstance(qiscusChatRoom);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        qiscusAccount = Qiscus.getQiscusAccount();
        super.onViewReady(savedInstanceState);
    }

    @Override
    protected void binRoomData() {
        super.binRoomData();
        tvTitle.setText(qiscusChatRoom.getName());
        if (!qiscusChatRoom.getSubtitle().isEmpty()) {
            tvSubtitle.setText(qiscusChatRoom.getSubtitle());
            tvSubtitle.setVisibility(qiscusChatRoom.getSubtitle().isEmpty() ? View.GONE : View.VISIBLE);
        }
        showRoomImage();
    }

    protected void showRoomImage() {
        for (QiscusRoomMember member : qiscusChatRoom.getMember()) {
            if (!member.getEmail().equalsIgnoreCase(qiscusAccount.getEmail())) {
                Nirmana.getInstance().get().load(member.getAvatar())
                        .error(R.drawable.ic_qiscus_avatar)
                        .placeholder(R.drawable.ic_qiscus_avatar)
                        .dontAnimate()
                        .into(ivAvatar);
                break;
            }
        }
    }

    @Override
    public void onUserTyping(String user, boolean typing) {
        if (qiscusChatRoom.getSubtitle().isEmpty()) {
            tvSubtitle.setText(typing ? getString(R.string.qiscus_typing) : getString(R.string.qiscus_online));
            tvSubtitle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onUserStatusChanged(String user, boolean online, Date lastActive) {
        if (qiscusChatRoom.getSubtitle().isEmpty()) {
            String last = QiscusDateUtil.getRelativeTimeDiff(lastActive);
            tvSubtitle.setText(online ? getString(R.string.qiscus_online) : getString(R.string.qiscus_last_seen, last));
            tvSubtitle.setVisibility(View.VISIBLE);
        }
    }
}
