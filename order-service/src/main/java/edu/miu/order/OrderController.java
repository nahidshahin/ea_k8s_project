package edu.miu.order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class OrderController {

  private final WebClient webClient;

  public OrderController(WebClient.Builder builder) {
    this.webClient = builder.baseUrl("http://product-service:8080/api").build();
  }

  @GetMapping("/order")
  public Mono<String> makeOrder() {
    return webClient.get().uri("/products")
      .retrieve()
      .bodyToMono(String.class)
      .map(body -> "Order placed. Got products: " + (body.length()>0 ? "OK" : "EMPTY"))
      .onErrorReturn("Order failed: product-service unavailable");
  }

  @GetMapping("/version")
  public String version() { return "order-service:v1"; }
}
