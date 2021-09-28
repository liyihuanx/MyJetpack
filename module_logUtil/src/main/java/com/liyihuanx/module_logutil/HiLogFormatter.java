package com.liyihuanx.module_logutil;

public interface HiLogFormatter<T> {

    String format(T data);
}