package axiz.four.androap.fanfood.coustom_adapter;

/**
 * Created by emran on 4/9/15.
 */

import android.widget.BaseAdapter;

        import java.util.ArrayList;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.ImageView;
        import android.widget.TextView;

import axiz.four.androap.fanfood.R;
import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;
import axiz.four.androap.fanfood.emran_method.Emran_a;


public class Adapter2str1Pic extends BaseAdapter {

    private ArrayList<DataAbstractEvent> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private Emran_a emrnAObj;

    public Adapter2str1Pic(Context context,
                           ArrayList<DataAbstractEvent> listData) {
        this.listData = listData;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        emrnAObj = new Emran_a(context);
    }

    @Override
    public int getCount() {
        return listData.size();
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
        if (convertView == null) {
            convertView = layoutInflater.inflate(
                    R.layout.adapter_2str_1pic_sublayer, null);
            holder = new ViewHolder();
            holder.syblayer_2str_1pic_textView1 = (TextView) convertView
                    .findViewById(R.id.syblayer_3str_2pic_textView1);
            holder.syblayer_2str_1pic_textView2 = (TextView) convertView
                    .findViewById(R.id.syblayer_3str_2pic_textView2);

            holder.syblayer_2str_1pic_imageView1 = (ImageView) convertView
                    .findViewById(R.id.syblayer_2str_1pic_imageView1);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.syblayer_2str_1pic_textView1.setText(listData.get(position)
                .getTxt1());
        holder.syblayer_2str_1pic_textView2.setText(listData.get(position)
                .getTxt2());

        //holder.syblayer_2str_1pic_imageView1.setImageDrawable(context.getResources().getDrawable(R.drawable.img_simages));

        // if (position % 2 == 1) {
        // convertView.setBackgroundColor(context.getResources().getColor(R.color.list_row_color1));
        // } else {
        // convertView.setBackgroundColor(context.getResources().getColor(R.color.list_row_color2));
        // }

        return convertView;
    }

    static class ViewHolder {
        TextView syblayer_2str_1pic_textView1;
        TextView syblayer_2str_1pic_textView2;

        ImageView syblayer_2str_1pic_imageView1;

    }

}
