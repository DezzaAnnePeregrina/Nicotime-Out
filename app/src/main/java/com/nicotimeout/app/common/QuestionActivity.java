package com.nicotimeout.app.common;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.nicotimeout.app.R;
import com.nicotimeout.app.database.DatabaseHelper;
import com.nicotimeout.app.database.UserModel;
import com.nicotimeout.app.user.UserProgress;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class QuestionActivity extends AppCompatActivity {

    //references to button and other controls
    Button question_button;

    Dialog empty_dialog;

    EditText edit_questions_cig_smok_day,
            edit_questions_cig_price_piece,
            edit_questions_years_smoking;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(QuestionActivity.this);
        }
        Cursor cursor = databaseHelper.getData();
        if (cursor.getCount() == 0) {
        } else {
            Intent intent = new Intent(QuestionActivity.this, UserProgress.class);
            startActivity(intent);
        }

        question_button = findViewById(R.id.question_button);

        empty_dialog = new Dialog(this);

        edit_questions_cig_smok_day = findViewById(R.id.edit_questions_cig_smok_day);
        edit_questions_cig_price_piece = findViewById(R.id.edit_questions_cig_price_piece);
        edit_questions_years_smoking = findViewById(R.id.edit_questions_years_smoking);


        question_button.setOnClickListener(view -> {

            String editTxt1 = edit_questions_cig_smok_day.getText().toString();
            String editTxt2 = edit_questions_cig_price_piece.getText().toString();
            String editTxt3 = edit_questions_years_smoking.getText().toString();

            if (editTxt1.isEmpty() || editTxt2.isEmpty() || editTxt3.isEmpty()) {

                empty_dialog();

            } else {
                UserModel userModel = null;
                try {
                    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                    Date currentLocalTime = calendar.getTime();
                    DateFormat date = new SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault());
                    date.setTimeZone(TimeZone.getTimeZone("GMT+8"));

                    String localTime = date.format(currentLocalTime);

                    userModel = new UserModel(-1,
                            localTime,
                            Integer.parseInt(editTxt1),
                            Integer.parseInt(editTxt2),
                            Integer.parseInt(editTxt3));

                    Intent intent = new Intent(QuestionActivity.this, UserProgress.class);
                    QuestionActivity.this.startActivity(intent);


                } catch (Exception e) {
                    Log.e("NICOTIME_OUT_LOGS", "Insert Failed", e);
                }
                DatabaseHelper databaseHelper = new DatabaseHelper(QuestionActivity.this);
                databaseHelper.addOne(userModel);

            }
        });

    }

    private void empty_dialog() {
        empty_dialog.setContentView(R.layout.empty_dialog);
        empty_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        empty_dialog.setCancelable(false);
        empty_dialog.setCanceledOnTouchOutside(false);


        Button button = empty_dialog.findViewById(R.id.button_no);
        button.setOnClickListener(view -> empty_dialog.dismiss());
        empty_dialog.show();
    }
}
