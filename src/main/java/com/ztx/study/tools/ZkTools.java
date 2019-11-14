package com.ztx.study.tools;

import com.sun.istack.internal.NotNull;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ZkTools implements Watcher {
    /** 定义原子变量 */
    AtomicInteger seq = new AtomicInteger();
    /** 定义session失效时间 */
    private static final int SESSION_TIMEOUT = 10000;
    /** zookeeper服务器地址 */
    private static final String CONNECTION_ADDR = "127.0.0.1:2181";
    /** zk父路径设置 */
    private static final String PARENT_PATH = "/ROOT";
    /** 进入标识 */
    private static final String LOG_PREFIX_OF_MAIN = "【Main】";
    /** zk变量 */
    private ZooKeeper zk = null;
    /** 信号量设置，用于等待zookeeper连接建立之后 通知阻塞程序继续向下执行 */
    private CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public void createConnection() {
        try {
            System.out.println(LOG_PREFIX_OF_MAIN + "开始连接ZK服务器");
            this.zk = new ZooKeeper(CONNECTION_ADDR, SESSION_TIMEOUT, this);
            connectedSemaphore.await();
        } catch (IOException |InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean updateData(String path, String data) {
        try {
            if (existsNode(path)!=null){
                this.zk.setData(path, data.getBytes(), -1);
                return true;
            }else {
                System.out.println("节点不存在，更新数据失败");
                return false;
            }
        } catch (KeeperException |InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createNode(String path, String data) {
        try {
            String[] split = path.substring(1).split("/");
            StringBuilder builder=new StringBuilder();
            for (int i = 0; i < split.length-1; i++) {
                builder.append("/").append(split[i]);
                if (existsNode(builder.toString()) == null) {
                    this.zk.create(builder.toString(), null, ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
                }
            }
            this.zk.create(path, CommUtils.nvl(data,"").getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        } catch (KeeperException |InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String readData(String path) {
        byte[] data=null;
        try {
            data = this.zk.getData(path, true, null);
        } catch (KeeperException |InterruptedException e) {
            e.printStackTrace();
        }
        return new String(data);
    }

    public void deleteNode(String path) {
        try {
            this.zk.delete(path,-1);
        } catch (KeeperException |InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        deleteAll(PARENT_PATH);
    }

    public void deleteAll(String path){
        if (existsNode(path) != null) {
            List<String> children = getChildren(path);
            if (children == null) {
                deleteNode(path);
            } else {
                for (String child : children) {
                    String newPath = path + "/" + child;
                    System.out.println(newPath);
                    deleteAll(newPath);
                }
                deleteNode(path);
            }
        }
    }

    public List<String> getChildren(String path) {
        try {
            if (existsNode(path) != null) {
                return zk.getChildren(path, true);
            }else {
                return null;
            }

        } catch (KeeperException |InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Stat existsNode(String path) {
        try {
            return zk.exists(path, true);
        } catch (KeeperException |InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        try {
            if (zk != null) {
                zk.close();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void process(WatchedEvent event) {
        {
            try {
                System.out.println("【进入process处理】");
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.print("事件类型：");
                    switch (event.getType()) {
                        case None:
                            System.out.println("----建立了zookeeper连接");
                            connectedSemaphore.countDown();
                            break;
                        case NodeCreated:
                            System.out.println("----创建了新节点");
                            break;
                        case NodeDeleted:
                            System.out.println("----删除了节点");
                            break;
                        case NodeDataChanged:
                            System.out.println("----节点数据发生了变化");
                            break;
                        case NodeChildrenChanged:
                            System.out.println("----子节点发生了变化");
                            break;
                    }
                    System.out.println("节点路径："+event.getPath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
