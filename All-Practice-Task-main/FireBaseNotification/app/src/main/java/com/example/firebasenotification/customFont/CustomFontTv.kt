package com.example.firebasenotification.customFont
//
//import android.content.Context
//import android.graphics.Typeface
//import android.util.AttributeSet
//import androidx.appcompat.widget.AppCompatTextView
//import com.example.firebasenotification.customFont.CustomFontTv.CustomFont.Companion.fromString
//
//
//class CustomFontTv : AppCompatTextView {
//    private val sScheme = "http://schemas.android.com/apk/res-auto"
//    private val sAttribute = "customFont"
//
//    constructor(context: Context?) : super(context!!)
//
//    enum class CustomFont(private val fileName: String) {
//        PACIFICO("fonts/pacifico"), BEAURIVAGEREGULAR("fonts/beaurivageregular");
//
//        fun asTypeface(context: Context): Typeface {
//            return Typeface.createFromAsset(context.assets, "$fileName.ttf")
//        }
//
//        companion object {
//            fun fromString(fontName: String): CustomFont {
//                return valueOf(fontName)
//            }
//        }
//    }
//
//
//    constructor(context: Context?, attrs: AttributeSet?) : super(
//        context!!, attrs) {
//        if (isInEditMode)
//            return
//        else
//        {
//            val fontName = attrs?.getAttributeValue(sScheme,sAttribute)
//            if (fontName==null)
//            {
//                throw IllegalAccessException("You must provider \"$sAttribute")
//            }
//            else
//            {
//                val customTypeface = fromString(fontName).asTypeface(context)
//                typeface = customTypeface
//            }
//        }
//    }
//
//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
//        context!!, attrs, defStyleAttr)
//}