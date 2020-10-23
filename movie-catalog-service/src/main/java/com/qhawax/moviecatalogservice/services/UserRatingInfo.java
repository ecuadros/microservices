package com.qhawax.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.qhawax.moviecatalogservice.models.Rating;
import com.qhawax.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfo
{
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating",
                    commandProperties = {
                            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
                    }

    )
    public UserRating getUserRating(String userId)
    {
        //String url1 = "http://localhost:8083/ratingsdata/users/" + userId;
        String url1 = "http://ratings-data-service/ratingsdata/users/" + userId;
        UserRating userRating = restTemplate.getForObject(url1, UserRating.class);
        return userRating;
    }
    private UserRating getFallbackUserRating(String userId)
    {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setUserRating(
                Arrays.asList( new Rating("0", 0))
        );
        return userRating;
    }
}
