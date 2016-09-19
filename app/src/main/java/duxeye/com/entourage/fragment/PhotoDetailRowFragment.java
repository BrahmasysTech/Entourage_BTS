package duxeye.com.entourage.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import duxeye.com.entourage.R;
import duxeye.com.entourage.Utility.LoadImage;
import duxeye.com.entourage.Utility.Utility;
import duxeye.com.entourage.constant.Constant;
import duxeye.com.entourage.customViews.MyDialog;
import duxeye.com.entourage.customViews.ZoomLayout;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * Created by Ondoor (Hasnain) on 8/30/2016.
 */
public class PhotoDetailRowFragment extends Fragment {
    private static final String TAG = PhotoDetailRowFragment.class.getSimpleName();
    private ImageView penImageView;
    private ImageView  bigImageView;
    private TextView fromTextView, imageNameTextView, sizeTextView, dateTextView;
    private EditText captionEditText;
    private View mView;
    ZoomLayout zoomLayout;
    private String next_photo_id, prior_photo_id, category_photo_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.row_photo_details, container, false);
        init();





        setImageAndText(getArguments().getString("photoID"));

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


        return mView;
    }

    private void init() {
        imageNameTextView = (TextView) mView.findViewById(R.id.tv_file_name);
        fromTextView = (TextView) mView.findViewById(R.id.tv_from);
        sizeTextView = (TextView) mView.findViewById(R.id.tv_size);
        dateTextView = (TextView) mView.findViewById(R.id.tv_date);
        zoomLayout=(ZoomLayout) mView.findViewById(R.id.relativelayout1);
        penImageView = (ImageView) mView.findViewById(R.id.iv_pen);

        bigImageView = (ImageView) mView.findViewById(R.id.iv_big_image);

        captionEditText = (EditText) mView.findViewById(R.id.et_caption);
        captionEditText.setTextColor(getActivity().getResources().getColor(R.color.textColor));


    }


    public static PhotoDetailRowFragment newInstance(String photoID) {
        PhotoDetailRowFragment mPhotoDetailRowFragment = new PhotoDetailRowFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("photoID", photoID);
        mPhotoDetailRowFragment.setArguments(mBundle);

        return mPhotoDetailRowFragment;
    }


    public void setImageAndText(String photoID) {
        String url = Constant.PHOTO_DETAILS + photoID + "&yearbook_id=" + Utility.getSharedPreferences(getActivity(), Constant.YEARBOOKID) + "&credential_key=" + Utility.getSharedPreferences(getActivity(), Constant.CREDENTIALKEY);
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
                        LoadImage.load(getActivity(),json.getString("image").replace("[", "%5B").replace("]", "%5D"),bigImageView);



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
    }
}
