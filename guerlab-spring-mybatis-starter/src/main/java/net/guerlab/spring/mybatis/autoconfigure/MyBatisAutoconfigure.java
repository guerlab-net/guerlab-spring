package net.guerlab.spring.mybatis.autoconfigure;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis配置
 *
 * @author guer
 *
 */
@Configuration
@ConditionalOnClass({
        DataSource.class, SqlSessionTemplate.class, SqlSessionFactory.class, PlatformTransactionManager.class
})
@EnableTransactionManagement
public class MyBatisAutoconfigure {

    /**
     * 默认事务管理器名称
     */
    public static final String DEFAULT_TRANSACTION_MANAGER_BEAN_NAME = "defaultTransactionManager";

    /**
     * 会话模版配置
     *
     * @param sqlSessionFactory
     *            会话工厂
     * @return 会话模板
     */
    @ConditionalOnMissingBean(SqlSessionTemplate.class)
    @ConditionalOnBean(SqlSessionFactory.class)
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 配置默认事务管理器
     *
     * @param dataSource
     *            数据源
     * @return 事务管理器
     */
    @ConditionalOnBean(DataSource.class)
    @Bean(DEFAULT_TRANSACTION_MANAGER_BEAN_NAME)
    public PlatformTransactionManager defaultTransactionManager(DataSource dataSource) {

        return new DataSourceTransactionManager(dataSource);
    }
}
