package io.renren;

import io.renren.modules.app.utils.JwtUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTest {
    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void test() throws InterruptedException {
        String token = jwtUtils.generateToken(1);
        Thread.sleep(100);
        String token2 = jwtUtils.generateToken(1);

        System.out.println("t1:"+token+"t2+:"+token2);
    }

}
