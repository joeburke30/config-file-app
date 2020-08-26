#!/bin/bash
#kubectl create secret generic spring-security --from-literal=spring.user.name=user1 --from-literal=spring.user.password=MyPassword
kubectl apply -f kube/dynamicsecrets_deployment.yaml
