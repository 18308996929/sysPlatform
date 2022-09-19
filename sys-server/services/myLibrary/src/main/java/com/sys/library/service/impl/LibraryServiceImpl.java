package com.sys.library.service.impl;

import com.sys.common.model.R;
import com.sys.common.model.ResultBody;
import com.sys.library.dto.LibraryDto;
import com.sys.library.repository.LibraryMapper;
import com.sys.library.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：Li.Yong
 * @date ：Created in 2022/9/15 22:50
 * @description：
 */
@Service
@Slf4j
public class LibraryServiceImpl implements LibraryService {

    @Value("${sys.library.clientId}")
    String clientId;

    @Autowired
    LibraryMapper libraryMapper;

    @Override
    public R qryBookInfo(LibraryDto libraryDto) {
        List<LibraryDto> bookInfo = libraryMapper.getBookInfo(libraryDto);
        R ok = R.ok();
        return ok.setData(bookInfo);
    }
}
