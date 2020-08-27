#!/bin/bash
kubectl apply -f kube/a2a.yaml
sleep 5
kubectl apply -f kube/dynamicsecrets_deployment.yaml
