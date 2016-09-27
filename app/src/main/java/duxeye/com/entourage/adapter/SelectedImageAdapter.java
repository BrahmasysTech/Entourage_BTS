package duxeye.com.entourage.adapter;

import android.app.Activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import duxeye.com.entourage.ApplicationController;
import duxeye.com.entourage.R;
import duxeye.com.entourage.Utility.LoadImage;

/**
 * Created by Hasnain on 24-07-2016.
 */
public class SelectedImageAdapter extends RecyclerView.Adapter<SelectedImageAdapter.ViewHolder> {
    private Activity mActivity;
    private ItemClickListener onItemClickListener;
    private ArrayList selectedImageArrayList;

    public interface ItemClickListener {
        void onDeleteClick(int position);
    }

    public SelectedImageAdapter(Activity mActivity, ArrayList selectedImageArrayList, ItemClickListener onItemClickListener) {
        this.mActivity = mActivity;
        this.selectedImageArrayList = selectedImageArrayList;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selected_gallery_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(selectedImageArrayList.get(position).toString(), position, onItemClickListener);


    }


    @Override
    public int getItemCount() {
        return selectedImageArrayList.size();
    }

    /**
     * Inner class
     *
     * @author tasol
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbImageView, deleteImageView;

        public ViewHolder(View view) {
            super(view);
            thumbImageView = (ImageView) view.findViewById(R.id.iv_thumb);
            deleteImageView = (ImageView) view.findViewById(R.id.iv_delete);

        }

        public void bind(String uri, final int position, final ItemClickListener onItemClickListener) {
            Uri muUri = Uri.fromFile(new File(uri));

//
//            Picasso.with(mActivity)
//                    .load(muUri)
//                    .fit()
//                     .centerInside()
//                    .skipMemoryCache()
//                    .placeholder(R.drawable.no_media)
//                    .error(R.drawable.no_media)
//                    .into(thumbImageView);
//
                     ApplicationController.getImageLoader().displayImage("file://" + uri,thumbImageView, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            thumbImageView.setImageResource(R.drawable.no_media);
                            super.onLoadingStarted(imageUri, view);
                        }
                    });

//            Picasso.with(mActivity)
//                    .load(new File(uri))
//                    .resize(70, 70)
//                    .centerCrop()
//                    .placeholder(R.drawable.no_media)
//                    .error(R.drawable.no_media)
//                    .into(thumbImageView);

            ApplicationController.getImageLoader().displayImage("file://" + uri, thumbImageView, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    thumbImageView.setImageResource(R.drawable.no_media);
                    super.onLoadingStarted(imageUri, view);
                }
            });




            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onDeleteClick(position);
                }
            });

        }
    }

    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }


}