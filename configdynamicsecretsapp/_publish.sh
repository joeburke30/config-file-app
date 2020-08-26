#!/bin/bash

scp target/joeburke30-dynamicsecrettest-1.0.tar.gz root@192.168.72.161:/tmp/.
ssh -t root@192.168.72.161 "docker load -i /tmp/joeburke30-dynamicsecrettest-1.0.tar.gz"


