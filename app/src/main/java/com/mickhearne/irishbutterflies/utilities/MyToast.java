package com.mickhearne.irishbutterflies.utilities;

import android.widget.Toast;

import com.mickhearne.irishbutterflies.MyApplication;

/**
 * Created by kxiang on 02/10/2013.
 */
public class MyToast {
    public static void showToast(String text){
        Toast toast= Toast.makeText(MyApplication.getAppContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
