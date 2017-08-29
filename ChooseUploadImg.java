package com.example.myfirstapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import static java.lang.System.out;

public class ChooseUploadImg extends Activity
{
    private String imgPath;
    private String myID = "";

    public void uploadImage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = findViewById(R.id.image_location);
        String location = editText.getText().toString();
        editText = findViewById(R.id.image_date);
        String date = editText.getText().toString();
        Date d = new Date(Long.parseLong(date));
        editText = findViewById(R.id.image_member);
        String member = editText.getText().toString();
        editText = findViewById(R.id.image_note);
        String note = editText.getText().toString();

        DataBaseManager db = new DataBaseManager();
        try {
            db.AddImage(myID, member, location, d, note, "");
        } catch (DataBaseError e) {
            out.println(e);
        } catch (DataBaseSignal e) {
            out.println(e);
        }
        startActivity(intent);
    }

    private void selectImage() {
        imgPath = Environment.getExternalStorageDirectory() + "/APP/Image/";
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 2);
        Bundle bundle = new Bundle();
        final Bitmap bmp = bundle.getParcelable(imgPath);
        saveImg(bmp);
    }

    private void saveImg(Bitmap bmp) {
        File imgFile = new File(imgPath);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FileOutputStream f;
        bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
        try {
            f = new FileOutputStream(imgFile);
            f.write(out.toByteArray());
            f.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
