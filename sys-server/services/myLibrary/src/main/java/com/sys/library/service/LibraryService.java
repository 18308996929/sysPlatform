package com.sys.library.service;

import com.sys.common.model.R;
import com.sys.common.model.ResultBody;
import com.sys.library.dto.LibraryDto;

/**
 * @author ：Li.Yong
 * @date ：Created in 2022/9/15 22:50
 * @description：
 */
public interface LibraryService {
    R qryBookInfo(LibraryDto libraryDto);
}
