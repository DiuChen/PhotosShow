package com.liuchen.photosshow.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liuchen.photosshow.R;
import com.liuchen.photosshow.entity.LocationsInfo;
import com.liuchen.photosshow.ui.activity.PhotoShowActivity;
import com.liuchen.photosshow.ui.widget.ImagesView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 刘晨
 * Date: 2019/4/30 14:48
 */
public class MainAdapter extends BaseQuickAdapter<List<String>, BaseViewHolder> {
    private Activity activity;

    public MainAdapter(Activity activity, int layoutResId, @Nullable List<List<String>> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final List<String> item) {
        final ImagesView imagesView = helper.getView(R.id.imagesView);
        imagesView.setImages(item);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        final int newWidth = point.x;
        final int newHeight = point.y;

        imagesView.setOnImagesClickListener(new ImagesView.OnImagesClickListener() {
            @Override
            public void onImagesClick(View view, int position) {
                final LocationsInfo locationsInfo = new LocationsInfo();
                List<LocationsInfo.Location> locationList = new ArrayList<>();
                List<ImageView> imageViewList = imagesView.getImageViews();
                for (int i = 0; i < imageViewList.size(); i++) {
                    int[] oldPosition = new int[2];
                    imageViewList.get(i).getLocationInWindow(oldPosition);
                    int oldWidth = imageViewList.get(i).getMeasuredWidth();
                    int oldHeight = imageViewList.get(i).getMeasuredHeight();

                    LocationsInfo.Location location = new LocationsInfo.Location();
                    location.setUrl(item.get(i));
                    location.setAmplification((float) oldWidth / newWidth);
                    location.setFromXDelta(oldWidth / 2f + oldPosition[0] - newWidth / 2f);
                    location.setFromYDelta(oldHeight / 2f + oldPosition[1] - newHeight / 2f);
                    locationList.add(location);
                }
                locationsInfo.setLocations(locationList);
                Intent intent = new Intent(mContext, PhotoShowActivity.class);
                intent.putExtra("locationsInfo", locationsInfo);
                intent.putExtra("position", position);
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }
        });
    }
}
