package com.cddx.system.service;

import com.cddx.base.PageGrid;
import com.cddx.domain.dto.AddSupplierDto;
import com.cddx.domain.dto.ListSupplierDto;
import com.cddx.domain.entity.SupplierEntity;
import com.cddx.domain.vo.SupplierVo;
import com.cddx.exception.http.ServerErrorException;
import com.cddx.mapper.SupplierMapper;
import com.cddx.utils.BeanUtils;
import com.cddx.utils.MyPage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ Description   :
 * @ Author        :  chenjunyan
 * @ CreateDate    :  2021/12/6-11:47
 * @ Version       :
 */
@Service
public class SupplierService {

    @Resource
    private SupplierMapper supplierMapper;

    @Transactional(rollbackFor = Exception.class)
    public void add(AddSupplierDto dto, Long currentUserId) {
        SupplierEntity entity = new SupplierEntity();
        BeanUtils.copyPropertiesIgnoreNull(dto,entity);
        entity.setCreateId(currentUserId);
        entity.setCreateTime(System.currentTimeMillis());
        entity.setLastUpdateId(currentUserId);
        entity.setLastUpdateTime(System.currentTimeMillis());
        entity.setDelFlag(false);
        supplierMapper.add(entity);
    }
    @Transactional(rollbackFor = Exception.class)
    public void update(AddSupplierDto dto, Long currentUserId) {
        SupplierEntity entity = new SupplierEntity();
        BeanUtils.copyPropertiesIgnoreNull(dto,entity);
        entity.setLastUpdateTime(System.currentTimeMillis());
        entity.setLastUpdateId(currentUserId);
        supplierMapper.update(entity);

    }

    public SupplierVo byId(Long id) {
        SupplierEntity supplierEntity = supplierMapper.selectSupplierByid(id);
        if(supplierEntity==null){
            throw new ServerErrorException(10009);
        }
        SupplierVo vo = new SupplierVo();
        BeanUtils.copyPropertiesIgnoreNull(supplierEntity,vo);
        return vo;
    }

    public MyPage<SupplierVo> pageList(PageGrid<ListSupplierDto> dto) {
        Integer pageSize = dto.getPageSize();
        Integer pageNo = dto.getPageNo();
        String supplierName = dto.getParameter().getSupplierName();
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setSupplierName(supplierName);
        supplierEntity.setDelFlag(false);
        Page page = PageHelper.startPage(pageNo, pageSize);
        supplierMapper.selectSupplierList(supplierEntity);
        MyPage<SupplierVo> myPage = new MyPage<>(page);
        return myPage;
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchRemove(Long[] ids, Long currentUserId) {
        supplierMapper.batchRemove(ids,currentUserId,System.currentTimeMillis());
    }

    public List<SupplierVo> list() {
        return supplierMapper.selectSupplierList(new SupplierEntity());
    }

//    public void batchDel(Long[] ids, Long id) {
//
//    }
}
