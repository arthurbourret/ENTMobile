package com.example.entmobile.schedule;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.entmobile.R;

public class DialogOpenCourse extends DialogFragment {
    private AlertDialog.Builder builder;

    public DialogOpenCourse(Context context, View dialogView, String[] infos) {
        builder = new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setCancelable(true)
                .setTitle("");

        int[] id = {R.id.name_dialog,
                R.id.start_dialog,
                R.id.duration_dialog,
                R.id.end_dialog,
                R.id.teacher_dialog,
                R.id.students_dialog,
                R.id.room_dialog};

        for (int i = 0; i < infos.length; i++)
            ((TextView) dialogView.findViewById(id[i])).setText(infos[i]);
    }

    public void showDialog() {
        if (builder != null)
            builder.create().show();
    }
}
