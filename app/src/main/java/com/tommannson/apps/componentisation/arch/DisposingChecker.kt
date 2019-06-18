package com.netflix.arch

import android.app.Activity

class DisposingChecker {

    internal fun check(target: Any) = target is Activity && !target.isChangingConfigurations
}
