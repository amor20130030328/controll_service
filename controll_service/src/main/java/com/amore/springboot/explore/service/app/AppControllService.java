package com.amore.springboot.explore.service.app;

import com.amore.springboot.explore.bean.App;
import com.amore.springboot.explore.utils.ADBUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class AppControllService{

    private boolean isMove;

    private ExecutorService executor;

    public AppControllService(){
        this.executor = Executors.newCachedThreadPool();
    }

    public abstract List<App> getAllApps();

    public abstract Map<String,Integer> getAppIndexMap();

    public void installApp(String deviceId){

        ADBUtils.installApp(deviceId);
    }

    public void casting(String deviceId){

        if("all".equals(deviceId)){
            List<String> deviceIds = ADBUtils.getAllDeviceIds();
            ADBUtils.casting(deviceIds);
        }else{
            ADBUtils.casting(Arrays.asList(deviceId));
        }

    }

    public void move(List<String>  deviceIds,boolean isMove, int up, int down){

        this.isMove = isMove;
        while(this.isMove){
            try{
                // 提交向上移动的任务
                for (int i = 0; i < up && this.isMove; i++) {
                    ADBUtils.mutilmove(deviceIds, true);
                    Thread.sleep(3000);
                }

                // 提交向下移动的任务
                for (int i = 0; i < down && this.isMove; i++) {
                    ADBUtils.mutilmove(deviceIds, false);
                    Thread.sleep(3000);
                }

            }catch (Exception e){
                System.err.println("move fail");
                e.printStackTrace();
            }

        }
    }

    public void open(String appKey){
        List<String> deviceIds = ADBUtils.getAllDeviceIds();
        Integer i = getAppIndexMap().get(appKey);
        App app = getAllApps().get(i);
        deviceIds.forEach(deviceId->{
            ADBUtils.CloseApp(deviceId,app.getAppPackageName());
            ADBUtils.OpenApp(deviceId,app.getAppPackageName(),app.getAppActivity());
        });
        System.out.println(app);
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
}
