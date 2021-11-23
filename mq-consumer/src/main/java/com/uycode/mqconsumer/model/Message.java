package com.uycode.mqconsumer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ahmatjan(UyCode)
 * @email Hyper-Hack@outlook.com
 * @since 11/23/2021
 */

@Getter
@Setter
@ToString
public class Message {

    private int id;
    private String message;
}
