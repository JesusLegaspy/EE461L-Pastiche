package com.pastiche.pastiche.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.pastiche.pastiche.R;

public class ImageDetailActivity extends AppCompatActivity {

    private TextView username = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        username = (TextView) findViewById(R.id.txt_detailActivity_user_name);
        TextView numViews = (TextView) findViewById(R.id.txt_detailActivity_num_views);
        ImageView userIcon = (ImageView) findViewById(R.id.img_detailActivity_user_icon);


        username.setText("Aria Pahlavan");
        numViews.setText("18 views");

        username.setOnClickListener(v-> launchUserPage());

        userIcon.setOnClickListener(v->launchUserPage());
    }

    private void launchUserPage() {
        //TODO fires up the activity to show user's page
        Intent intent = new Intent();
        startActivity(intent);
        finish();
    }


}
