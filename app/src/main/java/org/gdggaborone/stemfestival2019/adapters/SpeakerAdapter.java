package org.gdggaborone.stemfestival2019.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.gdggaborone.stemfestival2019.R;
import org.gdggaborone.stemfestival2019.models.SpeakerModel;

import java.util.ArrayList;

import pl.charmas.android.tagview.TagView;

public class SpeakerAdapter extends RecyclerView.Adapter<SpeakerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SpeakerModel>modelArrayList;

    public SpeakerAdapter(Context context, ArrayList<SpeakerModel> mList) {
        mContext = context;
        modelArrayList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.speaker_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final SpeakerModel speakerModel = modelArrayList.get(position);

        holder.speakerNameTextView.setText(speakerModel.getName());
        holder.locationTextView.setText(speakerModel.getCountry());
        holder.bioTextView.setText(speakerModel.getBio());

        TagView.Tag[] tags = new TagView.Tag[speakerModel.getTags().size()];

        for (int i = 0; i<speakerModel.getTags().size(); i++){
            tags[i] = new TagView.Tag(speakerModel.getTags().get(i), Color.parseColor("#7e7e7e"));
        }

        holder.tagView.setTags(tags, " ");

        Glide.with(mContext)
                .asBitmap()
                .load(speakerModel.getPhotoUrl())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.profilePictureImageView);

//        String twitterUrl = null;
//        String gplusUrl = null;
//        String linkedinUrl = null;

        for (int i=0; i<speakerModel.getSocials().size(); i++) {
            switch (speakerModel.getSocials().get(i).getIcon()) {
                case "twitter":
                    final int finalI1 = i;
                    holder.twitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Toast.makeText(mContext, "twitter", Toast.LENGTH_SHORT).show();
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(speakerModel.getSocials().get(finalI1).getLink())));
                        }
                    });
                    holder.twitter.setVisibility(View.VISIBLE);
                    break;
                case "linkedin":
                    final int finalI = i;
                    holder.linkedin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Toast.makeText(mContext, speakerModel.getSocials().get(0).getLink(), Toast.LENGTH_SHORT).show();
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(speakerModel.getSocials().get(finalI).getLink())));
                        }
                    });
                    holder.linkedin.setVisibility(View.VISIBLE);
                    break;
                case "gplus":
                    final int finalI2 = i;
                    holder.gplus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Toast.makeText(mContext, "gplus", Toast.LENGTH_SHORT).show();
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(speakerModel.getSocials().get(finalI2).getLink())));
                        }
                    });
                    holder.gplus.setVisibility(View.VISIBLE);
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;
        ImageView profilePictureImageView, twitter, linkedin, gplus;
        TagView tagView;
        TextView speakerNameTextView, locationTextView, bioTextView;

        ViewHolder(View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressBar);

            gplus = itemView.findViewById(R.id.gplus);
            twitter = itemView.findViewById(R.id.twitter);
            linkedin = itemView.findViewById(R.id.linked_in);
            profilePictureImageView = itemView.findViewById(R.id.profilePictureImageView);

            tagView = itemView.findViewById(R.id.tagsTextView);
            speakerNameTextView = itemView.findViewById(R.id.speakerNameTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            bioTextView = itemView.findViewById(R.id.bioTextView);

        }
    }
}
