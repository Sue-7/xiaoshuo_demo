package com.xiaoshuo.business.api.association.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoshuotech.cloud.core.base.PageRequest;
import com.xiaoshuotech.cloud.core.util.Func;

import java.util.List;

/**
 * xx类
 *
 * @Author chenqingdong
 * @Date 2020/11/6
 * @Version 1.0.0
 */
public class PageRequestUtils {

    /**
     * 构建分页参数
     *
     * @param queryRequest
     */
    public static Page createQueryPage(PageRequest queryRequest) {
        Page page = null;
        if (Func.isEmpty(queryRequest.getCurrent()) || queryRequest.getCurrent() == 0
                || Func.isEmpty(queryRequest.getSize()) || queryRequest.getSize() == 0) {
        } else {
            page = new Page();
            page.setSize(queryRequest.getSize());
            page.setCurrent(queryRequest.getCurrent());
        }
        return page;
    }

    /**
     * 填充分页参数
     *
     * @param page
     * @param list
     */
    public static IPage populatePage(IPage page, List list) {

        if (Func.isEmpty(page)) {
            page = new Page();
            page.setSize(Func.isEmpty(list) ? 10 : list.size());
            page.setTotal(Func.isEmpty(list) ? 0 : list.size());
        }
        page.setRecords(list);
        return page;
    }


    /**
     * 转化成mybatis plus中的Page
     * @param query
     * @param <T>
     * @return
     */
    public static <T> IPage<T> getPage(PageRequest query) {
        Page<T> page = new Page<>(Func.toInt(query.getCurrent(), 1), Func.toInt(query.getSize(), 10));
        if(Func.isEmpty(query.getAscs()) && Func.isEmpty(query.getDescs())){
            OrderItem orderItem = new OrderItem();
            orderItem.setAsc(false);
            orderItem.setColumn("pkid");
            page.addOrder(orderItem);
        }else {
            page.setAsc(Func.toStrArray(com.xiaoshuotech.minmetals.association.api.util.SqlKeyword.filter(query.getAscs())));
            page.setDesc(Func.toStrArray(com.xiaoshuotech.minmetals.association.api.util.SqlKeyword.filter(query.getDescs())));
        }
        return page;
    }
}
