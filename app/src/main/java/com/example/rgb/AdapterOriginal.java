package com.example.rgb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
public class AdapterOriginal extends RecyclerView.Adapter<AdapterOriginal.ViewHolder> {

    private List<RgbBeen> mFruitList;
    Context mContext;

    public AdapterOriginal(List<RgbBeen> fruitList, Context mContext){
        System.out.println("进来:Adapter");
        mFruitList=fruitList;
        this.mContext= mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        System.out.println("进来:onCreateViewHolder");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.AdapterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int Position = holder.getAdapterPosition();
                final RgbBeen rgbBeen = mFruitList.get(Position);


//                Intent intent = new Intent(mContext, DetailActivity.class);
////                ByteArrayOutputStream baos = new ByteArrayOutputStream();
////                rgbBeen.getBit().compress(Bitmap.CompressFormat.PNG,100,baos);
////                byte[] byteArray = baos.toByteArray();
//
//                intent.putExtra("zhaopian",rgbBeen.getDatas());
//                intent.putExtra("rgb",rgbBeen.getRgb());
//                intent.putExtra("difference",rgbBeen.getDifference());
//                intent.putExtra("good",rgbBeen.getGood());
//                intent.putExtra("score",rgbBeen.getScore());
//                mContext.startActivity(intent);


            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        System.out.println("进来:onBindViewHolder");
        RgbBeen rgbBeen = mFruitList.get(position);
        //viewHolder.zhaopian.setImageResource(fruit.getZhaopian());
        viewHolder.zhaopian.setImageBitmap(rgbBeen.getBit());
        viewHolder.rgb.setText(rgbBeen.getRgb());
        viewHolder.difference.setText(rgbBeen.getDifference());
        viewHolder.good.setText(rgbBeen.getGood());
        viewHolder.score.setText(rgbBeen.getScore());
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View AdapterView;
        ImageView zhaopian;
        TextView rgb,difference,good,score;

        public ViewHolder(View view) {
            super(view);
            AdapterView = view;
            zhaopian = itemView.findViewById(R.id.face);
//            rgb = itemView.findViewById(R.id.rgb);
//            difference = itemView.findViewById(R.id.difference);
//            good = itemView.findViewById(R.id.good);
//            score = itemView.findViewById(R.id.score);
        }
    }
}
