package com.example.mouna.inscriptionandconnection.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mouna.inscriptionandconnection.R;


import java.util.List;

/**
 * Created by Imen on 08/04/2017.
 */

public class SiteAdabter   extends
        RecyclerView.Adapter<SiteAdabter.ViewHolder> {
    private List<Site> mSites;

    private Context mContext;

    SiteAdabter(Context c, List<Site> l)
    {
        mContext=c;
        mSites=l;

    }
    private Context getContext() {
        return mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_row, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Site site = mSites.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(site.name);
        TextView linkView = holder.linkTextView;
        linkView.setText(site.link);
        ImageView iconView =holder.iconView;
        iconView.setImageBitmap(BitmapFactory.decodeByteArray(site.icon, 0, site.icon.length));

    }

    @Override
    public int getItemCount() {
        return mSites.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public ImageView iconView;
        public TextView linkTextView;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.txttitle);
            linkTextView = (TextView) itemView.findViewById(R.id.textLink);
            iconView = (ImageView) itemView.findViewById(R.id.iconImg);
        }
    }


}
