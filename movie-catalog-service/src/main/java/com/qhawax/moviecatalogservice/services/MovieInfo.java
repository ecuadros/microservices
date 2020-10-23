package com.qhawax.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.qhawax.moviecatalogservice.models.CatalogItem;
import com.qhawax.moviecatalogservice.models.Movie;
import com.qhawax.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo
{
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating)
    {
        //String url2 = "http://localhost:8082/movies/" + rating.getMovieId(); System.out.println(url2);
        String url2 = "http://movie-info-service/movies/" + rating.getMovieId();
        Movie movie = restTemplate.getForObject(url2, Movie.class);
        // Put them all together
        return new CatalogItem(movie.getName(), movie.getOverview(), rating.getRating());
    }

    private CatalogItem getFallbackCatalogItem(Rating rating)
    {
        return new CatalogItem("Movie name not found", "", rating.getRating());
    }
}
