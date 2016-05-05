package net.greenrivertech.hliu.camerademo2;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    //id number
    private static final int REQ_CODE_TAKE_PICTURE = 0;

    private static String TAG = "Camera Demo";

    //optional
    //a Uniform Resource Identifier (URI) is a string of characters used to identify a resource
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void capture(View view) {

        /*
            intent has to do with the activity that needs to pull in features or functionality
            from somewhere else (from another app, from a piece of hardware, or from another part of
            the same app)
         */

        //this piece of code means that my intention is I want to capture an image
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "test.jpg");
        // create a file to save the image
        fileUri = Uri.fromFile(photo);

        //set the image file name
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        /*
            I want to start an activity for a result
            the constant ID number is just like a ticket number that once the picture is back
            I will have access to the picture that was taken
            I can look it up using the ticket number
        */
        startActivityForResult(intent, REQ_CODE_TAKE_PICTURE);

    }

    //this method deals with when various piece of information come back to the program
    //various actions have their results delivered to my application
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQ_CODE_TAKE_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(MainActivity.this, "Image saved to:\n" +
                        intent.getData(), Toast.LENGTH_LONG).show();
                //get the image that's been taken
                Bitmap bmp = (Bitmap) intent.getExtras().get("data");
                //then tell my image view to use that image
                ImageView img = (ImageView) findViewById(R.id.photo);
                img.setImageBitmap(bmp);
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
                Toast.makeText(MainActivity.this, "Cancelled capture image", Toast.LENGTH_LONG).show();

            } else {
                // Image capture failed, advise user
                Toast.makeText(MainActivity.this, "Image capture failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}
