#! /bin/bash

days="${1:-20}"

http POST "localhost:8080/api/time-machine/${days}"