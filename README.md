# mmall_learning
---
## 项目简介
- 本项目是[慕课网 Java大牛 带你从0到上线开发企业级电商项目](http://coding.imooc.com/class/96.html)的学习成果展示. 
- 是使用ssm框架搭建的电商项目后台接口
- 感谢[网易云音乐](http://music.163.com/#/playlist?id=426490641&userid=9567158 "歌单 孔祥羽看书用")提供舒缓音乐让我专注Coding
## 版本说明
> 时间 : 2018年3月19日
> - 项目v1.0已完成开发与上线
> - 在线测试接口 : http://www.duruonanni.site/index.jsp

---
## 项目说明
### 技术选型
- 开发环境 : Windows10 1709
- 线上测试环境 : CentOS 6.8 64
- 开发工具 : IDEA 2017.3.4
- JDK : 7u80 64
- Maven : 3.0.5
- Mysql : 5.1.37
- git : 2.7.5
- nginx : 1.10.2
- vsftpd : vsftpd-2.2.2-24.el6.x86_64
- 其他技术 : Guava/Jackson/JodaTime/Simditor/logback/Alipay
### 模块说明和技术要点
- 模块测试[WiKi](https://github.com/duruonanni/mmall_learning/wiki)
- 用户模块: [port_user](https://github.com/duruonanni/mmall_learning/wiki/port_user)
    - 用户密码使用MD5进行加密,并为密码加salt确保安全
    - 使用Google Guava处理集合中的null元素
    - sql语句严格规避横向越权,纵向越权
    - 设计高复用性泛型响应对象ServerResponse,对返回参数进行统一封装
    - 重置密码时,使用token判断保证同一用户登录,防止越权
    - **json返回时,不提供用户密保问题的明文答案,防止恶意通过密保修改密码盗取账号**
- 分类模块 : [backend_category](https://github.com/duruonanni/mmall_learning/wiki/backend_category)
    - 无限子节点层级结构的设计
        - 通过设置id和ParentId,实现无限子节点的数据结构
    - 平级子节点的数据调用
    - 无限层级子节点数据的调用
        - 使用递归方法,调用无限子节点(根节点时跳出递归)
- 商品模块 : [portal_product](https://github.com/duruonanni/mmall_learning/wiki/portal_product) | [backend_product](https://github.com/duruonanni/mmall_learning/wiki/backend_product)
    - 使用Mybatis_PageHelper实现分页
        - 高效分页,动态排序
    - 使用抽象模型Vo对商品数据进行重组
    - 使用Vsftp搭建ftp服务上传商品图片
    - 使用Simditor回调格式,包装富文本传递的图片
- 购物车模块 : [portal_cart](https://github.com/duruonanni/mmall_learning/wiki/portal_cart)
    - 商品总价计算,复用封装
    - 使用BigDecimal解决商业运算中的小数精度丢失问题
        - 设计了BigDecimalUtil工具类实现总价的运算
- 收货地址模块 : [portal_shipping](https://github.com/duruonanni/mmall_learning/wiki/portal_shipping
    - 添加元素时同步获取自增主键
        - mybatis.generator插件自动生成的insert语句不会为id赋值,需要在sql中手动添加下面的属性对主键进行自动赋值
            - `useGeneratedKeys="true" keyProperty="id"`
- 订单与支付模块 : [portal_order](https://github.com/duruonanni/mmall_learning/wiki/portal_order) | [backend_order](https://github.com/duruonanni/mmall_learning/wiki/backend_order)
    - 封装OrderItemVo,ShippingVo进行数据传递
        - 使用装配类完成dao-->vo的转换
    - 使用枚举类封装订单状态信息
    - 真实对接支付宝沙箱,调用Alipay API实现支付宝当面付功能
        - 使用支付宝回调规则创建传递参数
        - 使用RSA2验证签名
        - 模仿支付宝demo避免重复支付
        - 调试支付宝SDK时,使用了natapp外网穿透,方便支付宝回调
            - 上线模式直接为服务器配置真实域名,方便回调
        - 生成支付二维码,并持久化到vsftp图片服务器中
        - 可扩展的订单号生成规则
            - 目前是使用生成订单的时间毫秒值加100以内随机数做订单号
            - v2.0会扩大成分布式项目,会使用更接近生产环境的订单号生成规则
- 线上部署
    - 本项目部署在腾讯云服务器上
    - 域名解析来自namesilo
    - 通过nginx转发到不同的二级域名上实现特定功能
<!-- 参考文献 -->