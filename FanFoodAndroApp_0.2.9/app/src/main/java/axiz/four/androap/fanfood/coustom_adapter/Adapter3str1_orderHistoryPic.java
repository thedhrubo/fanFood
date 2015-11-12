package axiz.four.androap.fanfood.coustom_adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import axiz.four.androap.fanfood.R;
import axiz.four.androap.fanfood.Receipt;
import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;
import axiz.four.androap.fanfood.drawerfeagmentclass.OrderHistory;
import axiz.four.androap.fanfood.emran_method.Emran_a;
import axiz.four.androap.fanfood.utilities.LongOperation;

import static android.support.v4.content.ContextCompat.startActivities;

public class Adapter3str1_orderHistoryPic extends BaseAdapter {
	
	private static  ArrayList<DataAbstractEvent> listData;
	private LayoutInflater layoutInflater;
	private Context context;
	private Emran_a emrnAObj;
    private static Handler handler;
	
	public Adapter3str1_orderHistoryPic(Context context, ArrayList<DataAbstractEvent> listData) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.adapter_3str_1pic_sublayer_orderhis, null);
			holder = new ViewHolder();
			holder.sublayer_itembg_orderhistory = (RelativeLayout) convertView.findViewById(R.id.sublayer_itembg_orderhistory);
			holder.syblayer_3str_2pic_textView1 = (TextView) convertView.findViewById(R.id.syblayer4_3str_2pic_textView1);
			holder.syblayer_3str_2pic_textView2 = (TextView) convertView.findViewById(R.id.syblayer4_3str_2pic_textView2);
			holder.syblayer_3str_2pic_textView3 = (TextView) convertView.findViewById(R.id.syblayer4_3str_2pic_textView3);
			holder.syblayer_3str_2pic_imageView1 = (ImageView) convertView.findViewById(R.id.syblayer4_3str_2pic_imageView1);
			holder.syblayer_3str_2pic_button     =(Button) convertView.findViewById(R.id.btnOrderHistryItemDelete);
            holder.syblayer_3str_2pic_viewbutton=(Button) convertView.findViewById(R.id.btnOrderHistryItemView);

           // OrderHistory myClick=new OrderHistory(holder);
           // holder.syblayer_3str_2pic_button.setOnClickListener(holder);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
        holder.syblayer_3str_2pic_viewbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //int value=listData.get(6);
                //Log.e("Button","view "+getCount()+getItemId(position));
                Long pos=getItemId(position);
                int poss=Integer.parseInt(pos.toString());
                Log.e("Tracking",pos+"  "+poss+" "+position);
                String rowId=listData.get(position).getTxt1();
                Log.e("RowId",rowId);
                OrderHistory.setOdreInfoDtl(position);
                Intent myIntent=new Intent(context,Receipt.class);
                context.startActivity(myIntent);
                //startActivities(context,myIntent,"")
                //Intent myIntent=new Intent(Adapter3str1_orderHistoryPic.,Receipt.class);
            }
        });
        holder.syblayer_3str_2pic_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String rowId=listData.get(position).getTxt1();
                Log.e("Delete Id",rowId);
                LongOperation longOperation=new LongOperation(rowId,position);
                String res=longOperation.execute().toString();
                Log.e("Loger",res);
            }
        });
		holder.syblayer_3str_2pic_textView1.setText(listData.get(position).getTxt1());
		holder.syblayer_3str_2pic_textView2.setText(""+listData.get(position).getTxt3());
		holder.syblayer_3str_2pic_textView3.setText(listData.get(position).getTxt2());
        //holder.syblayer_3str_2pic_button.setText("Delete");
        //holder.syblayer_3str_2pic_viewbutton.setText("View");
		//holder.syblayer_3str_2pic_imageView1.setImageDrawable(context.getResources().getDrawable(R.drawable.img_ic_selectmenu));

        if(listData.get(position).getTxt4().equals("3")){// Delivered
//            holder.sublayer_itembg_orderhistory.setBackgroundColor(0xaa33c833);
//            holder.sublayer_itembg_orderhistory.setBackgroundColor(0xaa33c833);
            holder.syblayer_3str_2pic_textView2.setText(listData.get(position).getTxt3()+" (Delivered)");
//            holder.syblayer_3str_2pic_textView2.setText(listData.get(position).getTxt4());
        }
		else if(listData.get(position).getTxt4().equals("2")){// Complite
//			holder.sublayer_itembg_orderhistory.setBackgroundColor(0xaa33c833);
            holder.syblayer_3str_2pic_textView2.setText(listData.get(position).getTxt3()+" (Complite)");
//            holder.syblayer_3str_2pic_textView2.setText(listData.get(position).getTxt4());
		}
		else if(listData.get(position).getTxt4().equals("1")){//Processing
//			holder.sublayer_itembg_orderhistory.setBackgroundColor(0xaac8c833);
            holder.syblayer_3str_2pic_textView2.setText(listData.get(position).getTxt3()+" (Processing)");
//            holder.syblayer_3str_2pic_textView2.setText(listData.get(position).getTxt4());
		}
		else if(listData.get(position).getTxt4().equals("0")){// Waiting
//			holder.sublayer_itembg_orderhistory.setBackgroundColor(0xaac83333);
            holder.syblayer_3str_2pic_textView2.setText(listData.get(position).getTxt3()+" (Waiting)");
//            holder.syblayer_3str_2pic_textView2.setText(listData.get(position).getTxt4());
		}
		
//		if (position % 2 == 1) {
//			convertView.setBackgroundColor(context.getResources().getColor(R.color.list_row_color1));  
//		} else {
//			convertView.setBackgroundColor(context.getResources().getColor(R.color.list_row_color2));  
//		}

//		convertView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				//holder.parent_ltut_3str1pic.setBackgroundColor(0xaa3333af);
//			}
//		});
		return convertView;
	}
	
	static class ViewHolder {
		RelativeLayout sublayer_itembg_orderhistory;
		TextView syblayer_3str_2pic_textView1;
		TextView syblayer_3str_2pic_textView2;
		TextView syblayer_3str_2pic_textView3;
		ImageView syblayer_3str_2pic_imageView1;
        Button syblayer_3str_2pic_button,syblayer_3str_2pic_viewbutton;
		boolean isSelect;
		
	}



}
