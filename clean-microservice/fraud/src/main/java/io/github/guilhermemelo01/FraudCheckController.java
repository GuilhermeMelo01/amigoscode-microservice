package io.github.guilhermemelo01;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/fraud-check")
@Slf4j
public class FraudCheckController {

    private final FraudCheckService fraudCheckService;

    @GetMapping(path = "{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") String customerId){
        Boolean isFraudlentCustomer = fraudCheckService.isFraudlentCustomer(customerId);
        log.info("fraud check to request for customer {}", customerId);
        return new FraudCheckResponse(isFraudlentCustomer);
    }
}
