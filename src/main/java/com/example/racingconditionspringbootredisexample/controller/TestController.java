package com.example.racingconditionspringbootredisexample.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @Author : Ragil Gilang Maulana
 * @Date : 12/07/22
 **/

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final RedisTemplate redisTemplate;

    @GetMapping
    public Boolean allowedRequest(@RequestParam("name") String name){
        try {
            var isAllowed = false;

            long count = redisTemplate.opsForValue().increment(name, 1);
            log.debug("count : {}", count);

            if (count == 1) {
                redisTemplate.expire(name, 10, TimeUnit.SECONDS);
                isAllowed=true;
            }

            return isAllowed;
        }catch (Exception e){
            log.error("Error ",e);
            return true;
        }
    }

}
