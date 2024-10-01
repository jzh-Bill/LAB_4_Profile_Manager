package com.example.lab3pm;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.app.Activity;

import android.graphics.BitmapFactory;

import android.provider.MediaStore;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> avatarLauncher;

    private EditText nameEditText;
    private EditText locationEditText;
    private ImageView logoImageView;
    private Button mapButton;
    private ImageView imageView;
    private ActivityResultLauncher<Intent> imagePickerLauncher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        nameEditText = findViewById(R.id.nameEditText);
        locationEditText = findViewById(R.id.locationEditText);
        logoImageView = findViewById(R.id.logoImageView);
        mapButton = findViewById(R.id.mapButton);


        avatarLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            int avatarId = data.getIntExtra("avatarId", 0);
                            if (avatarId != 0) {
                                logoImageView.setImageResource(avatarId);
                            }
                        }
                    }
                });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenInGoogleMaps();
            }
        });

        logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetAvatarButton(v);
            }
        });

    }

    public void onOpenInGoogleMaps() {

        EditText teamAddress = (EditText) findViewById(R.id.locationEditText);
        String location = locationEditText.getText().toString();
        Uri gmmIntentUri = Uri.parse("http://maps.google.co.in/maps?q=" + location);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void onSetAvatarButton(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        avatarLauncher.launch(intent);

        // Does not support anymore from the video
//        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
//        startActivityForResult(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Getting the Avatar Image we show to our users
        ImageView avatarImage = (ImageView) findViewById(R.id.logoImageView);

        // Figuring out the correct image
        String drawableName = "avatar1";
        switch (data.getIntExtra("imageID", R.id.imageView2)) {
            case R.id.imageView1:
                drawableName = "avatar1";
                break;
            case R.id.imageView2:
                drawableName = "avatar2";
                break;
            case R.id.imageView3:
                drawableName = "avatar3";
                break;
            case R.id.imageView4:
                drawableName = "avatar4";
                break;
            case R.id.imageView5:
                drawableName = "avatar5";
                break;
            case R.id.imageView6:
                drawableName = "avatar6";
                break;
            default:
                drawableName = "avatar1";
                break;
        }
        int resID = getResources().getIdentifier(drawableName, "drawable", getPackageName());
        avatarImage.setImageResource(resID);
    }
}