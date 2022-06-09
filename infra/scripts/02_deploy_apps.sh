#!/bin/bash

##################
### Apps Setup ###
##################

### Set parameters
program="ugur"
locationLong="westeurope"
locationShort="euw"
project="tracing"
stageLong="dev"
stageShort="d"
instance="001"

### Set variables

# AKS
aksName="aks$program$locationShort$project$stageShort$instance"

# Proxy
declare -A proxy
proxy["name"]="proxy"
proxy["namespace"]="proxy"

# First
declare -A first
first["name"]="first"
first["namespace"]="first"

# Second
declare -A second
second["name"]="second"
second["namespace"]="second"

# Third
declare -A third
third["name"]="third"
third["namespace"]="third"

# Zipkin Server
declare -A zipkinserver
zipkinserver["name"]="zipkinserver"
zipkinserver["namespace"]="third"

# Zipkin Exporter
declare -A zipkinexporter
zipkinexporter["name"]="zipkinexporter"
zipkinexporter["namespace"]="third"

####################
### Build & Push ###
####################

# # Proxy
# echo -e "\n--- Proxy ---\n"
# docker build \
#     --build-arg newRelicAppName=${proxy[name]} \
#     --build-arg newRelicLicenseKey=$NEWRELIC_LICENSE_KEY \
#     --tag "${DOCKERHUB_NAME}/${proxy[name]}" \
#     "../../apps/${proxy[name]}/."
# docker push "${DOCKERHUB_NAME}/${proxy[name]}"
# echo -e "\n------\n"

# First
echo -e "\n--- First ---\n"
docker build \
    --tag "${DOCKERHUB_NAME}/${first[name]}" \
    "../../apps/${first[name]}/."
docker push "${DOCKERHUB_NAME}/${first[name]}"
echo -e "\n------\n"

# Second
echo -e "\n--- Second ---\n"
docker build \
    --build-arg newRelicAppName=${second[name]} \
    --build-arg newRelicLicenseKey=$NEWRELIC_LICENSE_KEY \
    --tag "${DOCKERHUB_NAME}/${second[name]}" \
    "../../apps/${second[name]}/."
docker push "${DOCKERHUB_NAME}/${second[name]}"
echo -e "\n------\n"

# Third
echo -e "\n--- Third ---\n"
docker build \
    --tag "${DOCKERHUB_NAME}/${third[name]}" \
    "../../apps/${third[name]}/."
docker push "${DOCKERHUB_NAME}/${third[name]}"
echo -e "\n------\n"

# Zipkin Exporter
echo -e "\n--- Zipkin Exporter ---\n"
docker build \
    --tag "${DOCKERHUB_NAME}/${zipkinexporter[name]}" \
    "../../apps/${zipkinexporter[name]}/."
docker push "${DOCKERHUB_NAME}/${zipkinexporter[name]}"
echo -e "\n------\n"
#########

# ################
# ### Newrelic ###
# ################
# echo "Deploying Newrelic ..."

# kubectl apply -f https://download.newrelic.com/install/kubernetes/pixie/latest/px.dev_viziers.yaml && \
# kubectl apply -f https://download.newrelic.com/install/kubernetes/pixie/latest/olm_crd.yaml && \
# helm repo add newrelic https://helm-charts.newrelic.com && helm repo update && \
# kubectl create namespace newrelic ; helm upgrade --install newrelic-bundle newrelic/nri-bundle \
#     --wait \
#     --debug \
#     --set global.licenseKey=$NEWRELIC_LICENSE_KEY \
#     --set global.cluster=$aksName \
#     --namespace=newrelic \
#     --set newrelic-infrastructure.privileged=true \
#     --set global.lowDataMode=true \
#     --set ksm.enabled=true \
#     --set kubeEvents.enabled=true \
#     --set prometheus.enabled=true \
#     --set logging.enabled=true \
#     --set newrelic-pixie.enabled=true \
#     --set newrelic-pixie.apiKey=$PIXIE_API_KEY \
#     --set pixie-chart.enabled=true \
#     --set pixie-chart.deployKey=$PIXIE_DEPLOY_KEY \
#     --set pixie-chart.clusterName=$aksName
# #########

##########################
### Ingress Controller ###
##########################
echo "Deploying Ingress Controller ..."

helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx && \
helm repo update; \
helm upgrade --install ingress-nginx ingress-nginx/ingress-nginx \
    --namespace nginx --create-namespace \
    --wait \
    --debug \
    --set controller.replicaCount=1 \
    --set controller.nodeSelector."kubernetes\.io/os"="linux" \
    --set controller.image.image="ingress-nginx/controller" \
    --set controller.image.tag="v1.1.1" \
    --set controller.image.digest="" \
    --set controller.service.externalTrafficPolicy=Local \
    --set controller.admissionWebhooks.patch.nodeSelector."kubernetes\.io/os"="linux" \
    --set controller.admissionWebhooks.patch.image.image="ingress-nginx/kube-webhook-certgen" \
    --set controller.admissionWebhooks.patch.image.tag="v1.1.1" \
    --set controller.admissionWebhooks.patch.image.digest="" \
    --set defaultBackend.nodeSelector."kubernetes\.io/os"="linux" \
    --set defaultBackend.image.image="defaultbackend-amd64" \
    --set defaultBackend.image.tag="1.5" \
    --set defaultBackend.image.digest=""
#########

#############
### Proxy ###
#############
echo "Deploying proxy..."

helm upgrade ${proxy[name]} \
    --install \
    --wait \
    --debug \
    --create-namespace \
    --namespace ${proxy[namespace]} \
    --set dockerhubName=$DOCKERHUB_NAME \
    "../charts/${proxy[name]}"

# appIdOfProxy=$(curl -X GET 'https://api.eu.newrelic.com/v2/applications.json' \
#     -H "Api-Key:${NEWRELIC_API_KEY}" \
#     -H "Content-Type: application/json" \
#     -H "Accept: application/json" \
#     | jq -r '.applications[] | select(.name==''"'${proxy[name]}'"'') | .id')

# timestampOfProxy=$(date -u +%Y-%m-%dT%H:%M:%SZ)
# curl -X POST "https://api.eu.newrelic.com/v2/applications/$appIdOfProxy/deployments.json" \
#      -i \
#      -H "Api-Key:${NEWRELIC_API_KEY}" \
#      -H "Content-Type: application/json" \
#      -d \
#     '{
#         "deployment": {
#             "revision": "1.0.0",
#             "changelog": "Initial deployment",
#             "description": "Deploy the proxy app.",
#             "user": "datanerd@example.com",
#             "timestamp": "'"${timestampOfProxy}"'"
#         }
#     }'
#########

# First app
echo "Deploying first app..."

helm upgrade ${first[name]} \
    --install \
    --wait \
    --debug \
    --create-namespace \
    --namespace ${first[namespace]} \
    --set dockerhubName=$DOCKERHUB_NAME \
    "../charts/${first[name]}"
#########

# Second app
echo "Deploying second app..."

helm upgrade ${second[name]} \
    --install \
    --wait \
    --debug \
    --create-namespace \
    --namespace ${second[namespace]} \
    --set dockerhubName=$DOCKERHUB_NAME \
    "../charts/${second[name]}"
#########

#################
### Third App ###
#################

# Zipkin Server
echo "Deploying Zipkin server..."

helm upgrade ${zipkinserver[name]} \
    --install \
    --wait \
    --debug \
    --create-namespace \
    --namespace ${zipkinserver[namespace]} \
    --set dockerhubName=$DOCKERHUB_NAME \
    "../charts/${zipkinserver[name]}"

echo "Deploying third app..."

# Third app
helm upgrade ${third[name]} \
    --install \
    --wait \
    --debug \
    --create-namespace \
    --namespace ${third[namespace]} \
    --set dockerhubName=$DOCKERHUB_NAME \
    "../charts/${third[name]}"

# Zipkin Exporter
echo "Deploying Zipkin exporter..."

helm upgrade ${zipkinexporter[name]} \
    --install \
    --wait \
    --debug \
    --create-namespace \
    --namespace ${zipkinexporter[namespace]} \
    --set dockerhubName=$DOCKERHUB_NAME \
    --set newRelicLicenseKey=$NEWRELIC_LICENSE_KEY \
    "../charts/${zipkinexporter[name]}"
#########