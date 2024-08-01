package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojo.ApUser;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.ApUserService;
import com.heima.utils.common.AppJwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {

    /**
     * 登录接口
     *   二合一，用户登录也是有游客登录
     * @param dto
     * @return
     */
    @Override
    public ResponseResult login(LoginDto dto) {
        //TODO
        return null;
    }

    /**
     * 根据用户id获取用户实体
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseResult<ApUser>findUserById(Integer userId) {
        ApUser apUser = getById(userId);
        return ResponseResult.okResult(apUser);
    }
}
