package tech.uzi.com.customuichat.customitemview.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qiscus.sdk.data.model.QiscusComment;
import com.qiscus.sdk.ui.adapter.OnItemClickListener;
import com.qiscus.sdk.ui.adapter.OnLongItemClickListener;
import com.qiscus.sdk.ui.adapter.viewholder.QiscusBaseMessageViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import tech.uzi.com.customuichat.R;
import tech.uzi.com.customuichat.customitemview.viewholder.adapter.ListAdapter;
import tech.uzi.com.customuichat.customitemview.viewholder.adapter.ModelList;

/**
 * Created by uzi on 10/10/17.
 * Email : fauzisholichin@gmail.com
 */

public class ListsMessageViewHolder extends QiscusBaseMessageViewHolder<QiscusComment> implements ListAdapter.ListListener {


    private final Context context;
    private RecyclerView rvList;
    private ListAdapter adapter;

    public ListsMessageViewHolder(View itemView, OnItemClickListener itemClickListener, OnLongItemClickListener longItemClickListener, Context context) {
        super(itemView, itemClickListener, longItemClickListener);
        this.context = context;
        rvList = (RecyclerView) itemView.findViewById(R.id.rvList);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adapter = new ListAdapter(this);
        rvList.setAdapter(adapter);
        rvList.setNestedScrollingEnabled(false);
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
        List<ModelList> listList;
        try {
            JSONObject payload = new JSONObject(qiscusComment.getExtraPayload());
            JsonParser jsonParser = new JsonParser();
            JsonObject gsonObject = (JsonObject) jsonParser.parse(payload.toString());

            listList = getDataList(gsonObject, ModelList[].class);
            adapter.setLists(listList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static <T> List<T> getDataList(JsonObject jsonObject, Class<T[]> tClass) {
        String dataList = jsonObject.get("content").getAsJsonObject().get("data").getAsJsonArray().toString();
        return Arrays.asList(getGson().fromJson(dataList, tClass));
    }

    @NonNull
    private static Gson getGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .create();
    }

    @Override
    protected void setUpColor() {
        if (dateView != null) {
            dateView.setTextColor(dateColor);
        }
    }

    @Override
    public void onClickItem(String name) {
        Toast.makeText(context, "" + name, Toast.LENGTH_SHORT).show();
    }
}
