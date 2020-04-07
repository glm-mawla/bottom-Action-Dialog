package com.glmmawla.bottomdialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.util.Preconditions
import androidx.fragment.app.FragmentActivity
import java.io.Serializable

class BottomActionDialog private constructor(
    private val context: FragmentActivity,
    private val data: DialogBuilder.Build
) {

    private fun show() {
        //TODO("remove PRIVATE accessor")
        //TODO("build and show a custom dialog")
    }

    fun showAsBottomSheet(): BottomActionViewBottom {
        val bottomActionDialog = BottomActionViewBottom.newInstance(data)
        bottomActionDialog.show(context.supportFragmentManager, "dg dialog")

        return bottomActionDialog
    }


    class DialogBuilder (): Serializable{

        /*class Build: Serializable {
            var title: Any? = R.string.error_dialog_title
             var subTitle: Any? = null
             var positiveButtonName: Int? = R.string.ok
             var negativeButtonName: Int? = null
             var positiveClickListener: BottomActionViewBottom.BottomDialogPositiveClickListener? = null
             var negativeClickListener: BottomActionViewBottom.BottomDialogNegativeClickListener? = null
             var ratingSelectionListener: BottomActionViewBottom.BottomDialogRateSelectionListener? = null
             var isCancelable: Boolean = false
             var isShowBanner: Boolean = true
             var isShowRating: Boolean = false
             var isShowPositiveButton: Boolean = true
             var circularBannerUrl: String? = null
        }*/

        data class Build(
                internal var title: Any? = R.string.dialog_title,
                var subTitle: Any? = null,
                var positiveButtonName: Int? = R.string.ok,
                var negativeButtonName: Int? = null,
                var positiveClickListener: BottomActionViewBottom.BottomDialogPositiveClickListener? = null,
                var negativeClickListener: BottomActionViewBottom.BottomDialogNegativeClickListener? = null,
                var ratingSelectionListener: BottomActionViewBottom.BottomDialogRateSelectionListener? = null,
                var isCancelable: Boolean = false,
                var isShowBanner: Boolean = true,
                var isShowRating: Boolean = false,
                var isShowPositiveButton: Boolean = true,
                var circularBannerUrl: String? = null,
                var customView : View? = null
        ) : Serializable {
            internal var positiveButtonColor: Int? = R.color.holo_green_dark
            internal var negativeButtonColor: Int? = R.color.gray_lite
        }

        //initiate Build
        private val data = Build()

        fun setTitle(@StringRes title: Int): DialogBuilder {
            data.title = title
            return this
        }

        fun setSubTitle(@StringRes subTitle: Int): DialogBuilder {
            data.subTitle = subTitle
            return this
        }

        fun setTitle(title: String?): DialogBuilder {
            data.title = title
            return this
        }

        fun setSubTitle(subTitle: String?): DialogBuilder {
            data.subTitle = subTitle
            return this
        }

        @SuppressLint("RestrictedApi")
        fun build(context: FragmentActivity): BottomActionDialog {
            Preconditions.checkNotNull(context, "Context not be null")
            return BottomActionDialog(context, data)
        }

        fun setPositiveButton(
                @StringRes ok: Int,
                listener: BottomActionViewBottom.BottomDialogPositiveClickListener?,
                @ColorRes color: Int = R.color.holo_green_dark
        ): DialogBuilder {
            data.positiveButtonColor = color
            data.positiveButtonName = ok
            data.positiveClickListener = listener
            return this
        }

        fun setNegativeButton(
                @StringRes negative: Int,
                listener: BottomActionViewBottom.BottomDialogNegativeClickListener?,
                @ColorRes color: Int? = R.color.gray_lite
        ): DialogBuilder {
            data.negativeButtonColor = color
            data.negativeButtonName = negative
            data.negativeClickListener = listener
            return this
        }

        fun setCancelable(isCancelable: Boolean): DialogBuilder {
            data.isCancelable = isCancelable
            return this
        }

        fun setIsShowBanner(isShow: Boolean): DialogBuilder {
            data.isShowBanner = isShow
            return this
        }

        fun setRatingBar(listener: BottomActionViewBottom.BottomDialogRateSelectionListener): DialogBuilder {
            data.isShowRating = true
            data.ratingSelectionListener = listener
            return this
        }

        fun hidePositiveButton(): DialogBuilder {
            data.isShowPositiveButton = false
            return this
        }

        fun setCircularBanner(link: String?): DialogBuilder {
            data.circularBannerUrl = link
            return this
        }

        fun addCustomView(view: View): DialogBuilder{
            data.customView = view
            return this
        }
    }
}