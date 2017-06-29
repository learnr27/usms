/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.core.util.JpaUtil;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.GridEntity;
import net.evecom.common.usms.entity.InstitutionEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.GridDao;
import net.evecom.common.usms.uma.dao.InstitutionDao;
import net.evecom.common.usms.uma.dao.UserDao;
import net.evecom.common.usms.uma.dao.custom.UserDaoCustom;
import net.evecom.common.usms.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/6/28 下午6:33
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<UserEntity> implements UserDaoCustom {

    /**
     * @see InstitutionDao
     */
    @Autowired
    private InstitutionDao institutionDao;

    /**
     * @see GridDao
     */
    @Autowired
    private GridDao gridDao;

    /**
     * 根据分页查询用户Model
     *
     * @param page          当前页码
     * @param size          页面数据量
     * @param institutionId
     * @param sqlFilter
     * @return
     */
    @Override
    public Page<UserVO> listUsersByPage(int page, int size, Long institutionId, SqlFilter sqlFilter) {
        List params = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if (institutionId == null) {
            sb.append("select u.id, u.login_name, u.name, u.enabled, s.mobile from usms_users u ")
                    .append("left join usms_staffs s on u.staff_id = s.id ")
                    .append(sqlFilter.getWhereSql());
        } else {
            sb.append("select u.id, u.login_name, u.name, u.enabled, s.mobile from ( ")
                    .append("select distinct u.id, u.login_name, u.name, u.staff_id, ")
                    .append("u.enabled, ui.institution_id ")
                    .append("from usms_users u left join usms_user_institution ui on u.id = ui.user_id ")
                    .append("where ui.institution_id = ?) u ")
                    .append("left join usms_staffs s ")
                    .append("on u.staff_id = s.id ")
                    .append(sqlFilter.getWhereSql());
            params.add(institutionId);
        }
        params.addAll(sqlFilter.getParams());
        String sql = sb.toString();
        Page<Map<String, Object>> pageBean = queryForMap(sql, params.toArray(), page, size);
        List<UserVO> results = new ArrayList<>();

        for (Map<String, Object> var : pageBean.getContent()) {
            UserVO userVO = new UserVO();

            // 获得用户id
            Long userId = ((BigDecimal) var.get("ID")).longValue();

            // 获得登入名
            String loginName = (String) var.get("LOGIN_NAME");

            // 获得组织机构列表
            List<InstitutionEntity> insts = institutionDao.listInstsByUserId(userId);

            // 获得组织机构名
            List<String> instNames = new ArrayList<>();
            if (insts != null) {
                Long minLevel = Long.MAX_VALUE;
                for (InstitutionEntity inst : insts) {
                    minLevel = Math.min(minLevel, inst.getTreeLevel());
                }
                for (InstitutionEntity inst : insts) {
                    if (inst.getTreeLevel().equals(minLevel)) {
                        instNames.add(inst.getName());
                    }
                }
            }

            List<String> gridNames = new ArrayList<>();
            List<GridEntity> grids = gridDao.listGridsByLoginName(loginName);
            if (grids != null) {
                Long minLevel = Long.MAX_VALUE;
                for (GridEntity grid : grids) {
                    minLevel = Math.min(minLevel, grid.getLvl());
                }
                for (GridEntity grid : grids) {
                    if (grid.getLvl().equals(minLevel)) {
                        gridNames.add(grid.getName());
                    }
                }
            }
            // 设置变量
            userVO.setId(userId);
            userVO.setLoginName(loginName);
            userVO.setName((String) var.get("NAME"));
            userVO.setMobile((String) var.get("MOBILE"));
            userVO.setEnabled(((BigDecimal) var.get("ENABLED")).longValue());
            userVO.setInstitutionNames(instNames.toArray(new String[instNames.size()]));
            userVO.setGridNames(gridNames.toArray(new String[gridNames.size()]));
            results.add(userVO);
        }
        return new PageImpl<>(results, new PageRequest(page, size), pageBean.getTotalElements());
    }


    @Override
    public List<UserEntity> listUsersByLoginNames(String[] loginNames) {
        String queryParams = JpaUtil.getQuestionMarks(loginNames);
        StringBuilder sb = new StringBuilder();
        sb.append("select * from usms_users u where u.login_name in (")
                .append(queryParams).append(")");
        String sql = sb.toString();
        return super.queryForClass(sql, loginNames);
    }


    @Override
    public void updateRoles(Long userId, String[] roleIds) {
        String sql = "delete from usms_user_role where user_id =:userId";
        Query query = manager.createNativeQuery(sql);
        query.setParameter("userId", userId);
        query.executeUpdate();
        if (roleIds != null) {
            for (String id : roleIds) {
                Long roleId = Long.valueOf(id);
                sql = "insert into usms_user_role values(:roleId,:userId)";
                query = manager.createNativeQuery(sql);
                query.setParameter("roleId", roleId);
                query.setParameter("userId", userId);
                query.executeUpdate();
            }
        }

    }

    @Override
    public void updateInstitutions(Long userId, String[] institutionIds) {
        String sql = "delete from usms_user_institution ui where user_id=:userId";
        Query query = manager.createNativeQuery(sql);
        query.setParameter("userId", userId);
        query.executeUpdate();
        if (institutionIds != null) {
            for (String id : institutionIds) {
                Long institutionId = Long.valueOf(id);
                sql = "insert into usms_user_institution values(:userId, :institutionId)";
                query = manager.createNativeQuery(sql);
                query.setParameter("userId", userId);
                query.setParameter("institutionId", institutionId);
                query.executeUpdate();
            }
        }
    }

    @Override
    public List<UserEntity> listUsersByInstNames(String[] instNames) {
        String queryParams = JpaUtil.getQuestionMarks(instNames);
        StringBuilder sb = new StringBuilder();
        sb.append("select * from usms_users u where u.id in ( \n")
                .append("select distinct ui.user_id from usms_user_institution ui where ui.institution_id in (\n")
                .append("select i.id from usms_institutions i where i.name in (")
                .append(queryParams).append(") and i.enabled = 1 ))");
        String sql = sb.toString();
        return super.queryForClass(UserEntity.class, sql, instNames);
    }

    @Override
    public List<UserEntity> listUsersByRoleNames(String[] roleNames) {
        String queryParams = JpaUtil.getQuestionMarks(roleNames);
        StringBuilder sb = new StringBuilder();
        sb.append("select * from usms_users u where u.id in")
                .append(" (select ur.user_id from usms_user_role ur")
                .append(" where ur.role_id in")
                .append(" (select r.id from usms_roles r where r.name in (")
                .append(queryParams).append(") and r.enabled = 1 ))")
                .append(" and u.enabled = 1");
        String sql = sb.toString();
        return super.queryForClass(UserEntity.class, sql, roleNames);
    }


}
