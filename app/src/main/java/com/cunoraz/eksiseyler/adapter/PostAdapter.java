package com.cunoraz.eksiseyler.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaderFactory;
import com.bumptech.glide.load.model.LazyHeaders;
import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.activity.BookMarkListActivity;
import com.cunoraz.eksiseyler.fragment.ContentFragment;
import com.cunoraz.eksiseyler.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuneytcarikci on 07/11/2016.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private List<Post> postList;

    private ItemClick itemClick;

    private Context context;

    private ContentFragment fragment;

    private BookMarkListActivity activity;

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

    public void updatePosts(ArrayList<Post> posts) {
        this.postList = posts;
        notifyDataSetChanged();

    }

    public void resetAdapter() {
        this.postList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public PostAdapter(ContentFragment fragment, List<Post> postList) {
        this.fragment = fragment;
        this.postList = postList;
    }

    public PostAdapter(BookMarkListActivity activity, List<Post> postList) {
        this.activity = activity;
        this.postList = postList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        context = parent.getContext();

        if (fragment != null)
            itemClick = fragment;
        else if (activity != null)
            itemClick = activity;

        return new MyViewHolder(itemView);
    }

    @Override//new LazyHeaders.Builder().addHeader("referer","https://seyler.eksisozluk.com/")
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Post item = postList.get(position);
        holder.text.setText(item.getName());
        if (item.getImg().equals(""))
            return;
        GlideUrl glideUrl = new GlideUrl(item.getImg(), new LazyHeaders.Builder()
                .addHeader("referer", new LazyHeaderFactory() {
                    @Override
                    public String buildHeader() {
                        return "https://seyler.eksisozluk.com/";
                    }
                })
                .build());

        Glide.with(context).
                load(glideUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .into(holder.image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onClick(view, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public interface ItemClick {
        void onClick(View v, int position);
    }
}