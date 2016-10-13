package com.tripint.intersight.entity.mine.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eric on 16/10/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecialitiesNameEntity implements Serializable {

    private int id;
    private int pid;
    private String name;
    private List<SpecialitiesSubEntity> specialitiesSubEntityList;
}
