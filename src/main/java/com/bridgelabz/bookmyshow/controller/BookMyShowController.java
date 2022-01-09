package com.bridgelabz.bookmyshow.controller;



import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BookMyShowController {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "bookMyShowFallback")
    @GetMapping("/booknow")
    public String bookMyShow(){
        String emailServiceResponse = restTemplate.
                getForObject("http://localhost:8081/emailservice/send", String.class);
        String paymentServiceResponse = restTemplate.
                getForObject("http://localhost:8083/paymentservice/pay", String.class);

        return emailServiceResponse+"/n"+paymentServiceResponse;
    }
    @GetMapping("/booknowWithoutHystrix")
    public String bookMyShowwithoutHystrix(){
        String emailServiceResponse = restTemplate.
                getForObject("http://localhost:8081/emailservice/send", String.class);
        String paytmservice = restTemplate.
                getForObject("http://localhost:8082/paymentservice/pay", String.class);
        return emailServiceResponse+"/n"+paytmservice;

    }

    public String bookMyShowFallback(){
        return "Service gateway failed...";
    }
}
