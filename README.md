# guerlab-spring

spring 扩展工具集，包含cloud项目常用依赖包、通用工具包与自动配置、通用mapper与分页支持、mybatis自动配置与类型转换支持、mysql、redis自动配置、searchparams支持、swagger、基于redis的锁控制、上传支持、阿里云oss上传支持

## maven配置

```
<dependency>
	<groupId>net.guerlab</groupId>
	<artifactId>guerlab-spring</artifactId>
	<version>2.0.0-SNAPSHOT</version>
</dependency>
<repositories>
	<repository>
		<id>sonatype-nexus-snapshots</id>
		<url>https://oss.sonatype.org/content/repositories/snapshots/</url>
		<releases>
			<enabled>false</enabled>
		</releases>
		<snapshots>
			<enabled>true</enabled>
		</snapshots>
	</repository>
</repositories>
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

## wiki

- [Gitee](https://gitee.com/guerlab_net/guerlab-spring/wikis/pages)

## changelog

- [Gitee](https://gitee.com/guerlab_net/guerlab-spring/wikis/pages)