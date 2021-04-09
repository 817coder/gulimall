package com.wang.common.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorTest {

    static CuratorFramework curator = createCurator();

    static final String MASTER_PATH = "/master_select";
    static final String LOCK_PATH = "/distribute_lock";
    static final String ATOMIC_INT_PATH = "/atomic_lock";

    public static void main(String[] args) throws Exception {
        curator.start();
//        createNode();
//        nodeListen();
        Thread.sleep(20000);

    }

    static void leaderSelect(){
        LeaderSelector leaderSelector = new LeaderSelector(curator, MASTER_PATH, new LeaderSelectorListenerAdapter(){

            @Override
            public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                System.out.println("已经成为master，开始执行业务逻辑");
                // 模拟业务耗时逻辑
                Thread.sleep(2000);
                System.out.println("执行业务逻辑完成，释放master");
            }
        });

        leaderSelector.autoRequeue();
        leaderSelector.start();
    }

    static void distributeLock() throws Exception {
        InterProcessMutex lock = new InterProcessMutex(curator, LOCK_PATH);
        try {
            lock.acquire();
        } catch (Exception e){
            // pass
        } finally {
            lock.release();
        }
    }

    static void distAtomicInt() throws Exception {
        DistributedAtomicInteger atomicInteger =
                new DistributedAtomicInteger(curator,
                        ATOMIC_INT_PATH,
                        new RetryNTimes(3, 1000));
        AtomicValue<Integer> add = atomicInteger.add(1);
        System.out.println(add.postValue());
    }

    static void nodeListen() throws Exception {

        // 设置节点监听，可以监听多次

        NodeCache nodeCache = new NodeCache(curator, "/test-listen");
        nodeCache.start(true);
        nodeCache.getListenable().addListener(() -> {
            System.out.println("listened node data changed");
        });
    }

    static void childNodeListen(){
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curator, "/test-listen", true);
        /*
         * 事件类型
         * CHILD_ADDED,
         * CHILD_UPDATED,
         * CHILD_REMOVED,
         * CONNECTION_SUSPENDED,
         * CONNECTION_RECONNECTED,
         * CONNECTION_LOST,
         * INITIALIZED;
         */
        pathChildrenCache.getListenable().addListener((curator, event)->{
            PathChildrenCacheEvent.Type type = event.getType();
            event.getData();
            System.out.println("监测到节点变更事件" + type);

        });

    }

    static void createNode() throws Exception {

        BackgroundCallback backgroundCallback = (curatorFramework, curatorEvent) -> {
            CuratorEventType type = curatorEvent.getType();
            int resultCode = curatorEvent.getResultCode();
            System.out.println("[" + type + resultCode + "]");
        };

        curator.create().
                creatingParentsIfNeeded().
                withMode(CreateMode.EPHEMERAL).
                inBackground(backgroundCallback).
                forPath("/test-listen", "".getBytes());
    }

    static void deleteNode() throws Exception {
        curator.delete().
                guaranteed().
                deletingChildrenIfNeeded().
                withVersion(0).
                forPath("/test-e0000000043");

    }

    static void getData() throws Exception {
        Stat stat = new Stat();
        byte[] bytes = curator.getData().storingStatIn(stat).forPath("/test-a");
        System.out.println("getData===>" + new String(bytes));
        System.out.println("getStat===>" + stat);
    }

    static void setData() throws Exception {
        curator.setData().withVersion(1).forPath("/test-a", "wang".getBytes());
    }

    static CuratorFramework createCurator() {
        /*
         * baseSleepTimeMs: 重试的休眠时间，也就是休眠baseSleepTimeMs时间后进行下一次重试
         * maxRetries： 最大重试次数
         * maxSleepMs： 最大总重试时间
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3, 2147483647);
        /*
         * connectString : zookeeper主机地址，集群用，连接
         * sessionTimeoutMs： 会话超时时间
         * connectionTimeoutMs： 创建连接超时时间
         * retryPolicy
         */
//        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(
//                "127.0.0.1", 60000, 15000, retryPolicy
//        );
        return CuratorFrameworkFactory.builder().
                connectString("127.0.0.1").
                sessionTimeoutMs(60000).
                connectionTimeoutMs(15000).
                retryPolicy(retryPolicy).
                build();
    }

}
