import React from "react";
import axios from "axios";
import cors from 'cors'
import {Button, Divider, Table, Form, Input, Radio, Modal} from "antd";
import { EditFilled } from '@ant-design/icons';
const FormItem = Form.Item;
import {url} from '../Url'
import AppList from "./AppList";


export default class DeviceManagerBase extends React.Component{

    constructor(props) {
        super(props);

        this.state = {
            devices:[],
            visible:false,
            current_device : {}
        }
    }

    componentDidMount() {
        this.queryDeviceList();
    }


    queryDeviceList=()=>{
        axios.get(url+"/device/get_all_devices").then((res)=>{
            this.setState({devices:res.data})
        }).catch((error)=>{
            console.log(error)
        })
    }

    showModal = () => {
        this.setState({
            visible: true,
        });
    }

    installApp=(prop,deviceId)=>{

        axios.get( `http://localhost/local_device/installApp/${deviceId}`).then((res)=>{
            console.log(11111)
        }).catch((error)=>{
        })
    }

    casting=(prop,item)=>{

        axios.post( `http://localhost/device/casting`,item).then((res)=>{
        }).catch((error)=>{
        })
    }


    addDevice=(data)=>{

        axios.post( `http://localhost/device/put`,data).then((res)=>{
            console.log(data)
            this.queryDeviceList();
            this.setState({visible:false})
        }).catch((error)=>{
            console.log("新增失败");
        })
    }

    controllLight=(e,deviceId,isUp)=>{
        axios.get( `${url}/device/light/${deviceId}/${isUp}`).then((res)=>{

        }).catch((error)=>{
            console.log("调低暗度");
        })
    }


    getList=()=>{
        let {devices} = this.state
        return devices.map((item,index)=>{
            let {key,name} = item

            return <span key={`app_list_span_key_${key}`}>
                <span key={item} type="primary" >
                    {item}
                    <Button type={"primary"} onClick={(e)=>this.installApp(e,item)}>安装</Button><Divider type="vertical"/>
                </span> <Divider type="vertical"/>
            </span>


        })
    }

    render() {


        const columns = [
            {
                title: '设别号',
                dataIndex: 'deviceId',
                align:"center"
            },
            {
                title: '姓名',
                dataIndex: 'userName',
                align:"center"
            },
            {
                title: '手机号',
                dataIndex: 'phoneNum',
                align:"center"
            },
            {
                title: '身份证号码',
                dataIndex: 'idCard',
                align:"center"
            },
            {
                title: '操作',
                dataIndex: 'operation',
                align:"center"
            }

        ];

        const data = this.state.devices.map((item,index)=>{
            return {
                key: index,
                deviceId: <span  style={{color:"blue"}} >{item.deviceId} <EditFilled onClick={()=>{this.setState({visible:true, current_device:item})}}/></span>,
                userName : <span>{item.userName}</span>,
                phoneNum : <span>{item.phoneNum}</span>,
                idCard : <span>{item.idCard}</span>,
                operation: <div>
                    <Button type={"primary"} onClick={(e)=>this.installApp(e,item.deviceId)}>安装APP</Button>,
                    <Button type={"primary"} onClick={(e)=>this.casting(e,item)}>投屏</Button>,
                    <Button type={"primary"} onClick={(e)=>this.controllLight(e,item.deviceId,false)}>调低亮度</Button>,
                    <Button type={"primary"} onClick={(e)=>this.controllLight(e,item.deviceId,true)}>调高亮度</Button>,
                </div>
            }
        })


        let rowSelection = false;

        //const [form] = Form.useForm();

        return <div>
            <Modal
                title="手机信息"
                visible={this.state.visible}
                onOk={()=>{this.setState({visible:false})}}
                onCancel={()=>{this.setState({visible:false})}}
            >
                <Form
                    name="新增手机基础信息"
                    initialValues={{ remember: true }}
                    onFinish={(item)=>this.addDevice(item)}
                    onFinishFailed={()=>{}}
                >
                    <Form.Item
                        label="设备Id"
                        name="deviceId"
                        initialValue={this.state.current_device.deviceId}
                    >
                        <Input  disabled={true}/>

                    </Form.Item>

                    <Form.Item
                        label="姓名"
                        name="userName"
                        rules={[{ message: '姓名:' }]}
                        initialValue={this.state.current_device.username}
                    >
                        <Input />
                    </Form.Item>

                    <Form.Item
                        label="手机号码"
                        name="phoneNum"
                        rules={[{ message: '手机号码:' }]}
                        initialValue={this.state.current_device.phoneNum}
                    >
                        <Input />
                    </Form.Item>

                    <Form.Item
                        label="身份证号码"
                        name="idCard"
                        rules={[{ message: '身份证号码!' }]}
                        initialValue={this.state.current_device.idCard}
                    >
                        <Input />
                    </Form.Item>

                    <Form.Item>
                        <Button type="primary" htmlType="submit">
                            提交
                        </Button>
                    </Form.Item>
                </Form>
            </Modal>
            <Table rowSelection={rowSelection} columns={columns} dataSource={data}/>
            <div>
                <AppList/>
            </div>
        </div>
    }
}
