package com.wang.common.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperTest {

    static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        /*
         * 第一个参数：
         *    如果连接多个zookeeper实例：127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2181
         *    如果指定目录：127.0.0.1:2181/zk-book  可以实现客户端隔离命名空间、、
         * 第二个参数：
         *    session超时时间
         * 第三个参数：
         *    watcher：由于连接是异步的，当zooKeeper连接成功之后会回调watcher中的逻辑
         * 第四个参数：
         *    sessionId：通过 sessionId + sessionPasswd 可以实现客户端会话复用
         * 第五个参数：
         *    sessionPasswd：通过 sessionId + sessionPasswd 可以实现客户端会话复用
         * 第六个参数：
         *    canBeReadOnly：zookeeper的read-only模式
         */
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000,
                (watchedEvent) -> {
                    System.out.println("======" + "receive watchedEvent {" + watchedEvent + "}");
                    if (Watcher.Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                        countDownLatch.countDown();

                    }
                });
        System.out.println("======" + zooKeeper.getState());
        countDownLatch.await();
        getNode(zooKeeper);
        Thread.sleep(10000);
    }

    static void createZNode(ZooKeeper zooKeeper) throws KeeperException, InterruptedException {
        String s = zooKeeper.create(
                "/zk-test-ephemeral-",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);
        System.out.println("create node success , node ==> " + s);
        String s1 = zooKeeper.create(
                "/zk-test-ephemeral-",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("create node success , node ==> " + s1);
    }


    static void createAsyncZNode(ZooKeeper zooKeeper) throws KeeperException, InterruptedException {
        AsyncCallback.StringCallback stringCallback = (rc, path, ctx, name) -> {
            /*
             * 参数说明：
             * rc：执行结果状态码 =>
             *      0: 成功
             *      -4：客户度已经断开连接
             *      -110：节点已经存在
             *      -112：会话已经过期
             * path：参数传递的节点路径
             * ctx：第六个参数，context上下文
             * name：实际生成的节点路径
             */
            System.out.println("rc=>" + rc + "path =>" + path + "ctx=>" + ctx + "name=>" + name);
        };
        zooKeeper.create(
                "/zk-test-ephemeral-",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                stringCallback,
                "i am context");
        zooKeeper.create(
                "/zk-test-ephemeral-",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                stringCallback,
                "i am context");
        zooKeeper.create(
                "/zk-test-ephemeral-",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL,
                stringCallback,
                "i am context");
    }

    static void deleteNode(ZooKeeper zooKeeper) throws KeeperException, InterruptedException {

        zooKeeper.delete("/test-e", 0, (rc, path, ctx)->{
            System.out.println("删除节点完成");
        }, "context");
    }

    static void getNode(ZooKeeper zooKeeper) throws KeeperException, InterruptedException {
        Stat stat = new Stat();
        List<String> children = zooKeeper.getChildren("/test-e0000000043", true, stat);
        System.out.println(stat);

    }

}
