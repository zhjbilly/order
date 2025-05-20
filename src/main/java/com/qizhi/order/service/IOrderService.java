package com.qizhi.order.service;

import com.qizhi.order.dto.OrderGoodsDTO;

import java.util.List;

public interface IOrderService {

    /**
     * 顾客下单
     *
     * @param token
     * @param orderGoods
     */
    void createOrder(String token, List<OrderGoodsDTO> orderGoods);

    /**
     * 员工发货
     *
     * @param token
     * @param orderId
     */
    void dispatch(String token, int orderId);

}
