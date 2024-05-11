package com.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.common.ResponseData;
import com.base.entity.BaseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public abstract class BaseController<S extends IService<E>, E extends BaseEntity> {
    private Class<E> entityClass;

    public BaseController() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superClass;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            if (typeArguments.length > 1 && typeArguments[1] instanceof Class) {
                this.entityClass = (Class<E>) typeArguments[1];
            }
        }
    }

    public Class<E> getEntityClass() {
        return this.entityClass;
    }


    protected S service;

    @Resource
    public void setService(S service) {
        this.service = service;
    }

    @Resource
    protected HttpServletRequest request;

    @PostMapping("save")
    public ResponseData do_save(@RequestBody E e) {
        boolean isAdd = StringUtils.isEmpty(e.getId());
        e.setUpdateTime(new Date());
        if (isAdd) e.setCreateTime(new Date());
        return ResponseData.success(service.saveOrUpdate(e)).put("id", e.getId());
    }

    @PostMapping({"", "/"})
    public ResponseData to_list() {
        return ResponseData.success(service.page(getPage(), getQueryWrapper()));
    }

    @GetMapping("view/{id}")
    public ResponseData to_view(@PathVariable("id") String id) {
        return ResponseData.success("获取成功", service.getById(id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseData do_delete(@PathVariable("id") String id) {
        service.removeById(id);
        return ResponseData.success("删除成功", null);
    }

    @DeleteMapping("delete")
    public ResponseData do_delete(@RequestBody List<String> list) {
        service.removeByIds(list);
        return ResponseData.success("删除成功", null);
    }

    /**
     * 获取分页相关参数
     *
     * @return
     */
    private IPage getPage() {
        String current = getReqParamVal("page", "0");
        String limit = getReqParamVal("limit", "10");
        IPage<E> page = new Page<E>(Long.valueOf(current), Long.valueOf(limit));
        List<OrderItem> orders = page.orders();
        orders.add(OrderItem.desc("create_time"));
        return page;
    }

    /**
     * 获取查询相关参数
     *
     * @return
     */
    protected QueryWrapper getQueryWrapper() {
        return getBaseQueryWrapper();
    }

    protected QueryWrapper getBaseQueryWrapper() {
        QueryWrapper queryWrapper = new QueryWrapper();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(getEntityClass());
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        //最基本的参数相等查询
        for (TableFieldInfo info : fieldList) {
            String column = info.getColumn();
            String name = info.getField().getName();
            String val = getReqParamVal(name);
            if (!StringUtils.isEmpty(val)) {
                queryWrapper.eq(column, val);
            }
        }
        return queryWrapper;
    }

    private String[] getReqParamVals(String key) {
        return request.getParameterMap().getOrDefault(key, new String[]{});
    }

    private String getReqParamVal(String key) {
        return getReqParamVal(key, "");
    }

    private String getReqParamVal(String key, String val) {
        String[] vals = getReqParamVals(key);
        return vals.length == 1 ? vals[0] : val;
    }

}
