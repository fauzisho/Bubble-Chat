package tech.uzi.com.customuichat.customitemview.viewholder.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tech.uzi.com.customuichat.R;

/**
 * Created by fauzi sholichin on 22/11/17.
 * Email : fauzisholichin@gmail.com
 */

public class ListViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvName;
    private final CardView cvCard;

    public ListViewHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
        cvCard = (CardView) itemView.findViewById(R.id.cv_card);
    }

    public void bind(ModelList modelList, ListAdapter.ListListener listListener) {
        tvName.setText(modelList.getName());
        cvCard.setOnClickListener(v -> listListener.onClickItem(modelList.getName()));
    }
}
