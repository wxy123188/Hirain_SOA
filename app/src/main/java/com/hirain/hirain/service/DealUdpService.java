package com.hirain.hirain.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DealUdpService extends Service {
    public DealUdpService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {

        private DatagramSocket udpSocket;

        private ExecutorService mExecutorService;//线程池
        private String netIP = "192.168.2.223";//目标ip
        private int netPort = 9000;//目标端口
        private int heartTime = 5000;//间隔时间，接收和发送数据
        private Handler uHandler = new Handler();
        private int linkCount = 0;//连接次数

        public void startUdp() {
            if (mExecutorService == null) {
                mExecutorService = Executors.newCachedThreadPool();
            }
            mExecutorService.execute(initSocket);
        }

        private Runnable initSocket = new Runnable() {
            @Override
            public void run() {
                try {
                    // InetAddress address = InetAddress.getByName(netIP);
                    //这里必须要设置null，不然下面设置本地端口会报异常
                    udpSocket = new DatagramSocket(null);
                    udpSocket.setReuseAddress(true);
                    //设置本地端口
                    udpSocket.bind(new InetSocketAddress(9000));
                    //udpSocket.connect(address,netPort);
                    linkCount = 1;
                    uHandler.post(jieshou);
                    uHandler.post(fasong);
                }catch (Exception e){
                    e.printStackTrace();
                    linkCount++;
                    Log.i("sun", "连接失败，进行第" + linkCount + "次重连");
                    uHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mExecutorService.execute(initSocket);
                        }
                    }, heartTime);
                }

            }
        };


        private Runnable fasong = new Runnable() {
            @Override
            public void run() {
                sendData("123456789");
            }
        };

        //发送数据，可以暴露出去，在其他地方调用
        private void sendData(String ss) {
            mExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //uHandler.postDelayed(fasong,5000);定时发送
                        byte[] data = ss.getBytes();
                        DatagramPacket send = new DatagramPacket(data, data.length,new InetSocketAddress(netIP, netPort));
                        udpSocket.send(send);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        /**
         * 发送文件
         */
        private void sendFile(){
            mExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream in = getAssets().open("meet.docx");
                        int size = -1;
                        byte[] car = new byte[1024];
                        while ((size = in.read(car, 0, 1024)) != -1) {
                            //这里是发送文件，实际中应该是在对方回应后在发送下一包，添加说明数据
                            DatagramPacket send2 = new DatagramPacket(car, car.length,new InetSocketAddress(netIP, netPort));
                            udpSocket.send(send2);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        private Runnable jieshou = new Runnable() {
            @Override
            public void run() {
                Log.i("sun", "执行接收");
                receiveData();
            }
        };

        private void receiveData() {
            mExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //uHandler.postDelayed(jieshou,5000);
                        byte[] result = new byte[1024];
                        while (true) {
                            DatagramPacket reveive = new DatagramPacket(result, result.length, new InetSocketAddress(netIP, netPort));
                            udpSocket.receive(reveive);
                            byte[] data = reveive.getData();
                            String ss = new String(data,0,reveive.getLength());
                            String cc = bytesToHex(data);
                            Log.i("sun", cc + "==接收到数据==" + ss);
                        }
                    } catch (Exception e) {
                        Log.i("sun","接收异常=="+e);
                    }
                }
            });
        }

        /**
         * 将接收到byte数组转成String字符串
         *
         * @param bytes 接收的byte数组   16进制的数组
         * @return string字符串
         */
        private  String bytesToHex(byte[] bytes) {
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                String hex = Integer.toHexString(aByte & 0xFF);
                if (hex.length() < 2) {
                    sb.append(0);
                }
                sb.append(hex);
            }
            return sb.toString();
        }


        public void destory(){
            uHandler.removeCallbacksAndMessages(null);
            udpSocket.disconnect();
            udpSocket.close();
        }

    }
}
