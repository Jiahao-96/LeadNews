package com.heima.common.constant;

public class WemediaConstants {

    /**素材收藏状态：已收藏*/
    public static final short COLLECT_MATERIAL = 1;
    /**素材收藏状态：未收到*/
    public static final short CANCEL_COLLECT_MATERIAL = 0;

    /**自媒体文章中图片段落的类型标识*/
    public static final String WM_NEWS_TYPE_IMAGE = "image";

    /**自媒体文章封面类型：无图*/
    public static final short WM_NEWS_NONE_IMAGE = 0;
    /**自媒体文章封面类型：单图*/
    public static final short WM_NEWS_MANY_IMAGE = 3;
    /**自媒体文章封面类型：多图*/
    public static final short WM_NEWS_TYPE_AUTO = -1;
    /**自媒体文章封面类型：自动*/
    public static final short WM_NEWS_SINGLE_IMAGE = 1;

    /**素材引用类型：被文章内容引用*/
    public static final short WM_COVER_REFERENCE = 1;
    /**素材引用类型：被文章封面引用*/
    public static final short WM_CONTENT_REFERENCE = 0;
}