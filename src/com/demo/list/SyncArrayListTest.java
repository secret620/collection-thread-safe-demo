package com.demo.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Shi You Qin
 * @description 表示 ：
 * @email secret620@163.com
 * @date CreateTime 2021/4/9 19:42
 **/
public class SyncArrayListTest {
    /**
     * 结论
     * Collections.synchronizedList(new ArrayList<>())
     * 线程安全
     */
    static final int All = 100;

    static final int addNumber = 100;
    static final int threadNumner = 1000;
    public static void main(String[] args) {
        //执行多次。看结果
        for (int i = 0 ; i < All; i ++){
            SyncArrayListTest arrayListTest = new SyncArrayListTest();
            arrayListTest.execute();
        }
    }
    private void execute(){
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i < addNumber; i++){
                    list.add(i);
                }
            }
        };

        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < threadNumner; i++ ){
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

        //ArrayList的特性 ：可重复，无序 所以容量为 ： addNumber * threadNumner
        if(list.size() > (addNumber * threadNumner) || list.size() < (addNumber * threadNumner)){
            System.err.println(list.size());
        }else{
//            System.out.println(list.size());
        }

    }

}
