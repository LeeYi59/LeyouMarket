package com.ly.item.service;

import com.ly.item.pojo.SpecGroup;
import com.ly.common.enums.ExceptionEnum;
import com.ly.common.exception.LyException;
import com.ly.item.mapper.SpecGroupMapper;
import com.ly.item.mapper.SpecParamMapper;
import com.ly.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: ly
 * @description: Lyshop
 * @author: Lee
 * @create: 2018-12-24 13:43
 **/
@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper groupMapper;

    @Autowired
    private SpecParamMapper paramMapper;


    //查询规格组
    public List<SpecGroup> queryGroupByid(Long cid) {
        //查询条件
        SpecGroup specGroup=new SpecGroup();
        specGroup.setCid(cid);
        //查询
        List<SpecGroup> groups = groupMapper.select(specGroup);
        if (CollectionUtils.isEmpty(groups)){
            //没查到
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        return groups;
    }

    //新增规格组
    public void saveSpecGroup(SpecGroup specGroup) {
        int count = groupMapper.insert(specGroup);
        if (count != 1) {
            throw new LyException(ExceptionEnum.SPEC_GROUP_CREATE_FAILED);
        }
    }

    //删除规格组
    public void deleteSpecGroup(Long id) {
        if (id == null) {
            throw new LyException(ExceptionEnum.INVALID_PARAM);
        }
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(id);
        int count = groupMapper.deleteByPrimaryKey(specGroup);
        if (count != 1) {
            throw new LyException(ExceptionEnum.DELETE_SPEC_GROUP_FAILED);
        }
    }

    //修改规格组
    public void updateSpecGroup(SpecGroup specGroup) {
        int count = groupMapper.updateByPrimaryKey(specGroup);
        if (count != 1) {
            throw new LyException(ExceptionEnum.UPDATE_SPEC_GROUP_FAILED);
        }
    }


    //新增规格参数
    public void saveSpecParam(SpecParam specParam) {
        int count = paramMapper.insert(specParam);
        if (count != 1) {
            throw new LyException(ExceptionEnum.SPEC_PARAM_CREATE_FAILED);
        }
    }

    //修改规格参数
    public void updateSpecParam(SpecParam specParam) {
        int count = paramMapper.updateByPrimaryKeySelective(specParam);
        if (count != 1) {
            throw new LyException(ExceptionEnum.UPDATE_SPEC_PARAM_FAILED);
        }
    }

    //删除规格参数
    public void deleteSpecParam(Long id) {
        if (id == null) {
            throw new LyException(ExceptionEnum.INVALID_PARAM);
        }
        int count = paramMapper.deleteByPrimaryKey(id);
        if (count != 1) {
            throw new LyException(ExceptionEnum.DELETE_SPEC_PARAM_FAILED);
        }
    }

    //查询指定规格参数
    public List<SpecParam> querySpecParams(Long gid, Long cid, Boolean searching, Boolean generic) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        specParam.setGeneric(generic);
        List<SpecParam> specParamList = paramMapper.select(specParam);
        if (CollectionUtils.isEmpty(specParamList)) {
            throw new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }
        return specParamList;
    }

    //查询规格组及组内规格参数
    public List<SpecGroup> querySpecsByCid(Long cid) {
        List<SpecGroup> specGroups = queryGroupByid(cid);

        List<SpecParam> specParams = querySpecParams(null, cid, null, null);

        Map<Long, List<SpecParam>> map = new HashMap<>();
        //遍历specParams
        for (SpecParam param : specParams) {
            Long groupId = param.getGroupId();
            if (!map.keySet().contains(param.getGroupId())) {
                //map中key不包含这个组ID
                map.put(param.getGroupId(), new ArrayList<>());
            }
            //添加进map中
            map.get(param.getGroupId()).add(param);
        }

        for (SpecGroup specGroup : specGroups) {
            specGroup.setParams(map.get(specGroup.getId()));
        }

        return specGroups;
    }
}
