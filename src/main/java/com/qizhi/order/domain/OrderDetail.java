package com.qizhi.order.domain;

import lombok.Data;

@Data
public class OrderDetail {
    private Integer id;

    private Integer orderId;

    private String goodsName;

    private Integer amount;

}