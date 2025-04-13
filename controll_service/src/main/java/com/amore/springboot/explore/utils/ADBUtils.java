package com.amore.springboot.explore.utils;



import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ADBUtils {

    private static final String ADB_DEVICE_IDS = "adb devices";
    private static final String CLOSE_APP = "adb -s {device_id} shell am force-stop {package}";

    private static final String CONTROLL_LIGHT = "adb -s {device_id} shell input keyevent {num}";
    private static final String OPEN_APP = "adb -s {device_id} shell am start {package}/{activity}";

    private static final String qtPath = "D:\\environment\\QtScrcpy-win-x64-v2.1.2\\QtScrcpy.exe";
    private static final String NginxPath = "D:\\environment\\nginx-1.16.1\\run.bat";

    private static final String AppSize ="adb -s {device_id} shell wm size";
    private static final String MoveApp ="adb -s {device_id}  shell input swipe {start_x} {start_y} {end_x} {end_y} 400";

    private static final String InstallApp="adb -s {device_id} install {apk}";
    private static final String Casting="scrcpy -s device_id --window-title device_id --no-audio --turn-screen-off";
    public static List<String> getAllDeviceIds(){
        String res  = Processor.runSync(ADB_DEVICE_IDS);
        List<String> deviceIds = Arrays.stream(res.split("\n")).filter((item) -> {
            return item.split("\t").length == 2;
        }).filter(x->x.contains("device"))
                .map(x->x.split("\t")[0])
                .collect(Collectors.toList());
        return deviceIds;
    }

    public static void CloseApp(String deviceId,String packageName){
        String cmd = CLOSE_APP.replace("{device_id}", deviceId)
                .replace("{package}", packageName);
        System.out.println(cmd);
        Processor.runSync(cmd);
    }

    public static void OpenApp(String deviceId,String packageName,String activity){
        String cmd = OPEN_APP.replace("{device_id}", deviceId)
                .replace("{package}", packageName)
                        .replace("{activity}",activity);
        Processor.runSync(cmd);
    }



    public static void startQt(){
        String run = Processor.runSync("tasklist ");
        if(!run.contains("QtScrcpy.exe")){
            Processor.run(qtPath);
        }else{
            System.out.println("qtscrpy already start...");
        }

    }

    public static void startNginx(){
        String run = Processor.runSync("tasklist ");
        if(!run.contains("nginx.exe")){
            Processor.run(NginxPath);
            System.out.println("nginx starting...");
        }else{
            System.out.println("nginx already start...");
        }

    }

    public static void move(String deviceId){
        String stdout_str = Processor.runSync(AppSize.replace("{device_id}",deviceId));
        String x_size = stdout_str.split(":")[1].split("x")[0].trim();
        String y_size = stdout_str.split(":")[1].split("x")[1].trim();

        double start_x = StringUtils.parse2Int(x_size) * 0.85;
        double start_y = StringUtils.parse2Int(y_size) * 0.85;
        double end_x = StringUtils.parse2Int(x_size) * 0.85;
        double end_y = StringUtils.parse2Int(y_size) * 0.4;

        String cmd = MoveApp.replace("{device_id}",deviceId).replace("{start_x}", start_x+"")
                .replace("{start_y}", start_y+"")
                .replace("{end_x}", end_x+"")
                .replace("{end_y}", end_y+"");
        System.out.println(cmd);
        Processor.runSync(cmd);

    }

    public static void move(List<String> deviceIds,boolean up){

        deviceIds.forEach(deviceId->{
            String stdout_str = Processor.runSync(AppSize.replace("{device_id}",deviceId));
            String x_size = stdout_str.split(":")[1].split("x")[0].trim();
            String y_size = stdout_str.split(":")[1].split("x")[1].trim();
            int start_x = 0;
            int start_y = 0;
            int end_x = 0;
            int end_y = 0;
            if(up) {
                start_x = StringUtils.mutil(x_size,  0.85) ;
                start_y = StringUtils.mutil(y_size,0.85) ;
                end_x =  StringUtils.mutil(x_size,0.85) ;
                end_y =  StringUtils.mutil(y_size,0.4) ;
            }else {
                end_x =  StringUtils.mutil(x_size,0.85) ;
                end_y =  StringUtils.mutil(y_size,0.85) ;
                start_x = StringUtils.mutil(x_size,0.85) ;
                start_y =  StringUtils.mutil(y_size,0.4);
            }
            String cmd = MoveApp.replace("{device_id}",deviceId).replace("{start_x}", start_x+"")
                    .replace("{start_y}", start_y+"")
                    .replace("{end_x}", end_x+"")
                    .replace("{end_y}", end_y+"");
            System.out.println(cmd);
            Processor.runSync(cmd);
        });
    }

    public static void mutilmove(List<String> deviceIds,boolean up){

        move(deviceIds,up);

        deviceIds.forEach(deviceId->{
            String stdout_str = Processor.runSync(AppSize.replace("{device_id}",deviceId));
            String x_size = stdout_str.split(":")[1].split("x")[0].trim();
            String y_size = stdout_str.split(":")[1].split("x")[1].trim();
            int start_x = 0;
            int start_y = 0;
            int end_x = 0;
            int end_y = 0;
            if(up) {
                start_x = StringUtils.mutil(x_size,  0.5) ;
                start_y = StringUtils.mutil(y_size,0.6) ;
                end_x =  StringUtils.mutil(x_size,0.5) ;
                end_y =  StringUtils.mutil(y_size,0.25) ;
            }else {
                end_x =  StringUtils.mutil(x_size,0.5) ;
                end_y =  StringUtils.mutil(y_size,0.25) ;
                start_x = StringUtils.mutil(x_size,0.5) ;
                start_y =  StringUtils.mutil(y_size,0.6);
            }
            String cmd = MoveApp.replace("{device_id}",deviceId).replace("{start_x}", start_x+"")
                    .replace("{start_y}", start_y+"")
                    .replace("{end_x}", end_x+"")
                    .replace("{end_y}", end_y+"");
            System.out.println(cmd);
            Processor.run(cmd);
        });
    }

    public static void installApp(String deviceId){
        String path = "./apk";

        List<String> list = FileUtils.listFiles(path);
        list.forEach(fileName->{
            String cmd = InstallApp.replace("{device_id}", deviceId)
                    .replace("{apk}", path + "//" + fileName);
            System.out.println(cmd);
            Processor.runSync(cmd);
        });

    }

    public static void casting(List<String> deviceIds){
        for(int i = 0 ; i <deviceIds.size();i++){
            String deviceId = deviceIds.get(i);
            String cmd = Casting.replaceAll("device_id", deviceId);
            System.out.println(cmd);
            Processor.run(cmd);
        }

    }

    public static void casting(String deviceId, String title){
        String cmd = "scrcpy -s " + deviceId + "  --max-size 700 --window-width 500 --window-title " + deviceId ;
        Processor.runSync(cmd);
    }

    public static void casting(String deviceId){
        String cmd = "scrcpy -s " + deviceId + "  --max-size 700 --window-width 500 --window-title " + deviceId ;
        Processor.runSync(cmd);
    }

    public static void controllLight(String deviceId, boolean isUp){
        if(deviceId != null && deviceId != ""){
            String cmd = CONTROLL_LIGHT.replace("{device_id}", deviceId);
            String num = isUp ? "221":"220";
            cmd = cmd.replace("{num}", num);
            for(int i = 0 ; i < 10 ;i++){
                Processor.runSync(cmd);
            }

        }else{
            ADBUtils.getAllDeviceIds().forEach(Id->{
                String cmd = CONTROLL_LIGHT.replace("{device_id}", Id);
                String num = isUp ? "221":"220";
                cmd = cmd.replace("{num}", num);
                for(int i = 0 ; i < 10 ;i++){
                    Processor.runSync(cmd);
                }
            });
        }

    }

    public static void main(String[] args) {
        move("7NQBB24328021580");
    }
}