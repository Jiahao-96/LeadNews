//保存草稿、提交文章、修改文章都是一个请求（太多相同操作）
1.保存草稿，WmNews先构建再通过id判断是新增还是修改
    1.1 WmNews的构建，要根据dto的type判断页面布局是三图、单图、无图还是自动
    如果是自动，到了三张就是多图，一张两张就是单图，一张没有就是无图
    如果不是自动，有多少拿多少
    初始数据格式[{key1:val1, key2:val2},{key1:val1, key2:val2}...]
    这是个数组，要用JSON.parseArray(source,Map.class)这样可以解析出一个List<Map>
    List<Map>可以通过stream流先过滤所有的图片内容，再把图片内容的value值用.map拿出来，.distinct去重，然后用.collect收集
    List<String>根据需要可以用.limit取出想要的个数
    最后WmNews的Images属性，都是"url-1,url-2,url-3...."，string.join一下就可以
2.处理文章和素材（图片）的关系，先删除和文章id相关的所有图片关系，再重构关系
    2.1 将素材（图片）和文章用关系表关联起来，我们是对一个文章做操作，一个文章可以有多个图片素材
    首先，要判断图片列表不能为空，同样的图片用了多次应该去重。
    要把文章用的所有图片从数据库里查出来，查出来以后如果数据库里没有，要抛异常
    将图片的id拿出来，放到文章图片关系表里
3.通过文章的状态码判断文章是保存草稿，还是修改已发布文章和新发布文章
    3.1如果是保存草稿，在已经save了文章和重构了关系以后就可以结束方法了
    3.2如果是修改已发布文章或者新发布文章，需要提交审核（异步审核提高性能）
        3.2.1 本地dfa审查文字，远程百度审查文字，远程百度审查图片
        发布文章，更改文章状态