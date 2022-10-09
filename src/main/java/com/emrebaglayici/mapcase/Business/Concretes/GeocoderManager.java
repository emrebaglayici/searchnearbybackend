package com.emrebaglayici.mapcase.Business.Concretes;


import com.emrebaglayici.mapcase.Business.Abstracts.IGeocoder;
import com.emrebaglayici.mapcase.Entity.Cache;
import com.emrebaglayici.mapcase.Entity.Dtos.CacheSaveDto;
import com.emrebaglayici.mapcase.Entity.Response;
import com.emrebaglayici.mapcase.Repository.CacheRepository;
import com.emrebaglayici.mapcase.Security.API;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;


@Service
public class GeocoderManager implements IGeocoder {
    private final CacheRepository cacheRepository;

    public GeocoderManager(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    @Override
    public Response getGeoDetails(double lat, double lng, int radius) {
        ResponseEntity<Response> response = null;
        Optional<Cache> cache = cacheRepository.customFind(lat, lng, radius);
        if (cache.isEmpty()) {
            CacheSaveDto cacheSaveDto = new CacheSaveDto();
            cacheSaveDto.setLatitude(lat);
            cacheSaveDto.setLongitude(lng);
            cacheSaveDto.setRadius(radius);
            cacheRepository.save(cacheSaveDto.toCache());
            String location = lat + "," + lng;
            UriComponents uri = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host("maps.googleapis.com")
                    .path("/maps/api/place/nearbysearch/json")
                    .queryParam("location", location)
                    .queryParam("radius", radius)
                    .queryParam("key", API.API_KEY)
                    .build();


            response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);

        } else {
            String location = cache.get().getLatitude() + "," + cache.get().getLongitude();
            UriComponents uri = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host("maps.googleapis.com")
                    .path("/maps/api/place/nearbysearch/json")
                    .queryParam("location", location)
                    .queryParam("radius", cache.get().getRadius())
                    .queryParam("key", API.API_KEY)
                    .build();
            response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);
        }


        return response.getBody();
    }
}
