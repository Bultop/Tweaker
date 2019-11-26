package com.rw.tweaks.prefs.secure.specific

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.preference.DialogPreference
import com.rw.tweaks.R
import com.rw.tweaks.util.ISecurePreference
import com.rw.tweaks.util.SecurePreference

class CameraGesturesPreference(context: Context, attrs: AttributeSet) : DialogPreference(context, attrs), ISecurePreference by SecurePreference(context) {
    init {
        key = "camera_gestures"

        setTitle(R.string.feature_camera_gestures)
        setSummary(R.string.feature_camera_gestures_desc)

        dialogTitle = title
        dialogMessage = summary
        setIcon(R.drawable.ic_baseline_camera_24)
        iconColor = ContextCompat.getColor(context, R.color.pref_color_6)

        init(this)
    }
}