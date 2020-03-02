package net.guerlab.spring.searchparams.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.guerlab.spring.searchparams.SearchParamsUtils;

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

        QueryWrapper queryWrapper = new QueryWrapper();

        SearchParamsUtils.handler(searchParams, queryWrapper);

        System.out.println(queryWrapper.getSqlSegment());
        queryWrapper.getParamNameValuePairs().forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
