#!/usr/bin/env bash

wordCount=$(echo "" | wc -w)

if [ "$wordCount" -gt 0 ]; then
    echo "Data"
fi

N=10
echo $N
until [ $N -gt 0 ]; do
    echo $N
    N=`expr $N - 1`
done