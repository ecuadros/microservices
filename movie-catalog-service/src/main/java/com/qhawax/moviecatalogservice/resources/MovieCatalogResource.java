package com.qhawax.moviecatalogservice.resources;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.qhawax.moviecatalogservice.models.UserRating;
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
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private WebClient.Builder webClientBuilder;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId)
	{
//		Movie movie = restTemplate.getForObject("http://localhost:8082/movies/foo", Movie.class);
		WebClient.Builder builder = WebClient.builder();
		// get all rated movie Ids
//		List<Rating> ratings = Arrays.asList(
//				new Rating("1234", 4),
//				new Rating("5678", 3));

		UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/" + userId,
				UserRating.class);

		return ratings.getUserRating().stream().map(rating ->
		{
			// For each movie Id, call movie info service and get details
			Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
			// Put them all together
			return new CatalogItem(movie.getName(), "Description", rating.getRating());
		}).collect(Collectors.toList());
		//return Collections.singletonList(new CatalogItem("Transformers", "Something here", 4));
	}
}

/*			Movie movie = webClientBuilder.build()
					.get()
					.uri("http://localhost:8082/movies/" + rating.getMovieId())
					.retrieve()
					.bodyToMono(Movie.class)
					.block();
 */
