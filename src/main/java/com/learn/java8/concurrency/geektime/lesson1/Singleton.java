package com.learn.java8.concurrency.geektime.lesson1;

/**
 * 这看上去一切都很完美，无懈可击，但实际上这个 getInstance() 方法并不完美。问题出在哪里呢？出在 new 操作上，我们以为的 new 操作应该是：
 * 1)	分配一块内存 M；
 * 2)	在内存 M 上初始化 Singleton 对象；
 * 3)	然后 M 的地址赋值给 instance 变量。
 * 但是实际上优化后的执行路径却是这样的：
 * 1)	分配一块内存 M；
 * 2)	将 M 的地址赋值给 instance 变量；
 * 3)	最后在内存 M 上初始化 Singleton 对象。
 *    优化后会导致什么问题呢？我们假设线程 A 执行了 getInstance() 方法，当执行完指令 2 时恰好发生了线程切换，切换到了线程 B 上；
 * 如果此时线程 B 也执行 getInstance() 方法，那么线程 B 在执行第一个判断时会发现 instance != null，所以直接返回 instance，而此时的 instance 是没有初始化过的，
 * 如果我们这个时候访问 instance 的成员变量就可能触发空指针异常。
 *
 * 解决这个问题的思路也很简单，我们只需要把 instance 变量声明成 volatile 就可以了。
 * 1)	使用 volatile 关键字会强制将修改的值立即写入主存；
 * 2)	使用 volatile 关键字的话，当线程 2 进行修改时，会导致线程 1 的工作内存中缓存变量 instance 的缓存行无效（反映到硬件层的话，就是 CPU 的 L1 或者 L2 缓存中对应的缓存行无效）；
 * 3)	由于线程 1 的工作内存中缓存变量 instance 的缓存行无效，所以线程 1 再次读取变量 instance 的值时会去主存读取。
 *
 *    总结：
 *    1. 懒汉式单例在多线程下的问题
 *    2. 解决办法：使用volatile关键字
 *    3. 双重检查锁的问题：
 *       1) 指令重排序
 *       2) 内存可见性
 *    4. 解决办法：使用volatile关键字
 *
 * @author hhs
 * @since 2020/11/20 16:35
 */
public class Singleton {
    static Singleton instance;

    static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null)
                    instance = new Singleton();
            }
        }
        return instance;
    }
}
