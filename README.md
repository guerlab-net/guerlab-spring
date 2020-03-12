# guerlab-spring ![](https://img.shields.io/maven-central/v/net.guerlab/guerlab-spring.svg)![](https://img.shields.io/badge/LICENSE-LGPL--3.0-brightgreen.svg)

spring 扩展工具集，包含cloud项目常用依赖包、通用工具包与自动配置、通用mapper与分页支持、mybatis自动配置与类型转换支持、mysql、redis自动配置、searchparams支持、swagger、基于redis的锁控制、上传支持、阿里云oss上传支持

## 依赖管理
```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>net.guerlab.spring</groupId>
            <artifactId>guerlab-spring</artifactId>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## 子项目列表

|子项目|说明|
|:--|--|
|guerlab-spring-commons|通用工具包与自动配置|
|guerlab-spring-mybatis-starter|mybatis自动配置与类型转换支持|
|guerlab-spring-redis-starter|redis自动配置|
|guerlab-spring-searchparams|searchparams支持|
|guerlab-spring-searchparams-mybatisplus|searchparams支持 myBatis Plus扩展|
|guerlab-spring-searchparams-tkmapper|searchparams支持 tk.mapper扩展|
|guerlab-spring-swagger2-cloud-starter|swagger2聚合自动配置|
|guerlab-spring-swagger2-starter|启用swagger2|
|guerlab-spring-swagger2-ui-starter|启用swagger2-ui|
|guerlab-spring-task-starter|基于redis的简易锁控制|
|guerlab-spring-upload-aliyun-oss-starter|阿里云oss上传支持|
|guerlab-spring-upload-core|上传支持数据结构定义|
|guerlab-spring-upload-starter|上传支持|

## wiki

- [Gitee](https://gitee.com/guerlab_net/guerlab-spring/wikis/pages)

## changelog

- [Gitee](https://gitee.com/guerlab_net/guerlab-spring/wikis/pages)
