package duxeye.com.entourage.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import duxeye.com.entourage.R;
import duxeye.com.entourage.Utility.Utility;
import duxeye.com.entourage.adapter.PhotoGridAdapter;
import duxeye.com.entourage.constant.Constant;
import duxeye.com.entourage.customViews.CircularProgressBar;
import duxeye.com.entourage.customViews.MyDialog;
import duxeye.com.entourage.customViews.MySpanSizeLookup;
import duxeye.com.entourage.model.PhotoGrid;

/**
 * A simple {@link Fragment} subclass.
 */
public class GridPhotoFragment extends Fragment {
    private static final String TAG = GridPhotoFragment.class.getSimpleName();
    private View mView;
    private ImageView backButton;
    public ArrayList<PhotoGrid> mPhotoGridArrayList;
    private CircularProgressBar mProgressBar;
    private RelativeLayout noImageFoundLayout;
    private GridLayoutManager gridLayoutManagerVertical;

    public static final String CATEGORY_ID = "category_id";
    SharedPreferences category_id;
    SharedPreferences.Editor edit_category_id;
    public GridPhotoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_grid_photos, container, false);
        init();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoFragment();
                edit_category_id.clear();
                edit_category_id.commit();
            }
        });

        category_id = getActivity().getSharedPreferences(CATEGORY_ID,getActivity().MODE_PRIVATE);
        edit_category_id = category_id.edit();
        return mView;
    }

    private void init(){
        ((TextView) mView.findViewById(R.id.grid_photo_header_text)).setText("PHOTO LIST");
        noImageFoundLayout = (RelativeLayout) mView.findViewById(R.id.rl_photo_not_found);
        noImageFoundLayout.setVisibility(View.GONE);
        backButton = (ImageView) mView.findViewById(R.id.iv_back);
        mProgressBar = new CircularProgressBar(getActivity());

        mProgressBar.setCancelable(false);
        getImagesInCategory();
        gridLayoutManagerVertical =new GridLayoutManager(getActivity(),3,LinearLayoutManager.VERTICAL,false);
    }

    private void photoFragment(){
        Fragment fragment = new PhotosFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void photoDetailsFragment(){
        Fragment fragment = new PhotoDetailsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void populatePhotoGrid(){

//
//        RecyclerView photoGridView = (RecyclerView) mView.findViewById(R.id.photo_rv_category);
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),3);
//        photoGridView.setLayoutManager(mLayoutManager);
//        photoGridView.setItemAnimator(new DefaultItemAnimator());
//        photoGridView.setAdapter(new PhotoGridAdapter(getActivity(),mPhotoGridArrayList, new PhotoGridAdapter.ItemClickListener() {
//            @Override
//            public void onPhotoClick(PhotoGrid mPhotoGrid,int position) {
//                Utility.setSharedPreference(getActivity(), Constant.PHOTO_ID,mPhotoGrid.getPhotoId());
//                Utility.setSharedPreference(getActivity(),Constant.CURRENT_PAGE_INDEX,""+position+"");
//                photoDetailsFragment();
//
//            }
//        }));




        RecyclerView photoGridView = (RecyclerView) mView.findViewById(R.id.photo_rv_category);
//        GridLayoutManager manager = new GridLayoutManager(this, 3);
//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return (3 - position % 3);
//            }
//        });
//        recyclerView.setLayoutManager(manager);
        gridLayoutManagerVertical.setSpanSizeLookup(new MySpanSizeLookup(5, 1, 2));
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),3);
        photoGridView.setLayoutManager(gridLayoutManagerVertical);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if(position == 0)
                {
                    return 2;
                }
                else
                {
                    return 1;
                }

            }
        });
            photoGridView.setLayoutManager(mLayoutManager);
            photoGridView.setItemAnimator(new

                            DefaultItemAnimator()

            );
            photoGridView.setAdapter(new

                    PhotoGridAdapter(getActivity(), mPhotoGridArrayList,

                    new PhotoGridAdapter.ItemClickListener()

                    {
                        @Override
                        public void onPhotoClick(PhotoGrid mPhotoGrid, int position) {
                            //Toast.makeText(getActivity(), mPhotoGrid.getPhotoId(), Toast.LENGTH_SHORT).show();
                            Utility.setSharedPreference(getActivity(), Constant.PHOTO_ID, mPhotoGrid.getPhotoId());
                            Utility.setSharedPreference(getActivity(), Constant.CURRENT_PAGE_INDEX, "" + position + "");
                            photoDetailsFragment();

                        }
                    }

            ));

        }



    private void getImagesInCategory(){
        mProgressBar.start();
        mPhotoGridArrayList = new ArrayList<>();
        String url = Constant.GETCATEGORY_PHOTO+ Utility.getSharedPreferences(getActivity(),Constant.YEARBOOKID)+"&category_id="+Utility.getSharedPreferences(getActivity(),Constant.CATEGORY_ID)+"&num_photos="+Utility.getSharedPreferences(getActivity(),Constant.PHOTO_COUNT)+"&credential_key="+Utility.getSharedPreferences(getActivity(),Constant.CREDENTIALKEY);
        //Log.e(TAG,"Url: "+url);

        new AQuery(getActivity()).ajax(url, JSONObject.class, new AjaxCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
//                Log.e(TAG,"Response: "+json);
                if(json != null){
                    try{
                        JSONArray jsonArray = json.getJSONArray("photo_list");
                        //((TextView) mView.findViewById(R.id.grid_photo_header_text)).setText(json.getString("category_name"));
                        for(int i=0; i<jsonArray.length(); i++){

                            edit_category_id.putString("id_"+i,jsonArray.getJSONObject(i).getString("category_photo_id"));
                            edit_category_id.putInt("length", jsonArray.length());
                            mPhotoGridArrayList.add(new PhotoGrid(
                                    jsonArray.getJSONObject(i).getString("category_photo_id"),
                                    jsonArray.getJSONObject(i).getString("name"),
                                    jsonArray.getJSONObject(i).getString("image").replace("[", "%5B").replace("]", "%5D"),
                                    jsonArray.getJSONObject(i).getString("height"),
                                    jsonArray.getJSONObject(i).getString("width")));
                            //Log.w("CATEGORY_ID:", jsonArray.getJSONObject(i).getString("category_photo_id"));
                        }
                        edit_category_id.commit();

                        if(mPhotoGridArrayList.size() > 0) {
                            noImageFoundLayout.setVisibility(View.GONE);
                            populatePhotoGrid();
                        }else{
                            noImageFoundLayout.setVisibility(View.VISIBLE);
                        }

                        mProgressBar.stop();

                    }catch (Exception e){
                        e.printStackTrace();
                        mProgressBar.stop();
                    }

                }else{
                    mProgressBar.stop();
                    if (Utility.isConnectingToInternet()) {
                        MyDialog.iPhone("No response from server\nPlease try again!", getActivity());

                    } else {
                        Utility.showInternetAlert(getActivity());
                    }
                }
            }
        });




    }
}
