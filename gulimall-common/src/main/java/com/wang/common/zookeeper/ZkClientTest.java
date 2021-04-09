package com.wang.common.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

public class ZkClientTest {

    public static void main(String[] args) {

        /*
         * ZkClient提供了两种实例创建的方式
         * 参数：
         *  zkServers： zk地址，用，分割
         *  connectionTimeout： 连接超时时间
         *  sessionTimeOut： 会话超时时间， 默认30s
         *  connection： IZkConnection接口的实现类
         *  zkSerializer: 自定义序列化器
         */

        ZkClient zkClient1 = new ZkClient("127.0.0.1:2181", 5000);

        ZkConnection zkConnection = new ZkConnection("127.0.0.1:2181", 5000);
        ZkClient zkClient2 = new ZkClient(zkConnection);


    }

}
