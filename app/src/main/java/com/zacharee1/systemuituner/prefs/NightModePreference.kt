package com.zacharee1.systemuituner.prefs

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.data.NightModeInfo
import com.zacharee1.systemuituner.interfaces.IDangerousPreference
import com.zacharee1.systemuituner.interfaces.ISpecificPreference
import com.zacharee1.systemuituner.prefs.base.BaseDialogPreference
import com.zacharee1.systemuituner.util.SettingsType
import com.zacharee1.systemuituner.util.writeSecure
import com.zacharee1.systemuituner.views.NightModeView

class NightModePreference(context: Context, attrs: AttributeSet) : BaseDialogPreference(context, attrs), ISpecificPreference, IDangerousPreference {
    override val keys = hashMapOf(
        SettingsType.SECURE to arrayOf(
            NightModeView.NIGHT_DISPLAY_ACTIVATED,
            NightModeView.NIGHT_DISPLAY_AUTO_MODE,
            NightModeView.NIGHT_DISPLAY_COLOR_TEMPERATURE,
            NightModeView.TWILIGHT_MODE
        )
    )
    override var dangerous = true

    init {
        key = "night_mode_option"

        setTitle(R.string.option_night_mode)
        setSummary(R.string.option_night_mode_desc)
        setIcon(R.drawable.ic_baseline_nights_stay_24)

        lowApi = Build.VERSION_CODES.N
        dialogTitle = title
        dialogMessage = summary
        iconColor = R.color.pref_color_4
    }

    override fun onValueChanged(newValue: Any?, key: String) {
        val info = newValue as NightModeInfo?

        context.apply {
            if (info == null) {
                keys.forEach { key ->
                    key.value.forEach {
                        writeSecure(it, null)
                    }
                }
            } else {
                writeSecure(NightModeView.TWILIGHT_MODE, info.twilightMode)
                writeSecure(NightModeView.NIGHT_DISPLAY_ACTIVATED, info.nightModeActivated)
                writeSecure(NightModeView.NIGHT_DISPLAY_AUTO_MODE, info.nightModeAuto)
                writeSecure(NightModeView.NIGHT_DISPLAY_COLOR_TEMPERATURE, info.nightModeTemp)
            }
        }
    }
}