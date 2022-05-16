package com.example.rgb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bluetestchange.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.rgb1)
    TextView rgb1;
    @BindView(R.id.difference1)
    TextView difference1;
    @BindView(R.id.good1)
    TextView good1;
    @BindView(R.id.score1)
    TextView score1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        initshou();


    }

    private void initshou() {

        byte[] byteArray = getIntent().getByteArrayExtra("zhaopian");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

        String rgb = getIntent().getStringExtra("rgb");
        String difference = getIntent().getStringExtra("difference");
        String good = getIntent().getStringExtra("good");
        String score = getIntent().getStringExtra("score");

        imageView.setImageBitmap(bitmap);
        rgb1.setText("rgb："+rgb);
        difference1.setText("difference1："+difference);
        good1.setText("good1："+good);
        score1.setText("score1："+score);
    }
}
