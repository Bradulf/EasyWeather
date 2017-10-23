package com.example.quiqu.easyweather.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.quiqu.easyweather.R;

/**
 * Created by quiqu on 11/6/2016.
 */

public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /**
         * Notice that in the case of the messages we could have created them by means of passing the
         * context and getting the string via some methods.
         * This is the example:
         *
         *  setTitle(context.getString(R.string.error_title))
         *
         *  The same could have been done for each of the other string resources.
         * */
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_message)
                .setPositiveButton(R.string.error_ok_button_text, null);

        AlertDialog dialog = builder.create();
        return dialog;

    }
}
