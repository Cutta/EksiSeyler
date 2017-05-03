package com.cunoraz.eksiseyler.ui.content;

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
import com.cunoraz.eksiseyler.model.rest.entity.Post;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cuneytcarikci on 07/11/2016.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> mPosts;
    private PostAdapterCallback mCallback;

    public PostAdapter(PostAdapterCallback callback, List<Post> posts) {
        this.mPosts = posts;
        this.mCallback = callback;
    }

    public void updatePosts(ArrayList<Post> posts) {
        this.mPosts = posts;
        notifyDataSetChanged();

    }

    public void resetAdapter() {
        this.mPosts = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {

        final Post post = mPosts.get(position);

        holder.text.setText(post.getName());

        if (post.getImg().equals(""))
            holder.image.setImageResource(R.drawable.placeholder);
        else {
            GlideUrl glideUrl = new GlideUrl(post.getImg(), new LazyHeaders.Builder()
                    .addHeader("referer", new LazyHeaderFactory() {
                        @Override
                        public String buildHeader() {
                            return "https://seyler.eksisozluk.com/";
                        }
                    })
                    .build());

            Glide.with(holder.itemView.getContext()).
                    load(glideUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(holder.image);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCallback != null)
                    mCallback.onClickPost(holder, post);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.row_title)
        public TextView text;

        @BindView(R.id.item_root)
        public CardView cardView;

        @BindView(R.id.row_image)
        public ImageView image;

        PostViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public interface PostAdapterCallback {
        void onClickPost(PostViewHolder viewHolder, Post post);
    }

}