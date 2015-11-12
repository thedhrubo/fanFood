package axiz.four.androap.fanfood.coustom_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import axiz.four.androap.fanfood.R;
import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;
import axiz.four.androap.fanfood.emran_method.Emran_a;

/**
 * Created by emran on 4/8/15.
 */
public class AdapterConcession3str2Pic extends BaseAdapter {

    public ArrayList<DataAbstractEvent> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private Emran_a emrnAObj;

    public AdapterConcession3str2Pic(Context context, ArrayList<DataAbstractEvent> listData) {
        this.listData = listData;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        emrnAObj = new Emran_a(context);
    }



    @Override
    public int getCount() {
        return this.listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int colorCode = 0x00F0F0F0;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_concession, null);
            holder = new ViewHolder();
            holder.conRowLayOut = (RelativeLayout) convertView.findViewById(R.id.conRowLayOut);

            holder.syblayer_3str_2pic_textView1 = (TextView) convertView.findViewById(R.id.syblayer_3str_2pic_textView1);
            holder.syblayer_3str_2pic_textView2 = (TextView) convertView.findViewById(R.id.syblayer_3str_2pic_textView2);
            holder.syblayer_3str_2pic_textView3 = (TextView) convertView.findViewById(R.id.syblayer_3str_2pic_textView3);
            holder.syblayer_3str_2pic_textView3.setVisibility(TextView.GONE);

            holder.syblayer_3str_2pic_imageView1 = (ImageView) convertView.findViewById(R.id.syblayer_3str_2pic_imageView1);
            holder.syblayer_3str_2pic_imageView2 = (ImageView) convertView.findViewById(R.id.syblayer_3str_2pic_imageView2);
            holder.syblayer_3str_2pic_imageView3 = (ImageView) convertView.findViewById(R.id.syblayer_3str_2pic_imageView3);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(listData.get(position).getTxt6().equals("Y")){
            holder.conRowLayOut.setBackgroundColor(colorCode);
//        }else{
//            holder.conRowLayOut.setBackgroundColor(0xf0f0f0);
        }

        holder.syblayer_3str_2pic_textView1.setText(listData.get(position).getTxt1());
        holder.syblayer_3str_2pic_textView2.setText(listData.get(position).getTxt2()+"("+listData.get(position).getTxt3()+")");
        holder.syblayer_3str_2pic_textView3.setText("");

        holder.syblayer_3str_2pic_imageView1.setImageDrawable(context.getResources().getDrawable(R.drawable.img_simages));

//		if (position % 2 == 1) {
//			convertView.setBackgroundColor(context.getResources().getColor(R.color.list_row_color1));
//		} else {
//			convertView.setBackgroundColor(context.getResources().getColor(R.color.list_row_color2));
//		}

        return convertView;
    }

    static class ViewHolder {
        RelativeLayout conRowLayOut;

        TextView syblayer_3str_2pic_textView1;
        TextView syblayer_3str_2pic_textView2;
        TextView syblayer_3str_2pic_textView3;

        ImageView syblayer_3str_2pic_imageView1;
        ImageView syblayer_3str_2pic_imageView2;
        ImageView syblayer_3str_2pic_imageView3;
    }

}
