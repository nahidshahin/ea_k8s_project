## Prerequisites

For local env install following tools:

  

- Docker

- kubectl

- kind

- Maven

  

### Create kind cluster:

```
kind create cluster --name ea-cluster
```  

## Build images and load into kind:


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
```
  

Port-forward to access order-service (this exposes order-service on localhost:8081)

```
kubectl -n ea-project port-forward svc/order-service 8081:8080
```
  

now visit http://localhost:8081/api/order

  
## Install Ingress controller for load balancing

```
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml
kubectl apply -f k8s/order-service-ingress.yaml
```

### In /etc/hosts on your machine:
```
127.0.0.1 local.pc
```

### Port forward

```
kubectl port-forward --namespace ingress-nginx service/ingress-nginx-controller 80:80
```
