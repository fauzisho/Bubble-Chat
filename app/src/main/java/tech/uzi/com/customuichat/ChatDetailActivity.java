package tech.uzi.com.customuichat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.qiscus.nirmana.Nirmana;
import com.qiscus.sdk.ui.view.QiscusCircularImageView;

/**
 * Created by fauzi sholichin on 16/11/17.
 * Email : fauzisholichin@gmail.com
 */

public class ChatDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailchat);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String url = bundle.getString("url");
        QiscusCircularImageView ivUser = (QiscusCircularImageView) findViewById(R.id.ivUser);
        TextView tvName = (TextView) findViewById(R.id.tvUser);
        Nirmana.getInstance().get().load(url)
                .error(R.drawable.ic_qiscus_avatar)
                .placeholder(R.drawable.ic_qiscus_avatar)
                .dontAnimate()
                .into(ivUser);
        tvName.setText(name);
    }
}
