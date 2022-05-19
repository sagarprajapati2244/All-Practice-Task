package com.example.firebasenotification.customFont

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.example.firebasenotification.R


class CustomTextView : androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView)
            val fontName = a.getString(R.styleable.CustomTextView_customFont)
            if (fontName != null) {
                val mTypeface = Typeface.createFromAsset(context.assets,
                    "fonts/$fontName")
                typeface = mTypeface
            }
            a.recycle()
        }
    }
}