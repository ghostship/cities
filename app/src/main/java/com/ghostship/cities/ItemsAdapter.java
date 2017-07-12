package com.ghostship.cities;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Created by scottkeller on 5/18/17.
 */

final class ItemsAdapter extends Adapter {

  private List<String> data;
  private OnItemClickListener onItemClickListener;

  @Override
  public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_city, parent, false);
    return new ItemViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    final ItemViewHolder itemViewHolder = ((ItemViewHolder) holder);
    final String itemData = data.get(position);

    itemViewHolder.setTitle(itemData);
    itemViewHolder.setSubtitle("Position: " + position);

    itemViewHolder.itemView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (onItemClickListener != null) {
          onItemClickListener.onItemClick(itemData);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  void setItems(List<String> data) {
    this.data = data;
    notifyDataSetChanged();
  }

  void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  private final class ItemViewHolder extends ViewHolder {

    private TextView titleView;
    private TextView subtitleView;

    private ItemViewHolder(View itemView) {
      super(itemView);
      titleView = (TextView) itemView.findViewById(R.id.title);
      subtitleView = (TextView) itemView.findViewById(R.id.subtitle);
    }

    void setTitle(String title) {
      titleView.setText(title);
    }

    void setSubtitle(String subtitle) {
      subtitleView.setText(subtitle);
    }
  }

  public interface OnItemClickListener {

    void onItemClick(String itemData);
  }
}
