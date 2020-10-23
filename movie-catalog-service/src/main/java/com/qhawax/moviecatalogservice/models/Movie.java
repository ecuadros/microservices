package com.qhawax.moviecatalogservice.models;

public class Movie
{
    public String movieId;
    public String name;
    public String overview;

    public Movie()
    {
    }

    public Movie(String movieId, String name, String overview)
    {
        this.movieId = movieId;
        this.name = name;
        this.overview = overview;
    }

    public String getOverview()
    {
        return overview;
    }

    public void setOverview(String overview)
    {
        this.overview = overview;
    }

    public String getMovieId()
    {
        return movieId;
    }

    public void setMovieId(String movieId)
    {
        this.movieId = movieId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
