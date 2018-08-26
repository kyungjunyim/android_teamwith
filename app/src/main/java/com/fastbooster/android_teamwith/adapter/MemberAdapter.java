package com.fastbooster.android_teamwith.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fastbooster.android_teamwith.R;
import com.fastbooster.android_teamwith.model.MemberSearchVO;
import com.fastbooster.android_teamwith.share.ApplicationShare;
import com.fastbooster.android_teamwith.task.ImageTask;
import com.fastbooster.android_teamwith.viewholder.MemberViewHolder;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MemberAdapter extends BaseAdapter {
    static final String TAG = "member data...";

    Context context;
    List<MemberSearchVO> data;
    LayoutInflater layoutInflater;
    int flag;
    public MemberAdapter(Context context, List<MemberSearchVO> data) {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);


    }
    public MemberAdapter(Context context, List<MemberSearchVO> data,int flag) {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
        this.flag=flag;

    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MemberViewHolder vh;

        if (view != null) {
            vh = (MemberViewHolder) view.getTag();
        }
        else if(flag!=0){
            view=layoutInflater.inflate(R.layout.best_member_info_layout,null );
            vh = new MemberViewHolder();
            vh.memberName = view.findViewById(R.id.k_tv_memberName);
            vh.praiseCnt = view.findViewById(R.id.k_tv_praiseCnt);
            vh.memberPic = view.findViewById(R.id.k_iv_memberPic);
            vh.memberRole=view.findViewById(R.id.k_tv_memberRole);
            view.setTag(vh);

            vh.memberName.setText(data.get(i).getMemberName());
            vh.praiseCnt.setText("칭찬 "+data.get(i).getTotalPraiseCnt()+"회");
            vh.memberPic.setTag(data.get(i).getMemberPic());
            vh.memberRole.setText((String)ApplicationShare.roleList.get(data.get(i).getRoleId()));

            ImageTask imgTask = new ImageTask(context);
            imgTask.execute(vh.memberPic);
        }
        else {
            view = layoutInflater.inflate(R.layout.member_info_layout, null);

            vh = new MemberViewHolder();
            vh.memberName = view.findViewById(R.id.jmemberName1);
            vh.memberRole = view.findViewById(R.id.jmemberRole1);
            vh.memberPic = view.findViewById(R.id.jmemberPic1);

            view.setTag(vh);

            vh.memberName.setText(data.get(i).getMemberName());
            vh.memberRole.setText((String)ApplicationShare.roleList.get(data.get(i).getRoleId()));
            vh.memberPic.setTag(data.get(i).getMemberPic());

            ImageTask imgTask = new ImageTask(context);
            imgTask.execute(vh.memberPic);
        }




        return view;
    }


}
