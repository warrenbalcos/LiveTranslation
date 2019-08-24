package com.wfdb.livetranslation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wfdb.livetranslation.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by warren on 2019-08-24.
 */
public class LocaleViewAdapter extends RecyclerView.Adapter<LocaleViewAdapter.ViewHolder> {

    private ArrayList<LocaleItemData> items;
    private LocaleClickListener localeClickListener;

    public LocaleViewAdapter() {
        this.items = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(items.get(position));
        holder.setClickListener(localeClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<LocaleItemData> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void setLocaleClickListener(LocaleClickListener localeClickListener) {
        this.localeClickListener = localeClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.locale_name_text)
        TextView localeNameTextView;

        @BindView(R.id.locale_code_text)
        TextView localeCodeTextView;

        private LocaleItemData localeItemData;
        private LocaleClickListener clickListener;

        static ViewHolder create(ViewGroup parent) {
            return new ViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.view_holder_locale_list_item, parent, false));
        }

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                if (clickListener != null) {
                    clickListener.onClick(localeItemData);
                }
            });
        }

        void setData(LocaleItemData data) {
            localeItemData = data;
            localeNameTextView.setText(data.getName());
            localeCodeTextView.setText(data.getLocaleCode());
        }

        void setClickListener(LocaleClickListener clickListener) {
            this.clickListener = clickListener;
        }
    }

    public interface LocaleClickListener {
        void onClick(LocaleItemData data);
    }
}
