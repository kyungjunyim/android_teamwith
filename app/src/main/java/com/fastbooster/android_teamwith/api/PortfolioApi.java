package com.fastbooster.android_teamwith.api;

import com.fastbooster.android_teamwith.model.PortfolioSimpleVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class PortfolioApi {

    public static final String URL_STR="http://192.168.30.16:8089/api/portfolioSearch/";
    public static JSONObject getPortfolioDetail(String portfolioId) throws Exception{
        JSONObject json=null;
        URL url = new URL(URL_STR+portfolioId);

        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(1000);
        conn.setDoInput(true);

        conn.setDoOutput(true);
        int responseCode = conn.getResponseCode();
        StringBuilder sb = new StringBuilder();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            //JSON object 받아오기
            br.close();
            json = new JSONObject(sb.toString());
            conn.disconnect();
            return json;
        }
        conn.disconnect();
        return null;
    }
    public static List<PortfolioSimpleVO> getPortfolioList() throws Exception{
        URL url = new URL(URL_STR);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(1000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        int responseCode = conn.getResponseCode();
        StringBuilder sb = new StringBuilder();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            List<PortfolioSimpleVO> list=new ArrayList<>();
            JSONObject json=new JSONObject(sb.toString());

            JSONArray jarray=json.getJSONArray("portfolioList");
            PortfolioSimpleVO portfolioSimpleVO=null;
            for(int i=0;i<jarray.length();i++){
                JSONObject jo=jarray.getJSONObject(i);
                String portfolioId=jo.getString("portfolioId");
                String portfolioPic=jo.getString("portfolioPic");
                String memberId=jo.getString("memberId");
                String memberName=jo.getString("memberName");
                String portfolioTitle=jo.getString("portfolioTitle");
                String portfolioBest=jo.getString("portfolioBest");
                String projectCategory=jo.getString("projectCategoryId");
                String portfolioIntro=jo.getString("portfolioIntro");
                String portfolioUpdateDate=jo.getString("portfolioUpdateDate");
                portfolioSimpleVO=new PortfolioSimpleVO(portfolioId,portfolioPic,memberId,memberName,portfolioTitle,portfolioBest,projectCategory,
                        portfolioIntro, Date.valueOf(portfolioUpdateDate));
                list.add(portfolioSimpleVO);
            }
            br.close();
            conn.disconnect();
            return list;
        }
        conn.disconnect();
        return null;
    }
}