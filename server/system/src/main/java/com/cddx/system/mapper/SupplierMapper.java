package com.cddx.system.mapper;

import com.cddx.domain.entity.SupplierEntity;
import com.cddx.domain.vo.SupplierVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenjunyan
 * @since 2021-12-06
 */
public interface SupplierMapper {
    /**
     * 查询
     *
     * @param id Entity主键
     * @return 结果
     */
    SupplierEntity selectSupplierByid(Long id);

    /**
     * 查询列表
     *
     * @param entity
     * @return 结果集合
     */
    List<SupplierVo> selectSupplierList(SupplierEntity entity);

    Integer add(SupplierEntity entity);

    Integer update(SupplierEntity entity);

    Integer batchRemove(@Param("ids") Long[] ids,@Param("userId") Long currentUserId,@Param("updateTime") Long updateTime);
}
