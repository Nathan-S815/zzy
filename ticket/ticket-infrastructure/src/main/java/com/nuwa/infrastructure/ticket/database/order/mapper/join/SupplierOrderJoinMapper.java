package com.nuwa.infrastructure.ticket.database.order.mapper.join;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.framework.database.tk.join.mapper.JoinPagingMapper;
import com.nuwa.infrastructure.ticket.database.order.mapper.join.query.SupplierOrderJoinPageJoinQuery;
import com.nuwa.infrastructure.ticket.database.order.vo.SupplierOrderPageVO;
import com.nuwa.infrastructure.ticket.database.scenicspot.JoinSqlConstant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * SupplierOrderJoinMapper 供应商订单查询
 *
 * @author huyonghack@163.com
 * @since 2021-07-08
 */
@Repository
public interface SupplierOrderJoinMapper extends
        JoinPagingMapper<SupplierOrderJoinPageJoinQuery, SupplierOrderPageVO> {

    String SQL = "SELECT" +
            " ticket_order.id," +
            " ticket_order.order_no," +
            " ticket_order.unit_price," +
            " ticket_order.product_id," +
            " ticket_order.visit_date," +
            " ticket_order.create_time," +
            " ticket_order.amount," +
            " ticket_order.quantity," +
            " ticket_order.refunded_quantity," +
            " ticket_order.already_consume_quantity," +
            " ticket_order.`status`," +
            " ticket_order.product_name," +
            " ticket_order.supplier_product_code," +
            " supplier_payment_order.supplier_mch," +
            " supplier_payment_order.supplier_order_no," +
            " supplier_payment_order.payment_no," +
            " merchant_supplier_config.`name` as 'supplierMerchantName'," +
            " merchant_supplier_config.`id` as 'mchSupplierId'" +
            " FROM" +
            " supplier_payment_order supplier_payment_order" +
            " LEFT JOIN ticket_order ticket_order " +
            " ON supplier_payment_order.order_id = ticket_order.id" +
            " LEFT JOIN merchant_supplier_config merchant_supplier_config " +
            " ON ticket_order.supplier_id = merchant_supplier_config.id " +
            JoinSqlConstant.EW_CUSTOM_SQL_SEGMENT;


    /**
     * 分页查询
     *
     * @param page    分页参数
     * @param wrapper 查询条件
     * @param extMap  扩展参数
     * @return Page<SupplierOrderPageVO>
     */
    @Override
    @Select(SQL)
    Page<SupplierOrderPageVO> page(IPage<?> page, @Param(Constants.WRAPPER) Wrapper<SupplierOrderJoinPageJoinQuery> wrapper, Map<String, String> extMap);

    public static void main(String[] args) {
        System.out.println(SQL);
    }
}
