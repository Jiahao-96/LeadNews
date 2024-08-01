package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmLoginDto;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.AppJwtUtil;
import com.heima.wemedia.mapper.WmUserMapper;
import com.heima.wemedia.service.WmUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WmUserServiceImpl extends ServiceImpl<WmUserMapper, WmUser> implements WmUserService {

    @Override
    public ResponseResult login(WmLoginDto dto) {
        //1. 先根据帐号查询用户
        WmUser wmUser = lambdaQuery().eq(WmUser::getName, dto.getName()).one();
        if (wmUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "帐号不存在");
        }

        //2. 再校验密码是否正确。实际开发中，建议使用Bcrypt，比md5更安全
        String md5pwd = DigestUtils.md5Hex(dto.getPassword() + wmUser.getSalt());
        if (!md5pwd.equals(wmUser.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        //3. 给客户端返回结果。data里要有一个user是用户信息，有一个token是令牌
        Map<String, Object> data = new HashMap<>();
        data.put("token", AppJwtUtil.getToken(wmUser.getId().longValue()));
        wmUser.setPassword(null);
        wmUser.setSalt(null);
        data.put("user", wmUser);

        return ResponseResult.okResult(data);
    }
}