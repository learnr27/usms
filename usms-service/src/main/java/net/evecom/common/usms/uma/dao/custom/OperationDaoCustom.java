/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.custom;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.vo.OperationVO;

import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/7/20 上午9:25
 */
public interface OperationDaoCustom extends BaseDao<OperationEntity> {

    /**
     * 检查是否能被删除
     *
     * @param id
     * @return
     */
    boolean canBeDeleted(Long id);


    /**
     * 查找权限ID对应的操作列表
     *
     * @param privilegeId
     * @return
     */
    List<OperationVO> listOpersByPrivId(Long privilegeId);

}
