# guerlab-spring

spring 扩展工具集

## maven仓库地址

```
<dependency>
	<groupId>net.guerlab</groupId>
	<artifactId>guerlab-spring</artifactId>
	<version>0.4.1</version>
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
|guerlab-spring-swagger2-starter|配合guerlab-spring-swagger2-cloud-starter进行swagger2聚合|
|guerlab-spring-task-starter|基于redis的锁控制|
|guerlab-spring-upload-starter|上传支持|
|guerlab-spring-upload-aliyun-oss-starter|阿里云oss上传支持|

## 更新记录

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