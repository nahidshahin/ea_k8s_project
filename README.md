### Prerequisites

For local env install following tools:

  

- Docker

- kubectl

- kind

- Maven

  

### Create kind cluster:


kind create cluster --name ea-cluster
  

Build images and load into kind:


### from product-service folder

```  
mvn -DskipTests package
docker build -t product-service:latest .
```
  

### load into kind
```
kind load docker-image product-service:latest --name ea-cluster
```
  

### from order-service folder
```
mvn -DskipTests package
docker build -t order-service:latest .
kind load docker-image order-service:latest --name ea-cluster
```
  

### Apply k8s manifests

```
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/product-deployment.yaml
kubectl apply -f k8s/product-service.yaml
kubectl apply -f k8s/order-deployment.yaml
kubectl apply -f k8s/order-service.yaml
kubectl apply -f k8s/hpa-product.yaml
```
  

Port-forward to access order-service (this exposes order-service on localhost:8081)

```
kubectl -n ea-demo port-forward svc/order-service 8081:8080
```
  

now visit http://localhost:8081/api/orders

  
