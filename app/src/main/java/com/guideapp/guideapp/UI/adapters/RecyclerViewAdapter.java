/*
 * Copyright (C) 2015 Antonio Leiva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.guideapp.guideapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.guideapp.guideapp.R;
import com.guideapp.guideapp.model.MainMenu;

import java.util.List;


public class RecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
        implements View.OnClickListener {
    private List<MainMenu> items;
    private OnItemClickListener onItemClickListener;
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<MainMenu> items) {
        this.items = items;
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recycler, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        MainMenu item = items.get(position);

        holder.item.setBackgroundResource(item.getColor());
        holder.text.setText(item.getIdText());
        holder.image.setImageResource(item.getImage());

        holder.itemView.setTag(item);
    }

    @Override public int getItemCount() {
        return items.size();
    }

    @Override public void onClick(final View v) {
        onItemClickListener.onItemClick(v, (MainMenu) v.getTag());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public View item;
        public ImageView image;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, MainMenu mainMenu);

    }
}
