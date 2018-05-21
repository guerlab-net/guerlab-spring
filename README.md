# guerlab-spring

spring 扩展工具集

## maven仓库地址

```
<dependency>
	<groupId>net.guerlab</groupId>
	<artifactId>guerlab-spring</artifactId>
	<version>2.0.0-SNAPSHOT</version>
</dependency>
```

## 子项目列表

|子项目|说明|
|:--|--|
|guerlab-spring-cloud-starter|cloud项目常用依赖包|
|guerlab-spring-commons|通用工具包与自动配置|
|guerlab-spring-mapper-starter|通用mapper与分页支持|
|guerlab-spring-mybatis-starter|mybatis自动配置与类型转换支持|
|guerlab-spring-mysql-starter|mybatis+mysql|
|guerlab-spring-redis-starter|redis自动配置|
|guerlab-spring-searchparams|searchparams支持|
|guerlab-spring-swagger2-cloud-starter|swagger2聚合自动配置|
|guerlab-spring-swagger2-starter|启用swagger2|
|guerlab-spring-swagger2-ui-starter|启用swagger2-ui|
|guerlab-spring-task-starter|基于redis的锁控制|
|guerlab-spring-upload-starter|上传支持|
|guerlab-spring-upload-aliyun-oss-starter|阿里云oss上传支持|

## 更新记录

### 20180521 v2.0.0-SNAPSHOT

- 更新net.guerlab.spring.commons.exception.handler.GlobalExceptionHandler,增强对javax.validation系列注解的支持,并增加语言包支持

### 20180519 v2.0.0-SNAPSHOT

- 更新依赖guerlab-commons 1.3.0 -> 1.4.0
- 更新依赖guerlab-web 1.3.0 -> 1.4.0
- 更新net.guerlab.spring.commons.exception包下的所有异常信息类，增加根据系统语言环境选择默认信息的处理
- 调整net.guerlab.spring.commons.autoconfigure.ObjectMapperAutoconfigure,删除ObjectMapper的生成方法,调整为对默认ObjectMapper Bean对象直接进行扩展处理
- 调整net.guerlab.spring.commons.autoconfigure.WebMvcAutoconfigure,修改对HttpMessageConverter列表的处理
- 调整net.guerlab.spring.redis.autoconfigure.RedisTemplateAutoconfigure以兼容net.guerlab.spring.commons.autoconfigure.ObjectMapperAutoconfigure的修改
- 修改net.guerlab.spring.searchparams.SearchParamsUtils的方法的命名以符合方法该进行的处理内容
- 删除guerlab-spring-searchparams包对swagger的依赖
- 删除net.guerlab.spring.commons.util.TimeUtil

### 20180518 v2.0.0-SNAPSHOT

- 优化RestControllerAdvice处理
- 增强guerlab-spring-swagger2-starter功能,增加swagger的可配置支持
- 增加guerlab-spring-swagger2-ui-starter,用于启用对swagger2-ui的访问支持

### 20180508 v2.0.0-SNAPSHOT

- 优化searchParams对transient的支持

### 20180507 v2.0.0-SNAPSHOT

- 增加lombok支持

### 20180504 v2.0.0-SNAPSHOT

- 更新cors配置

### 20180429 v2.0.0-SNAPSHOT

- 更新依赖spring-boot 1.5.12.RELEASE -> 2.0.1.RELEASE
- 更新依赖spring-cloud Edgware.SR3 -> Finchley.RC1
- 更新依赖mybatis-starter.version 1.3.1 -> 1.3.2
- 更新依赖pagehelper-spring-boot-starter 1.2.1 -> 1.2.5
- 更新依赖pagehelper 5.1.1 -> 5.1.4
- 更新依赖mapper-spring-boot-starter 1.1.4 -> 2.0.2
- 更新依赖mapper 3.4.3 -> 1.0.2
- 新增net.guerlab.spring.commons.autoconfigure.SecurityAutoconfigure
- 删除net.guerlab.spring.commons.autoconfigure.WebMvcAutoconfigure中关于CORS的处理
- 删除net.guerlab.spring.commons.autoconfigure.RestTemplateAutoconfigure.loadBalancedAsyncRestTemplate方法

### 20180426 v0.4.1

- 更新net.guerlab.spring.upload.handler.UploadHandler增加order()方法，使UploadHandler的执行顺序可以定制

### 20180426 v0.4.0

- 更新依赖guerlab-commons 1.2.0 -> 1.3.0
- 更新依赖guerlab-web 1.2.0 -> 1.3.0
- guerlab-spring-commons删除net.guerlab.spring.commons.util.CollectionUtil,已迁移至guerlab-commons包net.guerlab.commons.collection.CollectionUtil
- guerlab-spring-commons删除net.guerlab.spring.commons.list.ListObject,已迁移至guerlab-web包net.guerlab.web.result.ListObject
- guerlab-spring-commons删除net.guerlab.spring.commons.list.ReadDataListCommand,已迁移至guerlab-web包net.guerlab.web.result.ReadDataListCommand
- guerlab-spring-commons删除net.guerlab.spring.commons.util.ResultUtil,已迁移至guerlab-web包net.guerlab.web.result.ResultUtil
- guerlab-spring-searchparams删除net.guerlab.spring.searchparams.FieldHelper,已迁移至guerlab-commons包net.guerlab.commons.reflection.FieldUtil

### 20180424 v0.3.8

- guerlab-spring-commons删除net.guerlab.spring.commons.geo包,net.guerlab.spring.commons.geo独立成guerlab-geo项目

### 20180424 v0.3.7

- guerlab-spring-upload-starter增加扩展上传处理接口
- 增加guerlab-spring-upload-aliyun-oss-starter扩展
- guerlab-spring-searchparams/net.guerlab.spring.searchparams.SearchParamsUtils增加toAllMap方法

### 20180424 v0.3.6

- net.guerlab.spring.searchparams.SearchModelType增加4种方式支持（START_WITH、START_NOT_WITH、END_WITH、END_NOT_WITH）
- net.guerlab.spring.commons.autoconfigure.ObjectMapperAutoconfigure增加对BigDecimal、BigInteger转换为string类型的支持，修正web请求时js的number精度缺失问题

### 20180419 v0.3.5

-  更新依赖版本

### 20180419 v0.3.4

-  更新依赖版本

### 20180419 v0.3.3

- net.guerlab.spring.commons.autoconfigure.ObjectMapperAutoconfigure增加对Long转换为string类型的支持，修正web请求时js的number精度缺失问题

### 20180415 v0.3.2

- net.guerlab.spring.upload.helper.UploadFileHelper增强获取文件类型的处理