package com.amore.springboot.explore.service;

import com.amore.springboot.explore.bean.Device;
import com.amore.springboot.explore.config.ComponentBase;
import com.amore.springboot.explore.mapper.DeviceMapper;
import com.amore.springboot.explore.service.device.DeviceControllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private ComponentBase componentBase;

    private DeviceControllService deviceControllService;

    @PostConstruct
    private void init(){
        deviceControllService = componentBase.getDeviceService();
    }

    public List<Device> getAllDevices(){
        List<Device> devices = deviceControllService.getAllDevices();
        return devices;
    }

    public void insertDevice(Device device){
        deviceControllService.insertDevice(device);
    }

    public void casting(Device device){
        deviceControllService.casting(device);
    }

    public void controllLight(String deviceId, boolean isUp){
        deviceControllService.controllLight(deviceId, isUp);
    }


}
