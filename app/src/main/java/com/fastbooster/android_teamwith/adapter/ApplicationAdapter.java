package com.fastbooster.android_teamwith.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fastbooster.android_teamwith.R;
import com.fastbooster.android_teamwith.model.InterviewVO;
import com.fastbooster.android_teamwith.model.MyApplicationVO;
import com.fastbooster.android_teamwith.share.ApplicationShare;
import com.fastbooster.android_teamwith.task.ApplicationCancelTask;
import com.fastbooster.android_teamwith.task.ImageTask;
import com.fastbooster.android_teamwith.viewholder.ApplicationViewHolder;

import java.util.List;
import java.util.Map;

public class ApplicationAdapter extends BaseAdapter {
    static final String TAG = "member data...";

    Context context;
    List<MyApplicationVO> applicationList;
    Map<String, List<InterviewVO>> interviewMap;
    LayoutInflater layoutInflater;

    public ApplicationAdapter(Context context, Object[] data) {
        this.context = context;
        this.applicationList = (List<MyApplicationVO>) data[0];
        this.interviewMap = (Map<String, List<InterviewVO>>) data[1];
        Log.v("myApplication adapter size", applicationList.size() + "");
        this.layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return applicationList.size();
    }

    @Override
    public Object getItem(int i) {
        return applicationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ApplicationViewHolder vh;
        final int fi = i;
        if (view != null) {
            vh = (ApplicationViewHolder) view.getTag();
        } else {
            view = layoutInflater.inflate(R.layout.application_layout, null);

            vh = new ApplicationViewHolder();
            vh.teamPic = view.findViewById(R.id.teamPic);
            vh.teamName = view.findViewById(R.id.teamName);
            vh.recruitingRole = view.findViewById(R.id.recruitingRole);
            vh.regDate = view.findViewById(R.id.regDate);
            vh.status = view.findViewById(R.id.status);
            vh.cancelBtn = view.findViewById(R.id.cancelBtn);
            vh.interviewBtn = view.findViewById(R.id.interviewBtn);

            view.setTag(vh);
        }
        Log.v("myApplication data adapter", applicationList.get(i).toString());
        vh.teamPic.setTag(applicationList.get(i).getTeamPic());
        ImageTask imgTask = new ImageTask(context);
        imgTask.execute(vh.teamPic);

        vh.teamName.setText(applicationList.get(i).getTeamName());
        vh.recruitingRole.setText((String) ApplicationShare.roleList.
                get(applicationList.get(i).getRoleId()));
        vh.regDate.setText(applicationList.get(i).getApplicationDate().substring(0, 10));

        vh.status.setText((String) ApplicationShare.applicationStatus.get
                (applicationList.get(i).getApplicationStatus()));


        vh.cancelBtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (vh.status.getText().toString().equals("취소")) {
                    Toast.makeText(context, "이미 취소 상태입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context,
                            android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

                    dialog.setTitle("지원 취소");
                    dialog.setMessage("정말 [ " + applicationList.get(fi).getTeamName() + " ] 지원을 취소하시겠습니까?");
                    dialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ApplicationCancelTask act = new ApplicationCancelTask(context, vh.status);
                            act.execute(applicationList.get(fi).getApplicationId());
                        }
                    });
                    dialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    dialog.show();
                }
            }
        });

        vh.interviewBtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogB = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                final AlertDialog dialog=dialogB.create();
                final View contentView = View.inflate(context, R.layout.application_content_layout, null);
                TextView freeWriting = contentView.findViewById(R.id.freeWriting);
                freeWriting.setText(applicationList.get(fi).getApplicationFreewriting());
                ListView listView = contentView.findViewById(R.id.interviewListView);
                List<InterviewVO> itvList = interviewMap.get(applicationList.get(fi).getApplicationId());
                listView.setAdapter(new InterviewAdapter(context, itvList));
                Button btnClose=contentView.findViewById(R.id.btnClose);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.setView(contentView);
                dialog.show();
            }
        });


        return view;
    }

}