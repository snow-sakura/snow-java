package com.bjpowernode.fastdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.IOException;

/**
 * ClassName:Test
 * Package:com.bjpowernode.fastdfs
 * Description:
 *
 * @date:2018/10/12 11:58
 * @author:bjpowernode.com
 */
public class Test {
    public static void main(String[] args) {
        upload();
//        download();
//        delete();
    }



    public static void upload(){
        StorageServer ss=null;
        TrackerServer ts= null;
        try {
            //加载配置文件，用于确定TrackerServer的地址
            ClientGlobal.init("fdfs.conf");
            //创建TrackerClient对象，用于连接TrackerServer和StorageServer
            TrackerClient tc=new TrackerClient();
            //获取TrackerServer ，并拦截到TrackerServer服务器
            ts=tc.getConnection();
            //根据获取TrackerServer获取StorageServer并拦截到StorageServer
            ss=tc.getStoreStorage(ts);
            //创建StorageClient对象，这个对象用于具体的操作FastDFS
            StorageClient storageClient=new StorageClient(ts,ss);

            //完成上传，上传后返回String数组，数组中的第一个元素为这个上传的文件所在组名，第二元素为这个上传的文件所在目录和文件名
            //我们需要将这个元素存入数据库中否则日后没有办法完成下载和删除
            String resultes[]= storageClient.upload_file("D:\\qrCode.jpg","jpg",null);

            for(String str:resultes){
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            if(ss!=null){
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ts!=null){
                try {
                    ts.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static  void download(){
        StorageServer ss=null;
        TrackerServer ts= null;
        try {
            //加载配置文件，用于确定TrackerServer的地址
            ClientGlobal.init("fdfs.conf");
            //创建TrackerClient对象，用于连接TrackerServer和StorageServer
            TrackerClient tc=new TrackerClient();
            //获取TrackerServer ，并拦截到TrackerServer服务器
            ts=tc.getConnection();
            //根据获取TrackerServer获取StorageServer并拦截到StorageServer
            ss=tc.getStoreStorage(ts);
            //创建StorageClient对象，这个对象用于具体的操作FastDFS
            StorageClient storageClient=new StorageClient(ts,ss);
            //根据组名和远程文件名，下载文件并保存到本地中
            //返回值为整数 0表示文件下载完成 22表示目录错误 2 表示文件错误
           int result=storageClient.download_file("group1","M00/00/00/wKgfgFvAHm6AGwcwAAAqj2EiUAc048.jpg","E:\\abc.jpg");

            System.out.println(result);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            if(ss!=null){
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ts!=null){
                try {
                    ts.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void delete(){
        StorageServer ss=null;
        TrackerServer ts= null;
        try {
            //加载配置文件，用于确定TrackerServer的地址
            ClientGlobal.init("fdfs.conf");
            //创建TrackerClient对象，用于连接TrackerServer和StorageServer
            TrackerClient tc=new TrackerClient();
            //获取TrackerServer ，并拦截到TrackerServer服务器
            ts=tc.getConnection();
            //根据获取TrackerServer获取StorageServer并拦截到StorageServer
            ss=tc.getStoreStorage(ts);
            //创建StorageClient对象，这个对象用于具体的操作FastDFS
            StorageClient storageClient=new StorageClient(ts,ss);
            //根据组名和远程文件名，删除文件
            //返回值为整数 0表示文件删除完成 22表示目录错误 2 表示文件错误
            int result=storageClient.delete_file("group12","M00/00/00/wKgfgFvAHm6AGwcwAAAqj2EiUAc048.jpg");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            if(ss!=null){
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ts!=null){
                try {
                    ts.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
