package com.liyihuanx.module_logutil;

import android.util.Log;

import static com.liyihuanx.module_logutil.HiLogConfig.MAX_LEN;


public class HiConsolePrinter implements HiLogPrinter {

    @Override
    public void print(HiLogConfig config, int level, String tag, String printString) {
        int len = printString.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            StringBuilder log = new StringBuilder();
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                log.append(printString.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }
            if (index != len) {
                log.append(printString.substring(index, len));
            }
            Log.println(level, tag, log.toString());
        } else {
            Log.println(level, tag, printString);
        }
    }
}
