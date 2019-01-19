package com.example.demo;

import com.example.demo.dao.CityRepository;
import com.example.demo.entity.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    CityRepository cityRepository;
    @Test
    public void contextLoads() {
        Long beginTime = System.currentTimeMillis();
        Long count=1l;
        while (count<(10000*10)) {
            City city = new City();
            city.setId(count);
            city.setDescription("中车人民解放军"+count);
            city.setName("飞机大炮天安门");
            cityRepository.save(city);
            count++;
            if (count % 1000 == 0) {
                System.out.println("count" + count);
            }
        }
        System.out.println("用时 :"+(System.currentTimeMillis()-beginTime)/1000);
        /*PageRequest request = new PageRequest(0, 10);
        Page page = cityRepository.findByDescription("人民", request);
        int p = page.getContent().size();
        assert p == 1;*/
    }

}

