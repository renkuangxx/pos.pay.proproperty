package com.jingpai.pos.customer.bean;

/**
 * Created By：jinheng.liu
 * on 2020/9/14
 */
public class EventBusMessage {
    private String eventType;
    private Object eventObj;
    public EventBusMessage(String eventType, Object eventObj) {
        this.eventObj = eventObj;
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Object getEventObj() {
        return eventObj;
    }

    public void setEventObj(Object eventObj) {
        this.eventObj = eventObj;
    }
}
