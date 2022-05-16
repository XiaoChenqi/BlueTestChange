package com.example.rgb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bluetestchange.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private RgbBeenNew mFruit;
    Context mContext;

    public Adapter(RgbBeenNew fruit, Context mContext){
        System.out.println("进来:Adapter");
        mFruit=fruit;
        this.mContext= mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        System.out.println("进来:onCreateViewHolder");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
//        holder.AdapterView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final int Position = holder.getAdapterPosition();
//                final RgbBeen rgbBeen = mFruitList.get(Position);
//
//
////                Intent intent = new Intent(mContext, DetailActivity.class);
//////                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//////                rgbBeen.getBit().compress(Bitmap.CompressFormat.PNG,100,baos);
//////                byte[] byteArray = baos.toByteArray();
////
////                intent.putExtra("zhaopian",rgbBeen.getDatas());
////                intent.putExtra("rgb",rgbBeen.getRgb());
////                intent.putExtra("difference",rgbBeen.getDifference());
////                intent.putExtra("good",rgbBeen.getGood());
////                intent.putExtra("score",rgbBeen.getScore());
////                mContext.startActivity(intent);
//
//
//            }
//        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        System.out.println("进来:onBindViewHolder");
        RgbBeenNew rgbBeen = mFruit;
        if(rgbBeen.getmBit()!=null){
            viewHolder.zhaopian.setImageBitmap(rgbBeen.getmBit());
        }else{
            Glide.with(mContext).load(rgbBeen.getFile()).into(viewHolder.zhaopian);
        }

        viewHolder.colorTv.setText(rgbBeen.getColorGet());
        viewHolder.lightTv.setText(rgbBeen.getLightGet());
        viewHolder.artTv.setText(rgbBeen.getArtGet());

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View AdapterView;
        ImageView zhaopian;
        TextView colorTv,lightTv,artTv;

        public ViewHolder(View view) {
            super(view);
            AdapterView = view;
            zhaopian = itemView.findViewById(R.id.face);
            colorTv = itemView.findViewById(R.id.colorTv);
            lightTv = itemView.findViewById(R.id.lightTv);
            artTv = itemView.findViewById(R.id.artTv);

        }
    }
}
