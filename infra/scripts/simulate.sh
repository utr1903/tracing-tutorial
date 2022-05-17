#!/bin/bash

makeRestCall() {

    local methodName=$1

    local randomValue=$(openssl rand -base64 12)
    local randomTag=$(openssl rand -base64 12)

    echo -e "---\n"

    curl -X POST "http://${clusterIp}/first/${methodName}" \
        -i \
        -H "Content-Type: application/json" \
        -d \
        '{
            "value": "'"${randomValue}"'",
            "tag": "'"${randomTag}"'"
        }'

    echo -e "\n"
    sleep 2
}

clusterIp=$(kubectl get svc \
    -n nginx \
    ingress-nginx-controller \
    -o json \
    | jq -r '.status.loadBalancer.ingress[0].ip')

echo "Cluster external IP: $clusterIp"

while true
do
    makeRestCall "method1"
    makeRestCall "method2"
    makeRestCall "method3"
done
