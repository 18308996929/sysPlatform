package com.sys.library.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sys.library.dto.LibraryDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：Li.Yong
 * @date ：Created in 2022/9/16 0:14
 * @description：
 */
@Mapper
public interface LibraryMapper {
    List<LibraryDto> getBookInfo(LibraryDto libraryDto);
}
