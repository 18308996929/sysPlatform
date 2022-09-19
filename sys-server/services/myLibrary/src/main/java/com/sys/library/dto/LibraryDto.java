package com.sys.library.dto;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author ：Li.Yong
 * @date ：Created in 2022/9/16 0:11
 * @description：
 */
@Data
public class LibraryDto {
    Long id;
    String name; // 书籍名称
    Integer status; // 0在库  1 借出
}
