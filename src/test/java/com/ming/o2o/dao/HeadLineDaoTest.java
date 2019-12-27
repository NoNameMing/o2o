package com.ming.o2o.dao;

import com.ming.o2o.BaseTest;
import com.ming.o2o.entity.HeadLine;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HeadLineDaoTest extends BaseTest {
    @Autowired
    private HeadLineDao headLineDao;

    @Test
    @Ignore
    public void testQueryHeadLine() {
        // 所有头条列表
        List<HeadLine> headLineList = headLineDao.queryHeadLine(new HeadLine());
        assertEquals(0, headLineList.size());
    }
}
