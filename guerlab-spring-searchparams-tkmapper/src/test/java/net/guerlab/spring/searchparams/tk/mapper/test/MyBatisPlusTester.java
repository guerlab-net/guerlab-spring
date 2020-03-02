package net.guerlab.spring.searchparams.tk.mapper.test;

import net.guerlab.spring.searchparams.SearchParamsUtils;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.session.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author guer
 */
public class MyBatisPlusTester {

    public static void main(String[] args) {
        TestSearchParams searchParams = new TestSearchParams();
        searchParams.setSingleValue("val");
        searchParams.setMultiValue(Arrays.asList("v1", "v2", "v3"));
        searchParams.setMultiValue2(Arrays.asList("v1", "v2"));

        DataSource dataSource = new SingleConnectionDataSource();

        Environment environment = new Environment.Builder("test").dataSource(dataSource)
                .transactionFactory(new JdbcTransactionFactory()).build();

        MapperHelper mapperHelper = new MapperHelper();
        mapperHelper.registerMapper(TestObjMapper.class);

        Configuration configuration = new Configuration();
        configuration.setEnvironment(environment);
        configuration.setMapperHelper(mapperHelper);
        configuration.addMapper(TestObjMapper.class);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession session = sqlSessionFactory.openSession();
        session.getMapper(TestObjMapper.class);

        Example example = new Example(TestObj.class);

        SearchParamsUtils.handler(searchParams, example);

        for (Example.Criteria criteria : example.getOredCriteria()) {
            for (Example.Criterion criterion : criteria.getAllCriteria()) {
                System.out.println(criterion.getCondition());
            }
        }
    }
}
