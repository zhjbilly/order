package com.qizhi.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qizhi.order.constant.OrderStatus;
import com.qizhi.order.dao.OrderMapper;
import com.qizhi.order.domain.Order;
import com.qizhi.order.dto.OrderGoodsDTO;
import com.qizhi.order.service.IOrderService;
import com.qizhi.user.constant.UserType;
import com.qizhi.user.dto.UserDTO;
import com.qizhi.user.facade.UserFacade;
import com.qizhi.user.util.BizException;
import com.qizhi.warehouse.dto.GoodsBatchQueryDTO;
import com.qizhi.warehouse.facade.GoodsBatchFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    @Reference
    private UserFacade userFacade;

    @Reference
    private GoodsBatchFacade goodsBatchFacade;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void createOrder(String token, List<OrderGoodsDTO> orderGoods) {
        UserDTO user = userAuth(token, UserType.CUSTOMER);
        List<GoodsBatchQueryDTO> needs = new ArrayList<>();
        for(OrderGoodsDTO good : orderGoods){
            GoodsBatchQueryDTO dto = new GoodsBatchQueryDTO();
            dto.setGoodsName(good.getGoodsName());
            dto.setAmount(dto.getAmount());
        }
        Integer warehouseId = goodsBatchFacade.queryWarehouse(needs, user.getLocationX(), user.getLocationY());
        if(null == warehouseId){
            throw new BizException("库存不足，无法下单");
        }
        // 锁库存
        if(goodsBatchFacade.lock(warehouseId, needs)){
            // 创建订单
            Order record = new Order();
            record.setStatus(OrderStatus.PENDING.name());
            record.setCustomerId(user.getUserId());
            orderMapper.insert(record);
        }
    }

    @Override
    public void dispatch(String token, int orderId) {
        userAuth(token, UserType.STAFF);
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(null == order || OrderStatus.PENDING.name() != order.getStatus()){
            throw  new BizException("订单不存在or订单状态不为待配送");
        }
        // 扣减
//        if(goodsBatchFacade.dudect())
    }

    private UserDTO userAuth(String token, UserType userType){
        // 用户校验
        UserDTO userDTO = null;
        if(null == token || null == (userDTO = userFacade.validToken(token)) || !userType.name().equals(userDTO.getUserType())){
            log.warn("token校验失败,token = {}, user = {}", token, userDTO);
            throw new BizException("用户未登陆或非可操作用户");
        }
        return userDTO;
    }
}
