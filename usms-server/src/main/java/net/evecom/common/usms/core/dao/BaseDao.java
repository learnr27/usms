/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.core.dao;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <P><B>描述: </B> 数据层基础类接口  </P>
 *
 * @param <T> the type parameter
 * @author Wash Wang
 * @version 2.0
 * @created 2017/05/23 12:00:00
 */
public interface BaseDao<T, ID extends Serializable> {

    /**
     * 查询数据总量
     *
     * @param sqlString sql语句
     * @param values    参数值
     * @return count by sql
     */
    long getCountBySql(String sqlString, Object[] values);

    /**
     * 根据sql语句查询，返回List
     *
     * @param sqlString the sql string
     * @param values    the values
     * @return list
     */
    List<Object> queryObject(String sqlString, Object[] values);

    /**
     * 根据sql语句分页查询，返回List
     *
     * @param sqlString sql语句
     * @param values    参数值
     * @param page      the page
     * @param pageSize  the page size
     * @return 查询结果 list
     */
    List<Object> queryObjectByPage(String sqlString, Object[] values, int page, int pageSize);

    /**
     * 查询返回ListMap
     *
     * @param sqlString
     * @param values
     * @return
     */
    List<Map<String, Object>> queryMap(String sqlString, Object[] values);

    /**
     * 返回分页过的List Map
     *
     * @param sqlString
     * @param values
     * @param page
     * @param pageSize
     * @return
     */
    Page<Map<String, Object>> queryMapByPage(String sqlString, Object[] values, int page, int pageSize);

    /**
     * 根据sql语句查询。也可以执行返回数据集的存储过程, 适用于VModel
     *
     * @param <M>         the type parameter
     * @param entityClass the clazz
     * @param sqlString   the sql string
     * @param values      the values
     * @return list
     */
    <M> List<M> queryBySql(Class<M> entityClass, String sqlString, Object[] values);

    /**
     * 根据sql语句查询。也可以执行返回数据集的存储过程 , 适用于Model
     *
     * @param sqlString the sql string
     * @param values    the values
     * @return list
     */
    List<T> queryBySql(String sqlString, Object[] values);

    /**
     * 根据sql语句查询（分页）, 适用于VModel
     *
     * @param <M>         the type parameter
     * @param entityClass the entityClass
     * @param sqlString   the sql string
     * @param values      the values
     * @param page        the page
     * @param pageSize    the page size
     * @return list
     */
    <M> List<M> queryBySql(Class<M> entityClass, String sqlString, Object[] values, int page, int pageSize);

    /**
     * 根据sql语句查询（分页）, 适用于Model
     *
     * @param sqlString the sql string
     * @param values    the values
     * @param page      the page
     * @param pageSize  the page size
     * @return list
     */
    List<T> queryBySql(String sqlString, Object[] values, int page, int pageSize);

    /**
     * 根据sql语句查询，并返回一条记录
     *
     * @param <M>         the type parameter
     * @param entityClass the entityClass
     * @param sqlString   the sql string
     * @param values      the values
     * @return m
     */
    <M> M queryUniqueBySql(Class<M> entityClass, String sqlString, Object[] values);

    /**
     * 根据sql语句查询，并返回一条记录
     *
     * @param sqlString the sql string
     * @param values    the values
     * @return t
     */
    T queryUniqueBySql(String sqlString, Object[] values);

    /**
     * 事务执行sql语句，也可执行无返回结果的存储过程
     *
     * @param sqlString the sql string
     * @param values    the values
     * @return 受影响的记录数 int
     * @throws Exception the exception
     */
    int executeSql(String sqlString, Object[] values) throws Exception;

    /**
     * 分页查询，返回本类对象
     *
     * @param <T>       the type parameter
     * @param sqlString the sql string
     * @param values    the values
     * @param page      the page
     * @param pageSize  the page size
     * @return the page
     */
    Page<T> queryByPage(String sqlString, Object[] values, int page, int pageSize);

    /**
     * 分页查询，返回方法中的对象
     *
     * @param entityClass
     * @param sqlString
     * @param values
     * @param page
     * @param pageSize
     * @param <M>
     * @return
     */
    <M> Page<M> queryByPage(Class<M> entityClass, String sqlString, Object[] values, int page, int pageSize);

    /**
     * 保存或者更新
     *
     * @param entity
     * @return
     */
    T saveOrUpdate(T entity);

    /**
     * 查询全部
     *
     * @return
     */
    List<T> findAll();

    /**
     * 查询单个
     *
     * @param entityClass
     * @param entityId
     * @param <T>
     * @return
     */
    <M> M findOne(Class<M> entityClass, Object entityId);

    /**
     * 查询单个
     *
     * @param entityId
     * @return
     */
    T findOne(ID entityId);

    /**
     * 单行删除
     *
     * @param entityClass
     * @param entityId
     * @param <M>
     */
    <M> void delete(Class<M> entityClass, Object entityId);

    /**
     * 多行删除
     *
     * @param entityClass
     * @param entityIds
     * @param <M>
     */
    <M> void delete(Class<M> entityClass, Object[] entityIds);

    /**
     * 单行删除
     *
     * @param entityId
     */
    void delete(ID entityId);

    /**
     * 多行删除
     *
     * @param entityIds
     */
    void delete(ID[] entityIds);

}
