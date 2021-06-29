package com.whaa.blog.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * created by wangzelong 2021-06-29 17:07
 */
@Data
@AllArgsConstructor
public class Session {
    private String userId;

    private String userName;
}
