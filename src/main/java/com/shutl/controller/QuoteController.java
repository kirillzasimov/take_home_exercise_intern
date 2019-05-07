package com.shutl.controller;

import com.shutl.model.Quote;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class QuoteController {

    @RequestMapping(value = "/quote", method = POST)
    public @ResponseBody Quote quote(@RequestBody Quote quote) {
        System.out.println(quote.getDeliveryPostcode());
        Long price = Math.abs((Long.valueOf(quote.getDeliveryPostcode(), 36) - Long.valueOf(quote.getPickupPostcode(), 36))/100000000);
        try {
            switch (quote.getVehicle()) {
                case "bicycle":
                    price = new Double(price * 1.1).longValue();
                    break;

                case "motorbike":
                    price = new Double(price * 1.15).longValue();
                    break;

                case "parcel_car":
                    price = new Double(price * 1.2).longValue();
                    break;

                case "small_van":
                    price = new Double(price * 1.3).longValue();
                    break;

                case "large_van":
                    price = new Double(price * 1.4).longValue();
                    break;
            }
        } catch (NullPointerException e){
            return new Quote(quote.getPickupPostcode(), quote.getDeliveryPostcode(), price);
        }

        return new Quote(quote.getPickupPostcode(), quote.getDeliveryPostcode(), quote.getVehicle(), price);
    }
}
