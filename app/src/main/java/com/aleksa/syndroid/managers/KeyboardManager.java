package com.aleksa.syndroid.managers;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardManager
{
    public static void showKeyboard(Context context)
    {
        toggleKeyboard(context, null,true);
    }

    public static void hideKeyboard(Context context, View view)
    {
       toggleKeyboard(context, view, false);
    }

   private static void toggleKeyboard(Context context, View view, boolean enabled)
   {
       InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

       if (enabled) {
           inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
       } else {
           inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
       }

   }
}
