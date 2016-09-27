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


public class PhotoDetailsFragment extends Fragment {

    ImageView backButton;
    ViewPager mViewPager;
    private View mView;
    public ImageFragmentPagerAdapter mPagerAdapter;
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
        mPagerAdapter.notifyDataSetChanged();
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



    public class ImageFragmentPagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {
        public ImageFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {

            return Integer.parseInt(Utility.getSharedPreferences(getActivity(), Constant.PHOTO_COUNT));
        }

        @Override
        public Fragment getItem(int position) {
          //  PhotoDetailRowFragment fragment = new PhotoDetailRowFragment();
            Log.e("position123",String.valueOf(position));

            return PhotoDetailRowFragment.newInstance(position);
        }
    }

    public void photoGridFragment() {
        Fragment fragment = new GridPhotoFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
