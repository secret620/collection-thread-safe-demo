package com.demo.map;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author Shi You Qin
 * @description 表示 ：
 * @email secret620@163.com
 * @date CreateTime 2021/4/9 19:24
 **/
public class HashTableTest {

    /*
       结论：线程安全
        */
    public static void main(String[] args) {
        //执行多次。看结果
        for (int i = 0 ; i < 150; i ++){
            HashTableTest hashMapTest = new HashTableTest();
            hashMapTest.execute();
        }
    }

    private void execute(){
        Map<String, Integer> map = new Hashtable<>();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i < 100; i++){
                    map.put(String.valueOf(i), i);
                }
            }
        };

        int n = 10;
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < n; i++ ){
            Thread thread = new Thread(runnable);
            threadList.add(thread);
            thread.start();
        }

        //主线程等待子线程完成
        threadList.stream().forEach(item -> {
            try {
                item.join(); //加入主线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        if(map.size() > 100 || map.size() < 100){
            System.err.println(map.size());
        }else{
            System.out.println(map.size());
        }

    }
}
