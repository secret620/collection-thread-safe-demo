package com.demo.set;

import java.util.*;

/**
 * @author Shi You Qin
 * @description 表示 ：
 * @email secret620@163.com
 * @date CreateTime 2021/4/9 19:58
 **/
public class SyncHashSetTest {

    /**
     * 结论
     * 非线程安全
     */
    static final int All = 1000;

    static final int addNumber = 100;
    static final int threadNumner = 10;
    public static void main(String[] args) {
        //执行多次。看结果
        for (int i = 0 ; i < All; i ++){
            SyncHashSetTest hashSetTest = new SyncHashSetTest();
            hashSetTest.execute();
        }
    }
    private void execute(){
        Set<Integer> set = Collections.synchronizedSet(new HashSet<>());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i < addNumber; i++){
                    set.add(i);
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

        //HashSet 的特性 ：不可重复，无序 所以容量为 ： addNumber * 1
        if(set.size() > (addNumber * 1) || set.size() < (addNumber * 1)){
            System.err.println(set.size());
        }else{
//            System.out.println(set.size());
        }

    }
}
