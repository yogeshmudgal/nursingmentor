package com.dakshata.mentor;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Aditya.v on 12-02-2018.
 */

public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ViewHolder> {
    List<ListPojo> listPojosImage;
    Context context;
    View view;

    public ImageRecyclerAdapter(Context context, List<ListPojo> listPojosImage) {
        this.listPojosImage = listPojosImage;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.recycler_image_item,parent,false);
        ImageRecyclerAdapter.ViewHolder myHolder=new ImageRecyclerAdapter.ViewHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListPojo listPojo=listPojosImage.get(position);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        listPojo.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
      //  Glide.with(context).load(stream.toByteArray()).into(holder.imageView);
        holder.imageView.setImageBitmap(listPojo.getImage());

    }

    @Override
    public int getItemCount() {
        return listPojosImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img110);
        }
    }
}
