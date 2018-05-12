package com.yada.demo.OutTranServer.dao;

import com.yada.demo.OutTranServer.model.Trans;
import com.yada.demo.OutTranServer.util.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TransDaoTest {

    @Autowired
    private TransDao transDao;

    @Before
    public void before(){
        Trans trans = new Trans();
        trans.setTranDate(DateUtils.getCurrent());
        transDao.save(trans);
    }

    @Test
    public void findByTranDateBetween() {
        List<Trans> transList = transDao.findByTranDateBetween(DateUtils.getTodayStart(), DateUtils.getTodayEnd());
        assertEquals(1, transList.size());
    }
}