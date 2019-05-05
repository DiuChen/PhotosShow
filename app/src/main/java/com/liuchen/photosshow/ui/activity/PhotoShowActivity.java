package com.liuchen.photosshow.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.liuchen.photosshow.R;
import com.liuchen.photosshow.entity.LocationsInfo;
import com.liuchen.photosshow.ui.widget.HackyViewPager;

public class PhotoShowActivity extends AppCompatActivity {
    private static final String TAG = "PhotoShowActivity";
    private View backgroundView;
    private HackyViewPager photoVp;
    private TextView indicatorTv;
    private LocationsInfo locationsInfo;
    private int position;
    private RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_show);

        backgroundView = findViewById(R.id.backgroundView);
        photoVp = findViewById(R.id.photoVp);
        indicatorTv = findViewById(R.id.indicatorTv);

        Intent intent = getIntent();
        locationsInfo = intent.getParcelableExtra("locationsInfo");
        position = intent.getIntExtra("position", 0);

        requestManager = Glide.with(this);

        photoVp.setPageMargin(30);
        indicatorTv.setText(position + 1 + "/" + locationsInfo.getLocations().size());
        indicatorTv.setVisibility(View.GONE);
        photoVp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return locationsInfo.getLocations().size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                final PhotoView photoView = new PhotoView(PhotoShowActivity.this);
                photoView.setZoomable(false);
                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                requestManager.load(locationsInfo.getLocations().get(position).getUrl())
                        .addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                photoView.setZoomable(true);
                                return false;
                            }
                        })
                        /*.apply(new RequestOptions()
                                .error(R.mipmap.bg_xzsb)
                                .placeholder(R.mipmap.bg_jzz))*/
                        .into(photoView);
                container.addView(photoView);
                return photoView;
            }
        });
        photoVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                PhotoShowActivity.this.position = position;
                indicatorTv.setText(position + 1 + "/" + locationsInfo.getLocations().size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        photoVp.setCurrentItem(position, false);

        float amplification = locationsInfo.getLocations().get(position).getAmplification();
        float fromXDelta = locationsInfo.getLocations().get(position).getFromXDelta();
        float fromYDelta = locationsInfo.getLocations().get(position).getFromYDelta();

        AnimationSet setAnimation = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(amplification, 1f, amplification, 1f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(300);

        TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, 0, fromYDelta, 0);
        translateAnimation.setDuration(300);

        setAnimation.addAnimation(scaleAnimation);
        setAnimation.addAnimation(translateAnimation);
        setAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                indicatorTv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        photoVp.startAnimation(setAnimation);
        backgroundView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_in));
    }


    @Override
    public void finish() {
        float amplification = locationsInfo.getLocations().get(position).getAmplification();
        float fromXDelta = locationsInfo.getLocations().get(position).getFromXDelta();
        float fromYDelta = locationsInfo.getLocations().get(position).getFromYDelta();
        AnimationSet setAnimation = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, amplification, 1f, amplification,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(300);

        TranslateAnimation translateAnimation = new TranslateAnimation(0, fromXDelta, 0, fromYDelta);
        translateAnimation.setDuration(300);

        setAnimation.addAnimation(scaleAnimation);
        setAnimation.addAnimation(translateAnimation);
        setAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                indicatorTv.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                PhotoShowActivity.super.finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        photoVp.startAnimation(setAnimation);
        backgroundView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_out));
    }
}
