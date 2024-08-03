package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmNewsMaterial;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.threadlocal.WmThreadLocalUtil;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.mapper.WmNewsMaterialMapper;
import com.heima.wemedia.service.WmNewsService;
import com.heima.wemedia.service.WmNewsTaskService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    @Autowired
    private WmMaterialMapper wmMaterialMapper;

    @Autowired
    private WmNewsTaskService wmNewsTaskService;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 查询文章列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult list(WmNewsPageReqDto dto) {
        //检查是否没有值
        dto.checkParam();
        //查询数据

        WmUser wmUser = WmThreadLocalUtil.getUser();
        Integer userId = wmUser.getId();
        Page<WmNews> page = lambdaQuery().eq(dto.getStatus()!=null,WmNews::getStatus,dto.getStatus())
                .eq(WmNews::getUserId,userId)
                .gt(dto.getBeginPubdate()!=null,WmNews::getPublishTime,dto.getBeginPubdate())
                .lt(dto.getEndPubdate()!=null,WmNews::getPublishTime,dto.getEndPubdate())
                .eq(dto.getChannelId()!=null,WmNews::getChannelId,dto.getChannelId())
                .like(dto.getKeyword()!=null,WmNews::getContent,dto.getKeyword())
                .page(new Page<WmNews>(dto.getPage(),dto.getSize()));
        PageResponseResult result = new PageResponseResult(dto.getPage(),dto.getSize(), (int) page.getTotal());
        result.setData(page.getRecords());
        return result;
    }

    /**
     * 保存-修改-提交草稿为一体的方法
     *  主方法
     * @param dto
     * @return
     */
    @Override
    public ResponseResult submit(WmNewsDto dto) {
        //TODO
        //6.返回数据
        return ResponseResult.okResult("文章已提交审核");
    }

    /**
     * 文章上下架
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult downOrUp(WmNewsDto dto) {
       //TODO
        return ResponseResult.okResult("文章上下架完成");
    }
}
