package net.guerlab.spring.mybatis.autoconfigure;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis配置
 *
 * @author guer
 *
 */
@Configuration
@ConditionalOnClass({
        DataSource.class, SqlSessionTemplate.class, SqlSessionFactory.class
})
@EnableTransactionManagement
public class MyBatisAutoconfigure {

    /**
     * 会话模版配置
     *
     * @param sqlSessionFactory
     *            会话工厂
     * @return 会话模板
     */
    @ConditionalOnMissingBean(SqlSessionTemplate.class)
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(
            SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
