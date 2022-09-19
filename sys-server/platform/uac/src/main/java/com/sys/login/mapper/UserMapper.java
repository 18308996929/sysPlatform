package com.sys.login.mapper;

import com.sys.common.model.userSecurity.SysMenu;
import com.sys.common.model.userSecurity.SysUser;
import com.sys.login.dto.LibraryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author ：Li.Yong
 * @date ：Created in 2022/9/16 0:14
 * @description：
 */
@Mapper
public interface UserMapper {
    SysUser getUser(SysUser user);
    List<SysMenu> getPermsByUserId(@Param("userId") Long userId);
    List<LibraryDto> getBookInfo(LibraryDto libraryDto);
}
