package com.liuchen.photosshow.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.bumptech.glide.Glide;
import com.liuchen.photosshow.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 刘晨
 * Date: 2019/4/29 15:34
 */
public class ImagesView extends TableLayout {
    private static final String TAG = "ImagesView";
    private List<String> images = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    private OnImagesClickListener onImagesClickListener;
    private int imagesColumn = 3;
    private int imagesMargin;

    public ImagesView(Context context) {
        super(context);
    }

    public ImagesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImagesView);
        imagesColumn = typedArray.getInteger(R.styleable.ImagesView_imagesColumn, 3);
        imagesMargin = typedArray.getDimensionPixelSize(R.styleable.ImagesView_imagesMargin, 0);
        typedArray.recycle();
    }


    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
        removeAllViews();
        imageViews.clear();
        for (int i = 0; i < images.size(); i += imagesColumn) {
            TableRow tableRow = new TableRow(getContext());
            for (int j = i; j < i + imagesColumn; j++) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_images_view, tableRow, false);
                ImageView imageView = view.findViewById(R.id.imageView);
                TableRow.LayoutParams layoutParams = ((TableRow.LayoutParams) view.getLayoutParams());
                layoutParams.setMargins(imagesMargin, imagesMargin, imagesMargin, imagesMargin);
                view.setLayoutParams(layoutParams);
                if (j < images.size()) {
                    final int position = j;
                    imageView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onImagesClickListener != null) {
                                onImagesClickListener.onImagesClick(v, position);
                            }
                        }
                    });
                    imageViews.add(imageView);
                    Glide.with(getContext())
                            .load(images.get(j))
                            .into(imageView);
                }
                tableRow.addView(view);
            }
            addView(tableRow);
        }
    }

    public void setOnImagesClickListener(OnImagesClickListener onImagesClickListener) {
        this.onImagesClickListener = onImagesClickListener;
    }

    public int getPhotoColumn() {
        return imagesColumn;
    }

    public void setPhotoColumn(int imagesColumn) {
        this.imagesColumn = imagesColumn;
    }

    public List<ImageView> getImageViews() {
        return imageViews;
    }

    public interface OnImagesClickListener {
        void onImagesClick(View view, int position);
    }
}
