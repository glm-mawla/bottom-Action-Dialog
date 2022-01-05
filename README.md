# bottom-dialog
Bottom Sheet Dialog acts as an Alert dialog. 
Features: 
- add Custom view 
- able to change custom default Title, subTItle 
- show/off imo rating bar 
- on/off negative button 
- change color of positive/negative button 
- change name of positive/negative button 
- individual call back for each button

Screen shots: 

<img src="confm_dialog.png" height="400" alt="Screenshot1"/>
<img src="conn_error.png" height="400" />

# how to call
Call as like BottomSheetDialogFragment i.e
            
KOTLIN:

```
BottomActionDialog.DialogBuilder()
                .setTitle("Change your email")
                .setSubTitle("bla bla bla")
                .setIsShowBanner(false)
                .setCancelable(false)
                .setPositiveButton(if (isForVerify) R.string.verify_pin else R.string.set_pin,
                        object : BottomActionViewBottom.BottomDialogPositiveClickListener {
                            override fun onPositiveButtonClick(dialog: Dialog?) {
                                    dialog?.dismiss()
                                    //TODO()
                            }
                        },
                        R.color.green)
                .setNegativeButton(R.string.cancel, object : BottomActionViewBottom.BottomDialogNegativeClickListener {
                    override fun onNegativeButtonClick(dialog: Dialog?) {
                        dialog?.dismiss()
                    }
                },
                        R.color.gray_lite)
                .build(this)
                .showAsBottomSheet()
                
                
```

- Customisable positive & negative button: 
       
```
            fun setPositiveButton(
                @StringRes ok: Int,
                listener: BottomActionViewBottom.BottomDialogPositiveClickListener?,
                @ColorRes color: Int = R.color.holo_green_dark
             )

             fun setNegativeButton(
                @StringRes negative: Int,
                listener: BottomActionViewBottom.BottomDialogNegativeClickListener?,
                @ColorRes color: Int? = R.color.gray_lite
            )
```

- On/OFF cancelable: 
``` 
            fun setCancelable(isCancelable: Boolean)
            
```
       
- others functions: 
 
        fun setTitle(@StringRes title: Int)

        fun setSubTitle(@StringRes subTitle: Int)

        fun setTitle(title: String?)

        fun setSubTitle(subTitle: String?)

        fun setIsShowBanner(isShow: Boolean)

        fun setRatingBar(listener: BottomActionViewBottom.BottomDialogRateSelectionListener)

        fun hidePositiveButton() 

        fun setCircularBanner(link: String?)

        fun addCustomView(view: View)
        
        
