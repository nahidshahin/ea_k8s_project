package edu.miu.product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

  @GetMapping("/products")
  public List<Map<String,Object>> products() {
    return List.of(
      Map.of("id",1,"name","Widget","price",19.99),
      Map.of("id",2,"name","Gadget","price",29.99),
      Map.of("id",3,"name","Doodad","price",9.99)
    );
  }

  @GetMapping("/version")
  public String version() {
    return "product-service:v1";
  }

}
