package com.tripint.intersight.entity.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/10/10.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDataEntity implements Serializable {
    private String time;
    private String content;
    private int status;
}
