package com.rw.tweaks.prefs.secure

import android.content.Context
import android.util.AttributeSet
import androidx.preference.DialogPreference
import com.rw.tweaks.R
import com.rw.tweaks.util.ISecurePreference
import com.rw.tweaks.util.SecurePreference
import com.rw.tweaks.util.SettingsType
import com.rw.tweaks.util.verifiers.BaseVisibilityVerifier
import com.rw.tweaks.util.writeSetting

class SecureSwitchPreference(context: Context, attrs: AttributeSet) : DialogPreference(context, attrs), ISecurePreference by SecurePreference(context) {
    companion object {
        const val DEFAULT_ENABLED = "1"
        const val DEFAULT_DISABLED = "0"
    }

    var enabled = DEFAULT_ENABLED
    var disabled = DEFAULT_DISABLED

    init {
        isPersistent = false
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.SecureSwitchPreference, 0, 0)

        type = SettingsType.values().find { it.value == array.getInt(R.styleable.SecureSwitchPreference_settings_type, SettingsType.UNDEFINED.value) } ?: SettingsType.UNDEFINED
        enabled = array.getString(R.styleable.SecureSwitchPreference_enabled_value) ?: DEFAULT_ENABLED
        disabled = array.getString(R.styleable.SecureSwitchPreference_disabled_value) ?: DEFAULT_DISABLED
        writeKey = array.getString(R.styleable.SecureSwitchPreference_differing_key)
        dangerous = array.getBoolean(R.styleable.SecureSwitchPreference_dangerous, false)
        lowApi = array.getInt(R.styleable.SecureSwitchPreference_low_api, lowApi)
        highApi = array.getInt(R.styleable.SecureSwitchPreference_high_api, highApi)
        iconColor = array.getColor(R.styleable.SecureSwitchPreference_icon_color, iconColor)

        val clazz = array.getString(R.styleable.SecureSwitchPreference_visibility_verifier)
        if (clazz != null) {
            visibilityVerifier = context.classLoader.loadClass(clazz)
                .getConstructor(Context::class.java)
                .newInstance(context) as BaseVisibilityVerifier
        }

        dialogMessage = summary

        array.recycle()

        init(this)
    }

    override fun onValueChanged(newValue: Any?, key: String?) {
        context.writeSetting(type, writeKey, newValue)
    }
}