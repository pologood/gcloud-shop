package com.gcloud.shop.core.utils;


import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

/**
 * @author ChenJin
 * @version V1.0
 * @Title: ZooUtil
 * @Package com.gcloud.shop.core.utils
 * @Description: zooUtil工具类
 * @date 2016/8/18 17:47
 */
public class ZooUtil implements Watcher {

    private static final int SESSION_TIME_OUT = 2000;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private ZooKeeper zookeeper;

    @Override
    public void process(WatchedEvent event) {

        if (event.getState() == KeeperState.SyncConnected) {
            System.out.println("Watch received event");
            countDownLatch.countDown();
        }
    }

    /**连接zookeeper
     * @param host
     * @throws Exception
     */
    public void connectZookeeper(String host) throws Exception{
        zookeeper = new ZooKeeper(host, SESSION_TIME_OUT, this);
        countDownLatch.await();
        System.out.println("zookeeper connection success");
    }

    /**
     * 创建节点
     * @param path
     * @param data
     * @throws Exception
     */
    public String createNode(String path,String data) throws Exception{
        return this.zookeeper.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 获取路径下所有子节点
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public List<String> getChildren(String path) throws KeeperException, InterruptedException{
        List<String> children = zookeeper.getChildren(path, true);
        return children;
    }

    /**
     * 获取节点上面的数据
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String getData(String path) throws KeeperException, InterruptedException{
        byte[] data = zookeeper.getData(path, true, null);
        if (data == null) {
            return "";
        }
        return new String(data);
    }

    /**
     * 设置节点信息
     * @param path
     * @param data
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Stat setData(String path,String data) throws KeeperException, InterruptedException{
        Stat stat = zookeeper.setData(path, data.getBytes(), -1);
        return stat;
    }

    /**
     * 删除节点
     * @param path
     * @throws InterruptedException
     * @throws KeeperException
     */
    public void deleteNode(String path) throws InterruptedException, KeeperException{
        zookeeper.delete(path, -1);
    }

    /**
     * 获取创建时间
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String getCTime(String path) throws KeeperException, InterruptedException{
        Stat stat = zookeeper.exists(path, false);
        return DateUtil.getDate(new Date()) ;//.longToString(String.valueOf(stat.getCtime()));
    }

    /**
     * 获取某个路径下孩子的数量
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Integer getChildrenNum(String path) throws KeeperException, InterruptedException{
        int childenNum = zookeeper.getChildren(path, false).size();
        return childenNum;
    }
    /**
     * 关闭连接
     * @throws InterruptedException
     */
    public void closeConnection() throws InterruptedException{
        if (zookeeper != null) {
            zookeeper.close();
        }
    }

    public static void main(String ars[]){
        ZooUtil zooUtil = new ZooUtil();
        try {
            zooUtil.connectZookeeper("127.0.0.1:2181");
            List<String> nodes = zooUtil.getChildren("/dubbo");
            String dubboData = zooUtil.getData("/dubbo");
            System.out.println(dubboData);
            for(String node : nodes){
                System.out.println(node);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
