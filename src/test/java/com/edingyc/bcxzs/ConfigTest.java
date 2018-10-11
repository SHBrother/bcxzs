package com.edingyc.bcxzs;

import com.edingyc.bcxzs.Utils.config.AppletConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: bcxzs
 * @description: config test
 * @author: YSM
 * @create: 2018-10-08 11:00
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ConfigTest {

    @Autowired
    AppletConfig appletConfig;

    @Test
    public void configTest(){
        Assert.assertEquals(appletConfig.getAppid(), "wx4a7829f2688c8ce8");
    }


}
