package edu.miu.order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.net.InetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class OrderController {
  private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

  private final WebClient webClient;

  public OrderController(WebClient.Builder builder) {
    this.webClient = builder.baseUrl("http://product-service:8080/api").build();
  }

  @GetMapping("/order")
  public Mono<String> makeOrder() throws java.net.UnknownHostException {
    String hostname = InetAddress.getLocalHost().getHostName();
    logger.info("Received get order call in hostname = " + hostname);
    return webClient.get().uri("/products")
      .retrieve()
      .bodyToMono(String.class)
      .map(body -> "Order placed. Got products: " + (body.length()>0 ? body : "EMPTY"))
      .onErrorReturn("Order failed: product-service unavailable");
  }

  @GetMapping("/version")
  public String version() throws java.net.UnknownHostException { 
    String hostname = InetAddress.getLocalHost().getHostName();
    return "order-service:v1, hostname=" + hostname; 
  }
}
