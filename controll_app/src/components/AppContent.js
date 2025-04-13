import React from "react";
import Other from './Other'
import AppList from './AppList'
import DeviceManager from './DeviceManager'

export default class AppContent extends React.Component{



    getMenu=(menu)=>{
        let app = <Other/>;
        switch (menu){
            case "install":
                app = <InstallApp/>
                break;
            case "device_manager":
                app = <DeviceManager/>
                break;
            case "start":
                app = <InstallApp/>
                break;
            default:
                app = <Other/>;
        }
        return app;
    }

    render() {
        let {menu} = this.props;
        return <div>
            {this.getMenu(menu)}
        </div>
    }
}