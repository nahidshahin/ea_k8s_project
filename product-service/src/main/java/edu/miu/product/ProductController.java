package edu.miu.product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.net.InetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ProductController {
  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

  @GetMapping("/products")
  public List<Map<String,Object>> products() throws java.net.UnknownHostException {
    String hostname = InetAddress.getLocalHost().getHostName();
    logger.info("Received get products call in hostname = " + hostname);
    return List.of(
      Map.of("id",1,"name","Widget","price",19.99),
      Map.of("id",2,"name","Gadget","price",29.99),
      Map.of("id",3,"name","Doodad","price",9.99)
    );
  }

  @GetMapping("/version")
  public String version() throws java.net.UnknownHostException {
    String hostname = InetAddress.getLocalHost().getHostName();
    return "product-service:v1, hostname=" + hostname;
  }

}
