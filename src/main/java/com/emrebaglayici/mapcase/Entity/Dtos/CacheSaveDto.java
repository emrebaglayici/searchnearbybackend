package com.emrebaglayici.mapcase.Entity.Dtos;


import com.emrebaglayici.mapcase.Entity.Cache;
import lombok.Setter;

@Setter
public class CacheSaveDto {
    private double latitude;
    private double longitude;
    private double radius;

    public Cache toCache() {
        return Cache.builder()
                .latitude(this.latitude)
                .longitude(this.longitude)
                .radius(this.radius)
                .build();
    }
}
