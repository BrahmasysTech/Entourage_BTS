package duxeye.com.entourage.adapter;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.widget.SpannableGridLayoutManager;
import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;
import duxeye.com.entourage.R;
import duxeye.com.entourage.Utility.LoadImage;
import duxeye.com.entourage.model.PhotoGrid;

/**
 * Created by User on 23-07-2016.
 */
public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.MyViewHolder> {
    private static String TAG = PhotoGridAdapter.class.getSimpleName();
    private ArrayList<PhotoGrid> mArrayList;
    private ItemClickListener onItemClickListener;
    private Activity mActivity;
    private final TwoWayView mRecyclerView;
    public interface ItemClickListener {
        void onPhotoClick(PhotoGrid mPhotoGrid,int position);
    }


    public PhotoGridAdapter(Activity mActivity, ArrayList<PhotoGrid> mArrayList,TwoWayView recyclerView, ItemClickListener listener) {
        this.mArrayList = mArrayList;
        this.onItemClickListener = listener;
        this.mActivity = mActivity;
        mRecyclerView = recyclerView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photo_grid, parent, false);
            return new MyViewHolder(v);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

//
//        if (position==0) {
//            StaggeredGridLayoutManager a = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
//
//            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(480,300);
//            layoutParams.setFullSpan(true);
//            holder.itemView.setLayoutParams(layoutParams);
//
//
//
//        }
//        if(position==1)
//        {
//            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(200,200);
//            layoutParams.setFullSpan(true);
//            holder.itemView.setLayoutParams(layoutParams);
//        }
//        if(position==2)
//        {
//            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(200,200);
//            layoutParams.setFullSpan(true);
//            holder.itemView.setLayoutParams(layoutParams);
//        }
        boolean isVertical = (mRecyclerView.getOrientation() == TwoWayLayoutManager.Orientation.VERTICAL);
        final View itemView = holder.itemView;

        final int itemId = position;
        final SpannableGridLayoutManager.LayoutParams lp = (SpannableGridLayoutManager.LayoutParams) itemView.getLayoutParams();

        final int span1 = (itemId == 0 || itemId == 0 ? 2 : 1);
        final int span2 = (itemId == 0 ? 2 : (itemId == 0 ? 0 : 0));

        final int colSpan = (isVertical ? span2 : span1);
        final int rowSpan = (isVertical ? span1 : span2);

        if (lp.rowSpan != rowSpan || lp.colSpan != colSpan) {
            lp.rowSpan = rowSpan;
            lp.colSpan = colSpan;

             itemView.setLayoutParams(lp);

        }

            holder.bind(mArrayList.get(position), position, onItemClickListener);

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView photoImageView;

        public MyViewHolder(View view) {
            super(view);
            photoImageView = (ImageView) view.findViewById(R.id.iv_grid_photo);


        }


        public void bind(final PhotoGrid mPhotoGrid, final int position, final ItemClickListener onItemClickListener) {
            LoadImage.load(mActivity, mPhotoGrid.getImageUrl(), photoImageView);


            photoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onPhotoClick(mPhotoGrid,position);
                 //Log.e("Position:",String.valueOf(position));
                }
            });
        }
    }
   // E/RecyclerView: No adapter attached; skipping layout
}