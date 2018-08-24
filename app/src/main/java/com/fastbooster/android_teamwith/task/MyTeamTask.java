package com.fastbooster.android_teamwith.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridLayout;
import android.widget.GridView;

import com.fastbooster.android_teamwith.MyTeamActivity;
import com.fastbooster.android_teamwith.R;
import com.fastbooster.android_teamwith.adapter.MyTeamAdapter;
import com.fastbooster.android_teamwith.api.MyTeamApi;
import com.fastbooster.android_teamwith.api.TeamDetailApi;
import com.fastbooster.android_teamwith.model.TeamSimpleVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyTeamTask extends AsyncTask<Void,Void,JSONObject> {
    private final Context context;

    public MyTeamTask(Context context) {
        this.context=context;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        List<TeamSimpleVO> data=new ArrayList<TeamSimpleVO>();
        try{
            JSONArray myTeamList=json.getJSONArray("myTeamList");
            for(int i=0;i<myTeamList.length();i++){
                TeamSimpleVO teamSimpleVO=new TeamSimpleVO(myTeamList.getJSONObject(i));
                data.add(teamSimpleVO);
            }

            if(context instanceof MyTeamActivity){
                MyTeamActivity myTeamActivity=(MyTeamActivity) context;
                GridView myTeamGridView=(GridView) myTeamActivity.findViewById(R.id.myTeamGridView);

                MyTeamAdapter adapter=new MyTeamAdapter(context,data);
                myTeamGridView.setAdapter(adapter);

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        super.onPostExecute(json);
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
        JSONObject json=null;
        try {
            json = MyTeamApi.getMyTeam();
        }catch(Exception e){
            e.printStackTrace();
        }
        return json;

    }
}