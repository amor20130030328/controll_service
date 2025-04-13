package com.amore.springboot.explore.service.app;

import com.alibaba.fastjson.JSONObject;
import com.amore.springboot.explore.bean.App;
import com.amore.springboot.explore.mapper.AppMapper;
import com.amore.springboot.explore.utils.ADBUtils;
import com.amore.springboot.explore.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OnlineAppService extends AppControllService{

    @Autowired
    private AppMapper appMapper;
    private List<App> apps ;

    private boolean isMove;

    private String singleDevice;

    private final Map<String,Integer> appIndexMap = new HashMap<>();


    @PostConstruct
    private void init(){
        try {
            apps = appMapper.getAllApp();
        }catch (Exception e){
            System.err.println("获取app配置信息失败");
        }

        if(apps == null || apps.size() == 0){
            String s = FileUtils.readDataFromResource("json/app.json");
            apps = JSONObject.parseArray(s, App.class);
        }

        int index = 0;
        for (App app : this.apps) {
            String key = app.getAppKey();
            appIndexMap.put(key, index);
            index++;
        }
    }

    public void move(List<String>  deviceIds,boolean isMove, int up, int down){

        this.isMove = isMove;
        while(this.isMove){
            for(int i=0; i< up && this.isMove ;i++){
                ADBUtils.move(deviceIds,true);
            }
            for(int i=0; i< down && this.isMove ;i++){
                ADBUtils.move(deviceIds,true);
            }
        }
    }

    public void mutilmove(List<String> deviceIds,boolean isMove, int up, int down){

        this.isMove = isMove;
        while(this.isMove){
            for(int i=0; i< up && this.isMove ;i++){
                ADBUtils.mutilmove(deviceIds,true);
            }
            for(int i=0; i< down && this.isMove ;i++){
                ADBUtils.mutilmove(deviceIds,true);
            }
        }
    }



    @Override
    public List<App> getAllApps(){
        try{
            return  apps;
        }catch (Exception e){
            System.out.println("getAllApps");
        }
        return Collections.emptyList();
    }

    @Override
    public Map<String,Integer>  getAppIndexMap(){
        try{
            return  appIndexMap;
        }catch (Exception e){
            System.out.println("getAllApps");
        }
        return Collections.emptyMap();
    }

    public void open(String appKey){
        List<String> deviceIds = ADBUtils.getAllDeviceIds();
        Integer i = appIndexMap.get(appKey);
        App app = apps.get(i);
        deviceIds.forEach(deviceId->{
            ADBUtils.CloseApp(deviceId,app.getAppPackageName());
            ADBUtils.OpenApp(deviceId,app.getAppPackageName(),app.getAppActivity());
        });
        System.out.println(app);
    }

    public void polling(){
        this.isMove = true;
        while(this.isMove){
            getAllApps().forEach(app->{
                open(app.getAppKey());
                List<String>  deviceIds = ADBUtils.getAllDeviceIds().stream().
                        filter(deviceId->!deviceId.equals(singleDevice))
                        .collect(Collectors.toList());

                for(int j = 0 ; j < 10; j++){

                    for(int i=0; i< 4  && this.isMove ;i++){
                        ADBUtils.move(deviceIds,true);
                    }

                    for(int i=0; i< 3 && this.isMove ;i++){
                        ADBUtils.move(deviceIds,true);
                    }
                }
            });
        }

    }


}
