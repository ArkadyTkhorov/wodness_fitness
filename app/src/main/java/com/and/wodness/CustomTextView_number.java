package com.and.wodness;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.and.wodness.R;

/**
 * Created by ocittwo on 10/5/16.
 *
 * @Author Ahmad Rosid
 * @Email ocittwo@gmail.com
 * @Github https://github.com/ar-android
 * @Web http://ahmadrosid.com
 */
public class CustomTextView_number extends android.support.v7.widget.AppCompatTextView {

    public CustomTextView_number(Context context) {
        this(context, null);
    }

    public CustomTextView_number(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.CustomTextView, 0, 0);
        String path_font = array.getString(R.styleable.CustomTextView_fontPath);

        array.recycle();
        if (path_font != null) {
            setCustomTypeFace(context, path_font);
        } else {
            setCustomTypeFace(context, "fonts/AvenirNextLTPro-Regular.otf");
        }
    }

    private void setCustomTypeFace(Context context, String path_font) {
        Typeface typeFaces = Typeface.createFromAsset(context.getAssets(), path_font);
        this.setTypeface(typeFaces);
    }
}
