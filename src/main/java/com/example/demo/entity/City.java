package com.example.demo.entity;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import java.io.Serializable;

@Document(indexName = "province2", type = "city")
public class City implements Serializable {
    private static final long serialVersionUID = -1L;

    @GeoPointField
    private GeoPoint location;

    /**
     * 城市编号
     */
    private Long id;
    /**
     * 城市名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer="ik_max_word",fielddata=true)
    private String name;
    /**
     * 描述
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer="ik_max_word",fielddata=true)
    private String description;

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    /**
     * 城市评分
     */

    private Integer score;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
}