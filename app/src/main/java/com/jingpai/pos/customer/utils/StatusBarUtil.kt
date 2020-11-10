package com.jingpai.pos.customer.utils

import android.R.id
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.IntDef
import com.readystatesoftware.systembartint.SystemBarTintManager
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.reflect.Field
import java.lang.reflect.Method

object StatusBarUtil {
    const val TYPE_MIUI = 0
    const val TYPE_FLYME = 1
    const val TYPE_M = 3//6.0

    /**
     * 修改状态栏颜色，支持4.4以上版本
     *
     * @param colorId 颜色
     */
    fun setStatusBarColor(activity: Activity, colorId: Int) {
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            val window: Window = activity.window
            window.statusBarColor = colorId
        } else if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            //使用SystemBarTintManager,需要先将状态栏设置为透明

            setTranslucentStatus(
                activity
            )
            val systemBarTintManager = SystemBarTintManager(activity)
            systemBarTintManager.isStatusBarTintEnabled = true//显示状态栏

            systemBarTintManager.setStatusBarTintColor(colorId)//设置状态栏颜色
        }
    }

    /**
     * 设置状态栏透明
     */
    @TargetApi(19)
    fun setTranslucentStatus(activity: Activity) {
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色

            val window: Window = activity.window
            val decorView = window.decorView
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间


            val option = (SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or SYSTEM_UI_FLAG_LAYOUT_STABLE)
            decorView.systemUiVisibility = option
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            //导航栏颜色也可以正常设置
            //window.setNavigationBarColor(Color.TRANSPARENT);

        } else if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val window: Window = activity.window
            val attributes: WindowManager.LayoutParams = window.attributes
            val flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            flagTranslucentStatus.also {
                val arg: WindowManager.LayoutParams = attributes
                arg.flags = arg.flags or it
            }
            //int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            //attributes.flags |= flagTranslucentNavigation;


            window.attributes = attributes
        }
    }

    /**
     * 代码实现android:fitsSystemWindows
     *
     * @param activity
     */
    fun setRootViewFitsSystemWindows(activity: Activity, fitSystemWindows: Boolean) {
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val winContent = activity.findViewById<View>(id.content) as ViewGroup
            if (winContent.childCount > 0) {
                val rootView = winContent.getChildAt(0) as ViewGroup
                if (rootView != null) {
                    rootView.fitsSystemWindows = fitSystemWindows
                }
            }
        }
    }

    /**
     * 设置状态栏深色浅色切换
     */
    fun setStatusBarDarkTheme(activity: Activity, dark: Boolean): Boolean {
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            if (VERSION.SDK_INT >= VERSION_CODES.M) {
                setStatusBarFontIconDark(
                    activity,
                    TYPE_M,
                    dark
                )
            } else if (OSUtils.isMiui) {
                setStatusBarFontIconDark(
                    activity,
                    TYPE_MIUI,
                    dark
                )
            } else if (OSUtils.isFlyme) {
                setStatusBarFontIconDark(
                    activity,
                    TYPE_FLYME,
                    dark
                )
            } else {//其他情况

                return false
            }
            return true
        }
        return false
    }

    /**
     * 设置状态栏模式
     * @param activity
     * @param isTextDark 文字、图标是否为黑色 （false为默认的白色）
     * @param colorId 状态栏颜色
     * @return
     */
    fun setStatusBarMode(
        activity: Activity,
        isTextDark: Boolean,
        colorId: Int
    ) {
        if (!isTextDark) {
            //文字、图标颜色不变，只修改状态栏颜色
            setStatusBarColor(
                activity,
                colorId
            )
        } else {
            //修改状态栏颜色和文字图标颜色
            setStatusBarColor(
                activity,
                colorId
            )
            //4.4以上才可以改文字图标颜色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (OSUtil.isMIUI()) {
                    //小米MIUI系统
                    setMIUIStatusBarTextMode(
                        activity,
                        isTextDark
                    )
                } else if (OSUtil.isFlyme()) {
                    //魅族flyme系统
                    setFlymeStatusBarTextMode(
                        activity,
                        isTextDark
                    )
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //6.0以上，调用系统方法
                    val window = activity.window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    //4.4以上6.0以下的其他系统，暂时没有修改状态栏的文字图标颜色的方法，有可以加上
                }
            }
        }
    }

    /**
     * 设置Flyme系统状态栏的文字图标颜色
     * @param activity
     * @param isDark 状态栏文字及图标是否为深色
     * @return
     */
    fun setFlymeStatusBarTextMode(
        activity: Activity,
        isDark: Boolean
    ): Boolean {
        val window = activity.window
        var result = false
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag =
                    WindowManager.LayoutParams::class.java
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags =
                    WindowManager.LayoutParams::class.java
                        .getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                value = if (isDark) {
                    value or bit
                } else {
                    value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
                result = true
            } catch (e: java.lang.Exception) {
            }
        }
        return result
    }


    /**
     * 设置MIUI系统状态栏的文字图标颜色（MIUIV6以上）
     * @param activity
     * @param isDark 状态栏文字及图标是否为深色
     * @return
     */
    fun setMIUIStatusBarTextMode(
        activity: Activity,
        isDark: Boolean
    ): Boolean {
        var result = false
        val window = activity.window
        if (window != null) {
            val clazz: Class<*> = window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams =
                    Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field: Field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField: Method = clazz.getMethod(
                    "setExtraFlags",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType
                )
                if (isDark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag) //状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag) //清除黑色字体
                }
                result = true
                if (VERSION.SDK_INT >= VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (isDark) {
                        activity.window.decorView.systemUiVisibility =
                            SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        activity.window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_VISIBLE
                    }
                }
            } catch (e: java.lang.Exception) {
            }
        }
        return result
    }


    /**
     * 设置 状态栏深色浅色切换
     */
    fun setStatusBarFontIconDark(activity: Activity, type: Int, dark: Boolean): Boolean {
        return when (type) {
            TYPE_MIUI -> setMiuiUI(
                activity,
                dark
            )
            TYPE_FLYME -> setFlymeUI(
                activity,
                dark
            )
            TYPE_M -> setCommonUI(
                activity,
                dark
            )
            else -> setCommonUI(
                activity,
                dark
            )
        }
    }

    //设置6.0 状态栏深色浅色切换
    fun setCommonUI(activity: Activity, dark: Boolean): Boolean {
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            val decorView = activity.window.decorView
            if (decorView != null) {
                var vis = decorView.systemUiVisibility
                vis = if (dark) {
                    vis or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    vis and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                }
                if (decorView.systemUiVisibility != vis) {
                    decorView.systemUiVisibility = vis
                }
                return true
            }
        }
        return false
    }

    //设置Flyme 状态栏深色浅色切换
    fun setFlymeUI(activity: Activity, dark: Boolean): Boolean {
        return try {
            val window: Window = activity.window
            val lp: WindowManager.LayoutParams? = window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            value = if (dark) {
                value or bit
            } else {
                value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            window.attributes = lp
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    //设置MIUI 状态栏深色浅色切换
    fun setMiuiUI(activity: Activity, dark: Boolean): Boolean {
        return try {
            val window: Window? = activity.window
            val clazz: Class<*> = activity.window.javaClass
            @SuppressLint("PrivateApi") val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            val darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getDeclaredMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            extraFlagField.isAccessible = true
            if (dark) {    //状态栏亮色且黑色字体

                extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    //获取状态栏高度
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier(
                "status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    @IntDef(
        TYPE_MIUI,
        TYPE_FLYME,
        TYPE_M
    )
    @Retention(RetentionPolicy.SOURCE)
    internal annotation class ViewType
}