package com.fastbooster.android_teamwith;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.fastbooster.android_teamwith.share.ApplicationShare;
import com.fastbooster.android_teamwith.task.PologTask;

public class MyPologActivity extends BarActivity {
    String[] praiseKeyList;

    String[] praiseList;

    boolean[] praiseChecked;

    private FrameLayout frame;
    private LayoutInflater inflater;
    private View profileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_polog);

        ImageButton settingBtn = findViewById(R.id.settingBtn);
        Button editBtn = findViewById(R.id.profileEditBtn);

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MyPologActivity.this, SettingActivity.class);
                startActivity(in);
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MyPologActivity.this, ProfileEditActivity.class);
                finish();
                startActivity(in);
            }
        });
        praiseList = new String[ApplicationShare.praiseList.size()];
        praiseKeyList = new String[ApplicationShare.praiseList.size()];
        praiseChecked = new boolean[praiseList.length];
        int i = 0;
        for (Object s : ApplicationShare.praiseList.keySet()) {
            String k = (String) s;
            praiseList[i] = (String) ApplicationShare.praiseList.get(k);
            praiseKeyList[i++] = k;
        }

        final Button kbtnProfile = (Button) findViewById(R.id.k_btn_Profile);
        final Button kbtnPortfolio = (Button) findViewById(R.id.k_btn_Portfolio);
        Button praiseBtn = findViewById(R.id.praiseBtn);
        frame = (FrameLayout) findViewById(R.id.k_fl_portfolioList);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        profileView = inflater.inflate(R.layout.profile_layout, frame, false);

        PologTask pt = new PologTask(MyPologActivity.this, profileView);
        pt.execute(memberId);//멤버아이디 전달 받아서 넣기


        kbtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kbtnProfile.setTextColor(Color.parseColor("red"));
                kbtnPortfolio.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
                changeView(0);
            }
        });
        kbtnPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kbtnProfile.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
                kbtnPortfolio.setTextColor(Color.parseColor("red"));
                changeView(1);
            }
        });
        changeView(0);
        kbtnProfile.setTextColor(Color.parseColor("red"));
    }

    private void changeView(int index) {


        if (frame.getChildCount() > 0) {
            frame.removeViewAt(0);
        }
//        View view=null;
        switch (index) {
            case 0:
//                view=inflater.inflate(R.layout.profile_layout,frame,false);
                if (profileView != null) {
                    frame.addView(profileView);
                } else {
                    profileView = inflater.inflate(R.layout.profile_layout, frame, false);

                    frame.addView(profileView);
                }
                break;
            case 1:
                //포트폴리오 레이아웃줄것
                Bundle bundle = new Bundle(1);
                bundle.putString("memberId", memberId);
                FragmentManager fm = getFragmentManager();

                PortfolioFragment pf = new PortfolioFragment();
                pf.setArguments(bundle);
                FragmentTransaction tr = fm.beginTransaction();
                tr.add(R.id.k_fl_portfolioList, pf);

                tr.commit();

                // Toast.makeText(getApplicationContext(), "프래그먼트", Toast.LENGTH_SHORT).show();
                break;

        }

    }

}
