package duxeye.com.entourage.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;
import org.xml.sax.Parser;

import java.util.ArrayList;

import duxeye.com.entourage.R;
import duxeye.com.entourage.Utility.LoadImage;
import duxeye.com.entourage.Utility.Utility;
import duxeye.com.entourage.constant.Constant;
import duxeye.com.entourage.customViews.CircularProgressBar;
import duxeye.com.entourage.customViews.MyDialog;
import duxeye.com.entourage.customViews.ZoomLayout;

/**
 * A simple {@link Fragment} subclass.
 */
//public class PhotoDetailsFragment extends Fragment {
//    private static final String TAG = PhotoDetailsFragment.class.getSimpleName();
//    private View mView;
//    private ViewPager mViewPager;
//    private PhotoDetailPagerAdapter mPagerAdapter;
//    private int lastPosition = 0;
//    public static final String CATEGORY_ID = "category_id";
//    SharedPreferences category_id;
//    SharedPreferences.Editor edit_category_id;
//    public ArrayList<String> mPhotoGridArrayList;
//    String Photo_Id;
//    boolean chageImage = false;
//    int setPos,total;
//    String next_photo_id,prior_photo_id,category_photo_id;
//    //    private ImageView penImageView;
////    private ImageViewTouch bigImageView;
////    private TextView fromTextView, imageNameTextView, sizeTextView, dateTextView;
////    private EditText captionEditText;
////    private String next_photo_id, prior_photo_id, category_photo_id;
//    private CircularProgressBar mProgressBar;
////    private RelativeLayout mPhotoLayout;
////    private ScaleGestureDetector mGestureDetector;
////    private Matrix matrix = new Matrix();
////    private float scale = 1f;
//
//
//
//    private int scroll = 0;
//    private int currentPage = 0;
//    private float currentScale = 0;
//
//    public PhotoDetailsFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mView = inflater.inflate(R.layout.fragment_photo_detail, container, false);
//
//        init();
//        total = Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.PHOTO_COUNT));
//        currentPage = Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.CURRENT_PAGE_INDEX));
//        Log.e("Current",String.valueOf(currentPage));
//        lastPosition = Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.CURRENT_PAGE_INDEX))-1;
//
//        mPhotoGridArrayList = new ArrayList<>();
//        category_id = getActivity().getSharedPreferences(CATEGORY_ID, getActivity().MODE_PRIVATE);
//        edit_category_id = category_id.edit();
//        mPhotoGridArrayList.clear();
//
//        for (int i =0;i<category_id.getInt("length",0);i++)
//        {
//            mPhotoGridArrayList.add(category_id.getString("id_"+i,"null"));
//
//        }
//        Log.w("ARRAY LIST:",String.valueOf(mPhotoGridArrayList));
//        for (int j=0;j<category_id.getInt("length",0);j++)
//        {
//            if (Utility.getSharedPreferences(getActivity(), Constant.CURRENT_PAGE_INDEX).equals(String.valueOf(mPhotoGridArrayList.indexOf(mPhotoGridArrayList.get(j))))) {
//                Photo_Id = mPhotoGridArrayList.get(j);
//            }
//        }
//        mView.findViewById(R.id.iv_pd_back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                edit_category_id.clear();
////                edit_category_id.commit();
////
////                mPhotoGridArrayList.clear();
//               // Utility.setSharedPreference(getActivity(), Constant.PHOTO_ID, "");
//           //     Utility.setSharedPreference(getActivity(), Constant.CURRENT_PAGE_INDEX, "");
//                photoGridFragment();
//
//
//
//            }
//        });
//
//        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
////                if (position == total - 1) {
////                    chageImage = true;
////                    setPos = 1;
////                } else if (position == 0) {
////                    chageImage = true;
////                    setPos = total - 2;
////                } else {
////                    chageImage = false;
////                }
//
//                if (currentPage != position) {
//                    if (lastPosition > position) {//User Move to left
//
//
//                        if (Utility.getSharedPreferences(getActivity(), Constant.PRIOR_PHOTO_ID).equalsIgnoreCase("")) {
//                            //Toast.makeText(getActivity(), "You are at 1st photo", Toast.LENGTH_SHORT).show();
//
//
//                        } else {
//                            for (int i = 0; i < total; i++) {
//                                if (position == mPhotoGridArrayList.indexOf(mPhotoGridArrayList.get(i))) {
//                                    Utility.setSharedPreference(getActivity(), Constant.PHOTO_ID, mPhotoGridArrayList.get(i));
//                                }
//                            }
//
//                            //Photo_Id =Utility.getSharedPreferences(getActivity(), Constant.PRIOR_PHOTO_ID);
//                            //      Log.e("scroll", "left" + Utility.getSharedPreferences(getActivity(), Constant.PRIOR_PHOTO_ID));
//                            lastPosition = position;
//                        }
//
//                        //   Utility.setSharedPreference(getActivity(), Constant.PHOTO_ID, Utility.getSharedPreferences(getActivity(), Constant.PRIOR_PHOTO_ID));
//
//                    } else if (lastPosition < position) { //User Move to right
//
//
//                        if (Utility.getSharedPreferences(getActivity(), Constant.NEXT_PHOTO_ID).equalsIgnoreCase("")) {
//                            //  Toast.makeText(getActivity(), "No more next", Toast.LENGTH_SHORT).show();
//
//                        } else {
//
//                            for (int i = 0; i < total; i++) {
//                                if (position == mPhotoGridArrayList.indexOf(mPhotoGridArrayList.get(i))) {
//                                    Utility.setSharedPreference(getActivity(), Constant.PHOTO_ID, mPhotoGridArrayList.get(i));
//                                }
//                            }
//                            lastPosition = position;
//                            //Utility.setSharedPreference(getActivity(), Constant.PHOTO_ID, Utility.getSharedPreferences(getActivity(), Constant.NEXT_PHOTO_ID));
//                            //   Photo_Id = Utility.getSharedPreferences(getActivity(), Constant.NEXT_PHOTO_ID);
//                            Log.e("scroll", "right" + Utility.getSharedPreferences(getActivity(), Constant.NEXT_PHOTO_ID));
//                        }
//
//                        //  Utility.setSharedPreference(getActivity(), Constant.PHOTO_ID, Utility.getSharedPreferences(getActivity(), Constant.NEXT_PHOTO_ID));
//
//                    }
//                } else {
//                    for (int i = 0; i < total - 1; i++) {
//                        if (position == mPhotoGridArrayList.indexOf(mPhotoGridArrayList.get(i))) {
//                            Utility.setSharedPreference(getActivity(), Constant.PHOTO_ID, mPhotoGridArrayList.get(i));
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
////                if (state == ViewPager.SCROLL_STATE_IDLE && chageImage) {
////                    mViewPager.setCurrentItem(setPos, false);
////
////                }
////
//            }
//        });
//        mViewPager.setCurrentItem(Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.CURRENT_PAGE_INDEX)));
//
//
//        return mView;
//    }
//
//    private void init() {
//        ((TextView) mView.findViewById(R.id.pd_header_text)).setText("PHOTO DETAILS");
////        backButton = (ImageView) mView.findViewById(R.id.iv_pd_back);
////        bigImageView = (ImageViewTouch) mView.findViewById(R.id.iv_big_image);
////        penImageView = (ImageView) mView.findViewById(R.id.iv_pen);
//
////        imageNameTextView = (TextView) mView.findViewById(R.id.tv_file_name);
////        fromTextView = (TextView) mView.findViewById(R.id.tv_from);
////        sizeTextView = (TextView) mView.findViewById(R.id.tv_size);
////        dateTextView = (TextView) mView.findViewById(R.id.tv_date);
//
////        captionEditText = (EditText) mView.findViewById(R.id.et_caption);
////        captionEditText.setTextColor(getActivity().getResources().getColor(R.color.textColor));
//
//        mProgressBar = new CircularProgressBar(getActivity());
//        mProgressBar.setCancelable(false);
//
////        mPhotoLayout = (RelativeLayout) mView.findViewById(R.id.relativelayout);
//        mViewPager = (ViewPager) mView.findViewById(R.id.vp_pd_pager);
//        mPagerAdapter = new PhotoDetailPagerAdapter(getActivity().getSupportFragmentManager());
//        mViewPager.setAdapter(mPagerAdapter);
//        mViewPager.setCurrentItem(Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.CURRENT_PAGE_INDEX)));
////        getImageDetails();
//    }
//
//    private void photoGridFragment() {
//        Fragment fragment = new GridPhotoFragment();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.content_frame, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }
//
//
//
//    public class PhotoDetailPagerAdapter extends FragmentPagerAdapter {
//
//        public PhotoDetailPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//
//        //  Log.e("PHOTO_ID: ", String.valueOf(Utility.getSharedPreferences(getActivity(), Constant.PHOTO_ID)));
//
//
////            return getUIData(Utility.getSharedPreferences(getActivity(), Constant.PRIOR_PHOTO_ID), position);
//            return new PhotoDetailRowFragment();
//
//        }
//
//        @Override
//        public int getCount() {
//
//
//         //   -Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.CURRENT_PAGE_INDEX))+2
//
//            return Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.PHOTO_COUNT));
//        }
//
//    }
//
//
////    public void getImageAndText(String photoId) {
////
////        String url = Constant.PHOTO_DETAILS + Utility.getSharedPreferences(getActivity(), Constant.PHOTO_ID) + "&yearbook_id=" + Utility.getSharedPreferences(getActivity(), Constant.YEARBOOKID) + "&credential_key=" + Utility.getSharedPreferences(getActivity(), Constant.CREDENTIALKEY);
//////        Log.e(TAG, "Url: " + url);
////        new AQuery(getActivity()).ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
////            @Override
////            public void callback(String url, JSONObject json, AjaxStatus status) {
//////                Log.e(TAG, "Response: " + json);
////                if (json != null) {
////                    try {
////                        json.getString("photo_file") ;
////                        json.getString("uploaded_by") ;
////                        json.getString("photo_caption") ;
////                        json.getString("exphoto_width");
////                        json.getString("exphoto_height") ;
////                        json.getString("create_date");
////                        next_photo_id = json.getString("next_photo_id");
////                        prior_photo_id = json.getString("prior_photo_id");
////                        category_photo_id = json.getString("category_photo_id");
////                        //LoadImage.load(getActivity(), json.getString("image").replace("[", "%5B").replace("]", "%5D"), bigImageView);
////
////
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                    Utility.setSharedPreference(getActivity(), Constant.NEXT_PHOTO_ID,category_photo_id);
////                    Utility.setSharedPreference(getActivity(), Constant.NEXT_PHOTO_ID, next_photo_id);
////                    Utility.setSharedPreference(getActivity(), Constant.PRIOR_PHOTO_ID, prior_photo_id);
////                    Log.e("NEXT:", next_photo_id);
////                    //  Log.e("Photo_id",category_photo_id);
////                    //   Log.e("BEFORE:",prior_photo_id);
////
////                } else {
////                    if (Utility.isConnectingToInternet()) {
////                        MyDialog.iPhone("No response from server\nPlease try again!", getActivity());
////
////                    } else {
////                        Utility.showInternetAlert(getActivity());
////                    }
////                }
////            }
////        });
////
////
////    }
//
//}



public class PhotoDetailsFragment extends Fragment  {

    ImageView backButton;
    ViewPager mViewPager;
    private View mView;
    ImageFragmentPagerAdapter mPagerAdapter;
    SharedPreferences category_id;
    SharedPreferences.Editor edit_category_id;
    public static final String CATEGORY_ID = "category_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         mView = inflater.inflate(R.layout.fragment_photo_detail, container, false);
        category_id = getActivity().getSharedPreferences(CATEGORY_ID, getActivity().MODE_PRIVATE);
        edit_category_id = category_id.edit();
        ((TextView) mView.findViewById(R.id.pd_header_text)).setText("PHOTO DETAILS");

        backButton = (ImageView) mView.findViewById(R.id.iv_pd_back);
         mViewPager = (ViewPager) mView.findViewById(R.id.vp_pd_pager);
         mPagerAdapter = new ImageFragmentPagerAdapter(getActivity().getSupportFragmentManager());
         mViewPager.setAdapter(mPagerAdapter);
         mViewPager.setCurrentItem(Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.CURRENT_PAGE_INDEX)));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edit_category_id.clear();
                edit_category_id.commit();
                photoGridFragment();

            }
        });
        return mView;
    }
    private void photoGridFragment() {
        Fragment fragment = new GridPhotoFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public class ImageFragmentPagerAdapter extends FragmentPagerAdapter {
        public ImageFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.PHOTO_COUNT));
        }

        @Override
        public Fragment getItem(int position) {
            PhotoDetailRowFragment fragment = new PhotoDetailRowFragment();
            return SwipeFragment.newInstance(position);
        }
    }

    public static class SwipeFragment extends Fragment {
        public ArrayList<String> mPhotoGridArrayList;
        SharedPreferences category_id;
        SharedPreferences.Editor edit_category_id;
        public static final String CATEGORY_ID = "category_id";
        String Photo_Id;
        private static final String TAG = PhotoDetailRowFragment.class.getSimpleName();
        private ImageView penImageView;
        private ImageView  bigImageView;
        private TextView fromTextView, imageNameTextView, sizeTextView, dateTextView;
        private EditText captionEditText;
        private View swipeView;
        ZoomLayout zoomLayout;
        private String next_photo_id, prior_photo_id, category_photo_id;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
             swipeView = inflater.inflate(R.layout.row_photo_details, container, false);
            init();

            penImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    captionEditText.setFocusable(true);
                    captionEditText.setFocusableInTouchMode(true);
                    captionEditText.setClickable(true);

                    ((InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE))
                            .toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    captionEditText.requestFocus();

                }
            });

            Bundle bundle = getArguments();
            int position = bundle.getInt("position");
            mPhotoGridArrayList = new ArrayList<>();
            mPhotoGridArrayList = new ArrayList<>();
            category_id = getActivity().getSharedPreferences(CATEGORY_ID, getActivity().MODE_PRIVATE);
            edit_category_id = category_id.edit();
       // mPhotoGridArrayList.clear();

        for (int i =0;i<category_id.getInt("length",0);i++)
        {
            mPhotoGridArrayList.add(category_id.getString("id_"+i,"null"));

        }
        Log.w("ARRAY LIST:",String.valueOf(mPhotoGridArrayList));
        for (int j=0;j<category_id.getInt("length",0);j++)
        {
            if (position==(mPhotoGridArrayList.indexOf(mPhotoGridArrayList.get(j)))) {
                Photo_Id = mPhotoGridArrayList.get(j);
            }
        }
            if (!Photo_Id.equals("")) {
                Log.e("PhotoId",Photo_Id);
                String url = Constant.PHOTO_DETAILS + Photo_Id + "&yearbook_id=" + Utility.getSharedPreferences(getActivity(), Constant.YEARBOOKID) + "&credential_key=" + Utility.getSharedPreferences(getActivity(), Constant.CREDENTIALKEY);
//        Log.e(TAG, "Url: " + url);
                new AQuery(getActivity()).ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject json, AjaxStatus status) {
//                Log.e(TAG, "Response: " + json);
                        if (json != null) {
                            try {
                                imageNameTextView.setText(json.getString("photo_file"));
                                fromTextView.setText(json.getString("uploaded_by"));
                                captionEditText.setText(json.getString("photo_caption"));
                                sizeTextView.setText(json.getString("exphoto_width") + " * " + json.getString("exphoto_height"));
                                dateTextView.setText(json.getString("create_date"));
                                next_photo_id = json.getString("next_photo_id");
                                prior_photo_id = json.getString("prior_photo_id");
                                category_photo_id = json.getString("category_photo_id");
                                LoadImage.load(getActivity(), json.getString("image").replace("[", "%5B").replace("]", "%5D"), bigImageView);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //  Utility.setSharedPreference(getActivity(), Constant.NEXT_PHOTO_ID, category_photo_id);
//                        Utility.setSharedPreference(getActivity(), Constant.NEXT_PHOTO_ID, next_photo_id);
//                        Utility.setSharedPreference(getActivity(), Constant.PRIOR_PHOTO_ID, prior_photo_id);
                            //Log.e("NEXT:", next_photo_id);
                            //  Log.e("Photo_id",category_photo_id);
                            //   Log.e("BEFORE:",prior_photo_id);

                        } else {
                            if (Utility.isConnectingToInternet()) {
                                MyDialog.iPhone("No response from server\nPlease try again!", getActivity());

                            } else {
                                Utility.showInternetAlert(getActivity());
                            }
                        }
                    }
                });

            }else
            {
                if (Utility.isConnectingToInternet()) {
                    MyDialog.iPhone("No response from server\nPlease try again!", getActivity());

                } else {
                    Utility.showInternetAlert(getActivity());
                }
            }




            captionEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {




                    if (actionId == EditorInfo.IME_ACTION_DONE) {


                        InputMethodManager imm= (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(captionEditText.getWindowToken(), 0);
                        captionEditText.setCursorVisible(false);

                        String caption = captionEditText.getText().toString();
                        //  String category_photo_id = photo_id.getString("category_photo_id","null");

                        String url = Constant.PHOTO_CAPTION_UPDATE + category_photo_id + "&caption=" + caption.replace(" ", "%20") + "&credential_key=" + Utility.getSharedPreferences(getActivity(), Constant.CREDENTIALKEY);
//        Log.e(TAG,"url: "+url);
                        new AQuery(getActivity()).ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
                            @Override
                            public void callback(String url, JSONObject json, AjaxStatus status) {
//                Log.e(TAG,"Response: "+json);
                                if (json != null) {
                                    try {
                                        if (json.getString("status").equalsIgnoreCase("SUCCESS")) {
                                            Toast.makeText(getActivity(), json.getString("message"), Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(getActivity(), json.getString("message"), Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }

                                } else {

                                    if (Utility.isConnectingToInternet()) {
                                        MyDialog.iPhone("No response from server\nPlease try again!", getActivity());

                                    } else {
                                        Utility.showInternetAlert(getActivity());
                                    }
                                }
                            }
                        });



                        return true; // consume.
                    }
                    return false; // pass on to other listeners.
                }
            });
            captionEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus) {


                    }
                }
            });



            return swipeView;
        }
        private void init() {
            imageNameTextView = (TextView) swipeView.findViewById(R.id.tv_file_name);
            fromTextView = (TextView) swipeView.findViewById(R.id.tv_from);
            sizeTextView = (TextView) swipeView.findViewById(R.id.tv_size);
            dateTextView = (TextView) swipeView.findViewById(R.id.tv_date);
            zoomLayout=(ZoomLayout) swipeView.findViewById(R.id.relativelayout1);
            penImageView = (ImageView) swipeView.findViewById(R.id.iv_pen);

            bigImageView = (ImageView) swipeView.findViewById(R.id.iv_big_image);

            captionEditText = (EditText) swipeView.findViewById(R.id.et_caption);
            captionEditText = (EditText) swipeView.findViewById(R.id.et_caption);
            captionEditText.setTextColor(getActivity().getResources().getColor(R.color.textColor));


        }
        static SwipeFragment newInstance(int position) {
            SwipeFragment swipeFragment = new SwipeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            swipeFragment.setArguments(bundle);
            return swipeFragment;
        }
    }
}

