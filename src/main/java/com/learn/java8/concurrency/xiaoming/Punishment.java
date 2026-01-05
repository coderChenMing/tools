package com.learn.java8.concurrency.xiaoming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Punishment {
    private int leftCopyCount;//剩余的抄写次数
    private String wordToCopy;//要抄写的单词
}
