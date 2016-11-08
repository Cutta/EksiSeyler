package com.cunoraz.eksiseyler.adapter;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.fragment.ContextFragment;
import com.cunoraz.eksiseyler.model.Post;

import java.util.List;

/**
 * Created by cuneytcarikci on 07/11/2016.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private List<Post> itemList;

    private ItemClick itemClick;

    private Context context;

    private ContextFragment fragment;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        CardView cardView;
        ImageView image;

        MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.row_image);
            text = (TextView) view.findViewById(R.id.row_title);
            cardView = (CardView) view.findViewById(R.id.item_root);
        }
    }


    public PostAdapter(ContextFragment fragment,List<Post> itemList) {
        this.fragment = fragment;
        this.itemList = itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        context = parent.getContext();
        itemClick = fragment;

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Post item = itemList.get(position);
        holder.text.setText(item.getName());
        Glide.with(context).load(item.getImg()).into(holder.image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onClick(view, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface ItemClick {
        void onClick(View v, int position);
    }
}