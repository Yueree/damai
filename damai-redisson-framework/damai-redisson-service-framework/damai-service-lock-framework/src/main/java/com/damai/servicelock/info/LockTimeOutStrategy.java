package com.damai.servicelock.info;


/**
 * @description: 分布式锁 策略
 **/
public enum LockTimeOutStrategy implements LockTimeOutHandler{
    /**
     * 快速失败
     * */
    FAIL(){
        @Override
        public void handler(String lockName) {
            String msg = String.format("%s请求频繁",lockName);
            throw new RuntimeException(msg);
        }
    }
}
