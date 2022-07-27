package com.xiaoshuo.business.api.association.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yisanyin
 * @date 2022年04月18日 10:16
 */
@Slf4j
public class NumUtils {
    private static long tmpID = 0;
    private static final long LOCK_TIME = 1;
    private static final long INCREASE_STEP = 1;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
    private static final Lock LOCK = new ReentrantLock();

    public static long nextPkId() {
        //当前：（年、月、日、时、分、秒、毫秒）
        long timeCount;
        try {
            if (LOCK.tryLock(LOCK_TIME, TimeUnit.SECONDS)) {
                timeCount = Long.parseLong(sdf.format(new Date()));
                try {
                    if (tmpID < timeCount) {
                        tmpID = timeCount;
                    } else {
                        tmpID += INCREASE_STEP;
                        timeCount = tmpID;
                    }
                    return timeCount;
                } finally {
                    LOCK.unlock();
                }
            } else {
                log.error("get num lock failed");
                return nextPkId();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("lock error ", e);
            return nextPkId();
        }
    }

}
