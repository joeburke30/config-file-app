#!/bin/bash
kubectl delete secret spring-security
kubectl create secret generic spring-security --from-literal=spring.user.name=demo1 --from-literal=spring.user.password=MyPassword
kubectl apply -f kube/kubesecret_deployment.yaml
