package com.jingpai.pos.bean;

/**
 * @author hongjingchao
 * @date 2020/2/18
 */
public enum PayState {
  //
  UN_PAY(1, "待支付"),
  PAYING(2, "支付中"),
  PAY_SUCCESS(3, "支付成功"),
  REFUND_SUCCESS(4, "退款成功"),
  TRADE_FAILED(5, "交易失败");

  private Integer code;
  private String name;

  PayState(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  public static String getNameByCode(Integer code) {
    for (PayState payState : PayState.values()) {
      if (code.equals(payState.getCode())) {
        return payState.getName();
      }
    }
    return null;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
