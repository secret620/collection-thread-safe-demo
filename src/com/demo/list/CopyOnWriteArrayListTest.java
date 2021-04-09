package com.demo.list;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Shi You Qin
 * @description 表示 ：
 * @email secret620@163.com
 * @date CreateTime 2021/4/9 19:42
 **/
public class CopyOnWriteArrayListTest {
    /**
     * 结论
     * 线程安全
     */
    static final int All = 1000000;//执行1百万次的比对
    static final int addNumber = 100;
    static final int threadNumner = 100000;//10万线程并发（笔记本电脑扛不住）

    public static void main(String[] args) {
        //执行多次。看结果
        for (int i = 0 ; i < All; i ++){
            CopyOnWriteArrayListTest arrayListTest = new CopyOnWriteArrayListTest();
            arrayListTest.execute();
        }
    }
    private void execute(){
        List<Integer> list = new CopyOnWriteArrayList<>();
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
