package com.emrebaglayici.mapcase.Controller;

import com.emrebaglayici.mapcase.Business.Abstracts.IGeocoder;
import com.emrebaglayici.mapcase.Entity.Response;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class GeocoderController {

    private final IGeocoder iGeocoder;

    public GeocoderController(IGeocoder iGeocoder) {
        this.iGeocoder = iGeocoder;
    }

    @GetMapping("locations")
    public Response getGeoDetails(@RequestParam double lat, @RequestParam double lng, @RequestParam int radius) {
        return iGeocoder.getGeoDetails(lat, lng, radius);
    }
}
