package com.qhawax.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.qhawax.moviecatalogservice.models.UserRating;
import com.qhawax.moviecatalogservice.services.MovieInfo;
import com.qhawax.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.qhawax.moviecatalogservice.models.CatalogItem;
import com.qhawax.moviecatalogservice.models.Movie;
import com.qhawax.moviecatalogservice.models.Rating;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource
{
//	@Autowired
//	private DiscoveryClient discoveryClient;

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	MovieInfo movieInfo;

	@Autowired
	UserRatingInfo userRatingInfo;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId)
	{
		UserRating userRating = userRatingInfo.getUserRating(userId);
		return userRating.getUserRating().stream()
				.map(rating -> movieInfo.getCatalogItem(rating) )
				.collect(Collectors.toList());
		//return Collections.singletonList(new CatalogItem("Transformers", "Something here", 4));
	}



//	@RequestMapping("/{userId}")
//	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId)
//	{
////		Movie movie = restTemplate.getForObject("http://localhost:8082/movies/foo", Movie.class);
//		WebClient.Builder builder = WebClient.builder();
//		// get all rated movie Ids
////		List<Rating> ratings = Arrays.asList(
////				new Rating("1234", 4),
////				new Rating("5678", 3));
//
//		String url1 = "http://localhost:8083/ratingsdata/users/" + userId;
//		//String url1 = "http://ratings-data-service/ratingsdata/users/" + userId;
//		UserRating ratings = restTemplate.getForObject(url1, UserRating.class);
//		return ratings.getUserRating().stream().map(rating ->
//		{
//			// For each movie Id, call movie info service and get details
//			String url2 = "http://localhost:8082/movies/" + rating.getMovieId();
//			//String url2 = "http://movie-info-service/movies/" + rating.getMovieId();
//			System.out.println(url2);
//			Movie movie = restTemplate.getForObject(url2, Movie.class);
//			// Put them all together
//			return new CatalogItem(movie.getName(), "Description", rating.getRating());
//		}).collect(Collectors.toList());
//		//return Collections.singletonList(new CatalogItem("Transformers", "Something here", 4));
//	}
}

/*			Movie movie = webClientBuilder.build()
					.get()
					.uri("http://localhost:8082/movies/" + rating.getMovieId())
					.retrieve()
					.bodyToMono(Movie.class)
					.block();
 */
