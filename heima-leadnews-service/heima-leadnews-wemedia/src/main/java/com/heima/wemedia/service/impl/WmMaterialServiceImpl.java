package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.file.service.FileStorageService;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.threadlocal.WmThreadLocalUtil;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.mapper.WmNewsMaterialMapper;
import com.heima.wemedia.service.WmMaterialService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;
    @Autowired
    private WmMaterialMapper wmMaterialMapper;
    /**
     * 素材上传
     *
     * @param file
     * @return
     */
    @Override
    @Transactional
    public ResponseResult upload(MultipartFile file) throws IOException {
        //1.上传图片到minio中
        String fileName = file.getName() + UUID.randomUUID();
        String url = fileStorageService.uploadImgFile("",fileName,file.getInputStream());
        //上传到数据库
        WmMaterial wmMaterial = new WmMaterial((Integer) null,WmThreadLocalUtil.getUser().getApUserId(),url, (short) 0, (short) 0,new Date());
        wmMaterialMapper.insert(wmMaterial);
        //返回
        return ResponseResult.okResult(wmMaterial);
    }

    /**
     * 查询素材列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult list(WmMaterialDto dto) {
       //TODO
        //检查是否有值
        dto.checkParam();
        //开始查询
        WmUser wmUser = WmThreadLocalUtil.getUser();
        Integer userId = wmUser.getApUserId();
        Page<WmMaterial> page = lambdaQuery().eq(WmMaterial::getUserId, userId)
                .eq(dto.getIsCollection()==1,WmMaterial::getIsCollection,dto.getIsCollection())
                .page(new Page<WmMaterial>(dto.getPage(),dto.getSize()));
        PageResponseResult result = new PageResponseResult(dto.getPage(),dto.getSize(), (int) page.getTotal());
        result.setData(page.getRecords());
        return result;
    }

    /**
     * 删除素材
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult del(Integer id) {
        //TODO
        return null;
    }

    /**
     * 取消收藏
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult cancel(Integer id) {
        //TODO
        return null;
    }

    /**
     * 收藏
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult collect(Integer id) {
        //TODO
        return null;
    }
}
