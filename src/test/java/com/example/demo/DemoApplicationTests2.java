package com.example.demo;

import com.example.demo.dao.CityRepository;
import com.example.demo.entity.City;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests2 {
    @Autowired
    CityRepository cityRepository;
    @Test
    public void contextLoads() {
        Long beginTime = System.currentTimeMillis();
        Long count=1019999l;
        List batchList = new ArrayList<>();
        while (count<(10000*1500)) {
            Long   beginTime2 = System.currentTimeMillis();
            City city = new City();
            city.setId(count);
            city.setDescription("中车人民解放军"+count);
            city.setName("飞机大炮天安门"+count);
           // cityRepository.save(city);
            //
            GeoPoint point = new GeoPoint();
            point.resetFromString("1,1");
            city.setLocation(point);
            batchList.add(city);
            count++;
            if (count % 6000 == 0) {
                cityRepository.saveAll(batchList);
                System.out.println("count" + count+","+(System.currentTimeMillis()-beginTime2)/1000);
            }
        }
        System.out.println("用时 :"+(System.currentTimeMillis()-beginTime)/1000);
        /*PageRequest request = new PageRequest(0, 10);
        Page page = cityRepository.findByDescription("人民", request);
        int p = page.getContent().size();
        assert p == 1;*/
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

        return cityRepository.search(boolQueryBuilder, pageable);
    }

}

