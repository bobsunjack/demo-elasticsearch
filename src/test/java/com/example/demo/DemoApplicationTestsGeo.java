package com.example.demo;

import com.example.demo.dao.CityRepository;
import com.example.demo.entity.City;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTestsGeo {
    @Autowired
    CityRepository cityRepository;
    @Test
    public void contextLoads() {
        Long beginTime = System.currentTimeMillis();
        PageRequest request = new PageRequest(1,10);
        Page<City> cities = findPage(1.0, 1.0, "1", request);
        int size=cities.getContent().size();
        System.out.println("用时 :"+(System.currentTimeMillis()-beginTime)/1000);
        /*PageRequest request = new PageRequest(0, 10);
        Page page = cityRepository.findByDescription("人民", request);
        int p = page.getContent().size();
        assert p == 1;*/
        assert size == 10;
    }




    public Page<City> findPage(double latitude, double longitude, String distance, Pageable pageable) {
        // 间接实现了QueryBuilder接口
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        // 以某点为中心，搜索指定范围
        GeoDistanceQueryBuilder distanceQueryBuilder = new GeoDistanceQueryBuilder("location");
        distanceQueryBuilder.point(latitude, longitude);
        // 定义查询单位：公里
        distanceQueryBuilder.distance(distance, DistanceUnit.KILOMETERS);
        boolQueryBuilder.filter(distanceQueryBuilder);

        ///boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("name", "*1"));
        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("1")//.escape(true)//escape 转义 设为true，避免搜索[]、结尾为!的关键词时异常 但无法搜索*
                       .defaultOperator(QueryStringQueryBuilder.DEFAULT_OPERATOR.AND);//不同关键词之间使用and关系
        boolQueryBuilder.must(queryBuilder);//添加第4条must的条件 关键词全文搜索筛选条件

        return cityRepository.search(boolQueryBuilder, pageable);
    }

}

