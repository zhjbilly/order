package com.qizhi.order.domain;

import lombok.Data;

@Data
public class Order {
    private Integer id;

    private Integer customerId;

    private String status;

}