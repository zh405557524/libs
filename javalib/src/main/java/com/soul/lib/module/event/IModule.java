package com.soul.lib.module.event;

/**
 * Description:
 * Author: 祝明
 * CreateDate: 2019-07-02 15:23
 * UpdateUser:
 * UpdateDate: 2019-07-02 15:23
 * UpdateRemark:
 */
public interface IModule {
    public boolean onEvent(int eventId, Object data);
}
