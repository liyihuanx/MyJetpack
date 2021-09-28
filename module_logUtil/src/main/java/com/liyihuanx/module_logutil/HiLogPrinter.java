package com.liyihuanx.module_logutil;


public interface HiLogPrinter {
    void print( HiLogConfig config, int level, String tag,  String printString);
}
