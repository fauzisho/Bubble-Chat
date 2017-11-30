package tech.uzi.com.customuichat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.uzi.com.customuichat.customactivity.CostomActivity;
import tech.uzi.com.customuichat.customitemview.BubbleChatActivity;

/**
 * Created by uzi on 10/10/17.
 * Email : fauzisholichin@gmail.com
 */

public class MainActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private Button mLoginButton;
    protected static final String CHAT_ROOM_DATA = "chat_room_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginButton = (Button) findViewById(R.id.bt_login);
        mLoginButton.setText(Qiscus.hasSetupUser() ? "Logout" : "Login");
    }

    public void loginOrLogout(View view) {
        if (Qiscus.hasSetupUser()) {
            Qiscus.clearUser();
            mLoginButton.setText("Login");
        } else {
            showLoading();
            Qiscus.setUser("fauzi25@gmail.com", "12345678")
                    .withUsername("fauzi")
                    .save()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(qiscusAccount -> {
                        Log.i("MainActivity", "Login with account: " + qiscusAccount);
                        mLoginButton.setText("Logout");
                        dismissLoading();
                    }, throwable -> {
                        throwable.printStackTrace();
                        showError(throwable.getMessage());
                        dismissLoading();
                    });
        }
    }


    public void customSurveyType(View view) {
        if (Qiscus.hasSetupUser()) {
            showLoading();
            Qiscus.buildChatRoomWith("zetra25@gmail.com")
                    .withTitle("zetra")
                    .build()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(qiscusChatRoom -> BubbleChatActivity.generateIntent(this, qiscusChatRoom))
                    .subscribe(intent -> {
                        setupStickerChatConfig();
                        startActivity(intent);
                        dismissLoading();
                    }, throwable -> {
                        throwable.printStackTrace();
                        showError(throwable.getMessage());
                        dismissLoading();
                    });
        } else {
            Toast.makeText(this, "Click Login please", Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityCustom(View view) {
        Qiscus.buildChatRoomWith("zetra25@gmail.com")
                .withTitle("zetra")
                .build()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(qiscusChatRoom -> {
                    Intent intent = new Intent(this, CostomActivity.class);
                    intent.putExtra(CHAT_ROOM_DATA, qiscusChatRoom);
                    startActivity(intent);
                });

    }

    private void revertStickerChatConfig() {
        Qiscus.getChatConfig()
                .setSendButtonIcon(R.drawable.ic_qiscus_send)
                .setShowAttachmentPanelIcon(R.drawable.ic_qiscus_attach);
    }

    private void setupStickerChatConfig() {
        Qiscus.getChatConfig()
                .setSendButtonIcon(R.drawable.ic_qiscus_send_on)
                .setShowAttachmentPanelIcon(R.drawable.ic_qiscus_send_off);
    }

    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Please wait...");
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public void dismissLoading() {
        mProgressDialog.dismiss();
    }
}
