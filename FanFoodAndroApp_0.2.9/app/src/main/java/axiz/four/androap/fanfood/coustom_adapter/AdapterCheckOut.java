package axiz.four.androap.fanfood.coustom_adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import axiz.four.androap.fanfood.CheckOut;
import axiz.four.androap.fanfood.R;
import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;
import axiz.four.androap.fanfood.emran_method.Emran_a;
import axiz.four.androap.fanfood.sqlitedb.DB_SQLiteDB_Adapter;

/**
 * Created by emran on 4/8/15.
 */
public class AdapterCheckOut extends BaseAdapter {

    public ArrayList<DataAbstractEvent> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private Emran_a emrnAObj;

    public AdapterCheckOut(Context context, ArrayList<DataAbstractEvent> listData) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_checkout, null);
            holder = new ViewHolder();
            holder.syblayer_3str_2pic_textView1 = (TextView) convertView.findViewById(R.id.syblayer_3str_2pic_textView1);
            holder.syblayer_3str_2pic_textView2 = (TextView) convertView.findViewById(R.id.syblayer_3str_2pic_textView2);
            holder.syblayer_3str_2pic_textView3 = (TextView) convertView.findViewById(R.id.syblayer_3str_2pic_textView3);

            holder.syblayer_3str_2pic_imageView1 = (ImageView) convertView.findViewById(R.id.syblayer_3str_2pic_imageView1);
            holder.syblayer_3str_2pic_imageView2 = (ImageView) convertView.findViewById(R.id.syblayer_3str_2pic_imageView2);
            holder.syblayer_3str_2pic_imageView3 = (ImageView) convertView.findViewById(R.id.checkOut_cancle_img);

            holder.position = position;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final int posi = holder.position;

        holder.syblayer_3str_2pic_imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, ""+position+", - "+posi, Toast.LENGTH_SHORT).show();
//                listData.remove(position);
//                notifyDataSetChanged();

                AlertDialog.Builder adb=new AlertDialog.Builder(context);
                adb.setTitle("");
                adb.setMessage("Do you want to remove this item?");

                final int positionToRemove = position;
                adb.setNegativeButton("No", null);
                adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                        float tmpTotalpris = CheckOut.tatalPrice;
//                        tmpTotalpris = (tmpTotalpris - (Float.parseFloat(listData.get(position).getTxt8())));
                            tmpTotalpris = (tmpTotalpris - (Float.parseFloat(listData.get(position).getTxt13())));
                            CheckOut.tatalPrice= tmpTotalpris;
                            CheckOut.checkout_totalPrice.setText("$" +tmpTotalpris );
                        }catch(Exception ex){

                        }
                        if(deleteItemSQL(listData.get(positionToRemove).getTxt3())){
                            listData.remove(positionToRemove);
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(context,"Item not delete.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                adb.show();
            }
        });

//        holder.syblayer_3str_2pic_textView1.setText(listData.get(position).getTxt4()+"("+listData.get(position).getTxt7()+")");
//        holder.syblayer_3str_2pic_textView2.setText(listData.get(position).getTxt8());
//        holder.syblayer_3str_2pic_textView3.setText(" "+listData.get(position).getTxt5());
        holder.syblayer_3str_2pic_textView1.setText(listData.get(position).getTxt4());
//        holder.syblayer_3str_2pic_textView2.setText(" ("+listData.get(position).getTxt5()+" X "+listData.get(position).getTxt7()+")");
        holder.syblayer_3str_2pic_textView2.setText(" ("+listData.get(position).getTxt5()+" X "+listData.get(position).getTxt14()+")");
        //holder.syblayer_3str_2pic_textView3.setText(" $"+listData.get(position).getTxt8());//Single item price
        holder.syblayer_3str_2pic_textView3.setText(" $"+listData.get(position).getTxt13());//sum of same item price


        holder.syblayer_3str_2pic_imageView1.setImageDrawable(context.getResources().getDrawable(R.drawable.img_simages));

//		if (position % 2 == 1) {
//			convertView.setBackgroundColor(context.getResources().getColor(R.color.list_row_color1));
//		} else {
//			convertView.setBackgroundColor(context.getResources().getColor(R.color.list_row_color2));
//		}

        return convertView;
    }

    public boolean deleteItemSQL(String ItemID) {
        DB_SQLiteDB_Adapter DBObj= new DB_SQLiteDB_Adapter(context);
        if(DBObj.deleteItemByID(ItemID)>0){
            return true;
        }else
        return false;
    }

    static class ViewHolder {
        int position;
        TextView syblayer_3str_2pic_textView1;
        TextView syblayer_3str_2pic_textView2;
        TextView syblayer_3str_2pic_textView3;

        ImageView syblayer_3str_2pic_imageView1;
        ImageView syblayer_3str_2pic_imageView2;
        ImageView syblayer_3str_2pic_imageView3;
    }

}
