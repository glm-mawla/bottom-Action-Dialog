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

# how to call
Call as like BottomSheetDialogFragment i.e


JAVA:

```

new BottomActionDialog.DialogBuilder()
                .setTitle("Change your email")
                .setSubTitle("")
                .setIsShowBanner(false)
                .setCancelable(false)
                .setPositiveButton(R.string.change, new BottomActionViewBottom.BottomDialogPositiveClickListener() {
                            @Override
                            public void onPositiveButtonClick(@org.jetbrains.annotations.Nullable Dialog dialog) {
                                 if (dialog != null) dialog.dismiss();
                                //TODO()
                            }
                        },
                        R.color.green)
                .setNegativeButton(R.string.cancel, new BottomActionViewBottom.BottomDialogNegativeClickListener() {
                            @Override
                            public void onNegativeButtonClick(@org.jetbrains.annotations.Nullable Dialog dialog) {
                                if (dialog != null) dialog.dismiss();
                            }
                        },
                        R.color.gray_lite)
                .build(context)
                .showAsBottomSheet();
                
  ```             
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
