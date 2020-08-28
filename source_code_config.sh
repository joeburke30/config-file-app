#!/bin/bash

while true
do
   echo ""
   echo "$(date)"
   curl http://192.168.72.161:8888/
   sleep 2
done	
