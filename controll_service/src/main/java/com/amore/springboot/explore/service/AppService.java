package com.amore.springboot.explore.service;

import com.amore.springboot.explore.bean.App;
import com.amore.springboot.explore.config.ComponentBase;
import com.amore.springboot.explore.service.app.AppControllService;
import com.amore.springboot.explore.utils.ADBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppService {


    private AppControllService appControllService;

    @Autowired
    private ComponentBase componentBase;

    private boolean isMove;

    private String singleDevice;

    @PostConstruct
    private void init(){
        appControllService = componentBase.getAppService();
    }

    public void move(List<String>  deviceIds,boolean isMove, int up, int down){
        appControllService.move(deviceIds, isMove, up, down);
    }

    public void mutilmove(List<String> deviceIds,boolean isMove, int up, int down){
       appControllService.mutilmove(deviceIds, isMove, up, down);
    }

    public void installApp(String deviceId){
        appControllService.installApp(deviceId);
    }


    public List<App> getAllApps(){
       return appControllService.getAllApps();
    }

    public void open(String appKey){
        appControllService.open(appKey);
    }

    public void setSingleDevice(String deviceId){
        singleDevice = deviceId;
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

    public void casting(String deviceId){
        appControllService.casting(deviceId);
    }
}
