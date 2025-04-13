package com.amore.springboot.explore.config;

import com.amore.springboot.explore.service.app.AppControllService;
import com.amore.springboot.explore.service.app.LocalAppService;
import com.amore.springboot.explore.service.app.OnlineAppService;
import com.amore.springboot.explore.service.device.DeviceControllService;
import com.amore.springboot.explore.service.device.LocalDeviceService;
import com.amore.springboot.explore.service.device.OnlineDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ComponentBase {

    @Value("${env.online}")
    private boolean envIsOnline;

    @Autowired
    private OnlineAppService onlineAppService;

    @Autowired
    private LocalAppService localAppService;

    @Autowired
    private OnlineDeviceService onlineDeviceService;

    @Autowired
    private LocalDeviceService localDeviceService;

    public AppControllService getAppService(){
        if(getEnvIsOnline()){
            return onlineAppService;
        }else{
            return localAppService;
        }
    }

    public DeviceControllService getDeviceService(){
        if(getEnvIsOnline()){
            return onlineDeviceService;
        }else{
            return localDeviceService;
        }
    }

    public boolean getEnvIsOnline(){
        return envIsOnline;
    }

}
