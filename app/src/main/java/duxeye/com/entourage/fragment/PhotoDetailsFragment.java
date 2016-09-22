package duxeye.com.entourage.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.Parser;

import java.util.ArrayList;

import duxeye.com.entourage.R;
import duxeye.com.entourage.Utility.Utility;
import duxeye.com.entourage.constant.Constant;
import duxeye.com.entourage.customViews.CircularProgressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoDetailsFragment extends Fragment {
    private static final String TAG = PhotoDetailsFragment.class.getSimpleName();
    private View mView;
    private ViewPager mViewPager;
    private PhotoDetailPagerAdapter mPagerAdapter;
    private int lastPosition = 0;
    public static final String CATEGORY_ID = "category_id";
    SharedPreferences category_id;
    SharedPreferences.Editor edit_category_id;
    public ArrayList<String> mPhotoGridArrayList;
    String Photo_Id;
    boolean chageImage = false;
    int setPos,total;
    //    private ImageView penImageView;
//    private ImageViewTouch bigImageView;
//    private TextView fromTextView, imageNameTextView, sizeTextView, dateTextView;
//    private EditText captionEditText;
//    private String next_photo_id, prior_photo_id, category_photo_id;
    private CircularProgressBar mProgressBar;
//    private RelativeLayout mPhotoLayout;
//    private ScaleGestureDetector mGestureDetector;
//    private Matrix matrix = new Matrix();
//    private float scale = 1f;



    private int scroll = 0;
    // set only on `onPageSelected` use it in `onPageScrolled`
    // if currentPage < page - we swipe from left to right
    // if currentPage == page - we swipe from right to left  or centered
    private int currentPage = 0;
    // if currentPage < page offset goes from `screen width` to `0`
    // as you reveal right fragment.
    // if currentPage == page , offset goes from `0` to `screen width`
    // as you reveal right fragment
    // You can use it to see
    //if user continue to reveal next fragment or moves it back
    private int currentOffset = 0;
    // behaves similar to offset in range `[0..1)`
    private float currentScale = 0;

    public PhotoDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_photo_detail, container, false);

        init();
        total = Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.PHOTO_COUNT));
       // lastPosition = Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.CURRENT_PAGE_INDEX));

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
        mView.findViewById(R.id.iv_pd_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                edit_category_id.clear();
//                edit_category_id.commit();
//
//                mPhotoGridArrayList.clear();
               // Utility.setSharedPreference(getActivity(), Constant.PHOTO_ID, "");
           //     Utility.setSharedPreference(getActivity(), Constant.CURRENT_PAGE_INDEX, "");
                photoGridFragment();



            }
        });





        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                if (position == total - 1) {
//                    chageImage = true;
//                    setPos = 1;
//                } else if (position == 0) {
//                    chageImage = true;
//                    setPos = total - 2;
//                } else {
//                    chageImage = false;
//                }

                if(lastPosition>position)
                {//User Move to left


                    if (Utility.getSharedPreferences(getActivity(), Constant.PRIOR_PHOTO_ID).equalsIgnoreCase("")) {
                        //Toast.makeText(getActivity(), "You are at 1st photo", Toast.LENGTH_SHORT).show();


                     } else {
                        Utility.setSharedPreference(getActivity(), Constant.PHOTO_ID, Utility.getSharedPreferences(getActivity(), Constant.PRIOR_PHOTO_ID));

                    }

                //   Utility.setSharedPreference(getActivity(), Constant.PHOTO_ID, Utility.getSharedPreferences(getActivity(), Constant.PRIOR_PHOTO_ID));
                    Log.e("scroll", "left" + Utility.getSharedPreferences(getActivity(), Constant.PRIOR_PHOTO_ID));
                }
                else if(lastPosition<position)
                { //User Move to right



                    if (Utility.getSharedPreferences(getActivity(), Constant.NEXT_PHOTO_ID).equalsIgnoreCase("")) {
                     //  Toast.makeText(getActivity(), "No more next", Toast.LENGTH_SHORT).show();

                    } else {

                        Utility.setSharedPreference(getActivity(), Constant.PHOTO_ID, Utility.getSharedPreferences(getActivity(), Constant.NEXT_PHOTO_ID));
                    }

                  //  Utility.setSharedPreference(getActivity(), Constant.PHOTO_ID, Utility.getSharedPreferences(getActivity(), Constant.NEXT_PHOTO_ID));
                    Log.e("scroll", "right" + Utility.getSharedPreferences(getActivity(), Constant.NEXT_PHOTO_ID));
                }
                lastPosition=position;

            }

            @Override
            public void onPageSelected(int position) {


                currentPage = position;
                currentScale = 0;
                currentOffset = 0;
                scroll = 0;


            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == ViewPager.SCROLL_STATE_IDLE && chageImage) {
                    mViewPager.setCurrentItem(setPos, false);

                }



            }
        });
        mViewPager.setCurrentItem(Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.CURRENT_PAGE_INDEX))+1);


        return mView;
    }

    private void init() {
        ((TextView) mView.findViewById(R.id.pd_header_text)).setText("PHOTO DETAILS");
//        backButton = (ImageView) mView.findViewById(R.id.iv_pd_back);
//        bigImageView = (ImageViewTouch) mView.findViewById(R.id.iv_big_image);
//        penImageView = (ImageView) mView.findViewById(R.id.iv_pen);

//        imageNameTextView = (TextView) mView.findViewById(R.id.tv_file_name);
//        fromTextView = (TextView) mView.findViewById(R.id.tv_from);
//        sizeTextView = (TextView) mView.findViewById(R.id.tv_size);
//        dateTextView = (TextView) mView.findViewById(R.id.tv_date);

//        captionEditText = (EditText) mView.findViewById(R.id.et_caption);
//        captionEditText.setTextColor(getActivity().getResources().getColor(R.color.textColor));

        mProgressBar = new CircularProgressBar(getActivity());
        mProgressBar.setCancelable(false);

//        mPhotoLayout = (RelativeLayout) mView.findViewById(R.id.relativelayout);
        mViewPager = (ViewPager) mView.findViewById(R.id.vp_pd_pager);
        mPagerAdapter = new PhotoDetailPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
//        mViewPager.setCurrentItem(Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.CURRENT_PAGE_INDEX)));
//        getImageDetails();
    }

    private void photoGridFragment() {
        Fragment fragment = new GridPhotoFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



    public class PhotoDetailPagerAdapter extends FragmentPagerAdapter {

        public PhotoDetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Log.e("PHOTO_ID: ", String.valueOf(Utility.getSharedPreferences(getActivity(), Constant.PHOTO_ID)));




//            return getUIData(Utility.getSharedPreferences(getActivity(), Constant.PRIOR_PHOTO_ID), position);
            return new PhotoDetailRowFragment();


        }

        @Override
        public int getCount() {


         //   -Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.CURRENT_PAGE_INDEX))+2

            return Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.PHOTO_COUNT));
        }

    }

}


