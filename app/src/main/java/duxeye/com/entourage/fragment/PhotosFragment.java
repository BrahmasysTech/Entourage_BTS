package duxeye.com.entourage.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import duxeye.com.entourage.R;
import duxeye.com.entourage.Utility.Utility;
import duxeye.com.entourage.adapter.CategoryAdapter;
import duxeye.com.entourage.callback.Refresh;
import duxeye.com.entourage.constant.Constant;
import duxeye.com.entourage.customViews.CircularProgressBar;
import duxeye.com.entourage.customViews.MyDialog;
import duxeye.com.entourage.model.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotosFragment extends Fragment implements Refresh {
    private static final String TAG = PhotosFragment.class.getSimpleName();
    private View mView;
    private CircularProgressBar mProgressBar;
    private ArrayList<Category> mCategoryArrayList;
    Button create_category;
    public PhotosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_photos, container, false);
        ((TextView) mView.findViewById(R.id.photo_header_text)).setText("CATEGORIES");
        create_category = (Button) mView.findViewById(R.id.create_category);
        mProgressBar = new CircularProgressBar(getActivity());
        mProgressBar.setCancelable(false);
        if (Utility.getBoolean(getActivity(),Constant.ALLOW_CREATE_CATEGORY))
        {
            create_category.setVisibility(View.VISIBLE);
        }

        create_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.getBoolean(getActivity(), Constant.ALLOW_CREATE_CATEGORY)) {
                    final android.app.Dialog mDialog = MyDialog.createCategory("Enter Category Name", getActivity());

                    (mDialog.findViewById(R.id.done)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                            if ((((EditText) mDialog.findViewById(R.id.et_category_name)).getText().toString().trim()).equalsIgnoreCase("")) {
                                MyDialog.iPhone("Category name should not be blank!", getActivity());
                            } else {
                                mProgressBar.start();
                                String url = Constant.CREATE_CATEGORY + ((EditText) mDialog.findViewById(R.id.et_category_name)).getText().toString().trim() + "&credential_key=" + Utility.getSharedPreferences(getActivity(), Constant.CREDENTIALKEY) + "&yearbook_id=" + Utility.getSharedPreferences(getActivity(), Constant.YEARBOOKID);
                                Log.e(TAG, "Url: " + url);

                                new AQuery(getActivity()).ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
                                    @Override
                                    public void callback(String url, JSONObject json, AjaxStatus status) {
                                        Log.e(TAG, "Response: " + json);
                                        if (json != null) {
                                            try {
                                                if (json.getString("status").equalsIgnoreCase("SUCCESS")) {
                                                    MyDialog.iPhone(json.getString("message"), getActivity());
                                                } else {
                                                    MyDialog.iPhone(json.getString("message"), getActivity());
                                                }

                                                mProgressBar.stop();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                mProgressBar.stop();
                                            }

                                        } else {
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
                    });

                    (mDialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    });
                }else {
                    MyDialog.iPhone("You are not allowed to create category!", getActivity());
                }
            }

        });

        getCategory();
        return mView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()){
            getCategory();
        }
    }

    private void getCategory(){
        mProgressBar.start();
        mCategoryArrayList = new ArrayList<>();
        String url = Constant.GETCATEGORY+ Utility.getSharedPreferences(getActivity(),Constant.YEARBOOKID)+"&credential_key="+Utility.getSharedPreferences(getActivity(),Constant.CREDENTIALKEY);
//        Log.e(TAG,"Url: "+url);
        new AQuery(getActivity()).ajax(url, JSONArray.class, new AjaxCallback<JSONArray>(){
            @Override
            public void callback(String url, JSONArray json, AjaxStatus status) {
//                Log.e(TAG,"Response: "+json);
                mCategoryArrayList.clear();
                if(json != null){
                    try{
                        for(int i=0; i<json.length(); i++){

                            mCategoryArrayList.add(new Category(
                                    json.getJSONObject(i).getString("id"),
                                    json.getJSONObject(i).getString("count"),
                                    json.getJSONObject(i).getString("icon"),
                                    json.getJSONObject(i).getString("name")));
                        }


                        Log.e("LIST OF Categories:",String.valueOf(mCategoryArrayList));
                        if(mCategoryArrayList.size() > 0) {
                            populateCategoryRecyclerView();
                        }else{
                            MyDialog.iPhone("Carousel Items not Found\nPlease try again!", getActivity());
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

    private void populateCategoryRecyclerView() {
        RecyclerView mRecyclerView = (RecyclerView) mView.findViewById(R.id.photo_rv_category);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(new CategoryAdapter(mCategoryArrayList, new CategoryAdapter.ItemClickListener() {
            @Override
            public void onCategoryClick(Category mCategory) {
                Utility.setSharedPreference(getActivity(),Constant.CATEGORY_ID,mCategory.getId());
                Utility.setSharedPreference(getActivity(),Constant.PHOTO_COUNT,mCategory.getCount());
                photoGridFragment();
                Log.e("Selected Category:", String.valueOf(mCategory.getId())+"\n"+String.valueOf(mCategory.getCount()));

            }
        }));
    }

    private void photoGridFragment(){
        Fragment fragment = new GridPhotoFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void refreshPhotoList() {
        Log.e(TAG,"Refresh Call");
        getCategory();

    }
}
