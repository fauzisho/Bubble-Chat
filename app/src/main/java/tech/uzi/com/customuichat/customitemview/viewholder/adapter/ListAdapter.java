package tech.uzi.com.customuichat.customitemview.viewholder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tech.uzi.com.customuichat.R;

/**
 * Created by fauzi sholichin on 22/11/17.
 * Email : fauzisholichin@gmail.com
 */

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {
    List<ModelList> lists;
    ListListener listListener;

    public ListAdapter(ListListener listListener) {
        this.listListener = listListener;
    }

    public void setLists(List<ModelList> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.bind(lists.get(position),listListener);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public interface ListListener {
        void onClickItem(String name);
    }
}
