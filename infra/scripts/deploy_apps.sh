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
aksName="aks-$program-$locationShort-$project-$stageShort-$instance"

# Zipkin
declare -A zipkin
zipkin["name"]="zipkin"
zipkin["namespace"]="zipkin"

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

# Fourth
declare -A fourth
fourth["name"]="fourth"
fourth["namespace"]="fourth"

# Fifth
declare -A fifth
fifth["name"]="fifth"
fifth["namespace"]="fifth"

### Build & Push

# First
echo -e "\n--- FIRST ---\n"
docker build \
    --build-arg newRelicAppName=${first[name]} \
    --build-arg newRelicLicenseKey=$NEWRELIC_LICENSE_KEY \
    --tag "${DOCKERHUB_NAME}/${first[name]}" \
    ../../apps/first/.
docker push "${DOCKERHUB_NAME}/${first[name]}"
echo -e "\n------\n"

# Second
echo -e "\n--- SECOND ---\n"
docker build \
    --tag "${DOCKERHUB_NAME}/${second[name]}" \
    ../../apps/second/.
docker push "${DOCKERHUB_NAME}/${second[name]}"
echo -e "\n------\n"

# Third
echo -e "\n--- THIRD ---\n"
docker build \
    --build-arg newRelicAppName=${third[name]} \
    --build-arg newRelicLicenseKey=$NEWRELIC_LICENSE_KEY \
    --tag "${DOCKERHUB_NAME}/${third[name]}" \
    ../../apps/third/.
docker push "${DOCKERHUB_NAME}/${third[name]}"
echo -e "\n------\n"

# Fourth
echo -e "\n--- FOURTH ---\n"
docker build \
    --tag "${DOCKERHUB_NAME}/${fourth[name]}" \
    ../../apps/fourth/.
docker push "${DOCKERHUB_NAME}/${fourth[name]}"
echo -e "\n------\n"

# Fifth
echo -e "\n--- FIFTH ---\n"
docker build \
    --tag "${DOCKERHUB_NAME}/${fifth[name]}" \
    ../../apps/fifth/.
docker push "${DOCKERHUB_NAME}/${fifth[name]}"
echo -e "\n------\n"

# Newrelic
echo "Deploying Newrelic ..."

kubectl apply -f https://download.newrelic.com/install/kubernetes/pixie/latest/px.dev_viziers.yaml && \
kubectl apply -f https://download.newrelic.com/install/kubernetes/pixie/latest/olm_crd.yaml && \
helm repo add newrelic https://helm-charts.newrelic.com && helm repo update && \
kubectl create namespace newrelic ; helm upgrade --install newrelic-bundle newrelic/nri-bundle \
    --wait \
    --debug \
    --set global.licenseKey=$NEWRELIC_LICENSE_KEY \
    --set global.cluster=$aksName \
    --namespace=newrelic \
    --set newrelic-infrastructure.privileged=true \
    --set global.lowDataMode=true \
    --set ksm.enabled=true \
    --set kubeEvents.enabled=true \
    --set prometheus.enabled=true \
    --set logging.enabled=true \
    --set newrelic-pixie.enabled=true \
    --set newrelic-pixie.apiKey=$PIXIE_API_KEY \
    --set pixie-chart.enabled=true \
    --set pixie-chart.deployKey=$PIXIE_DEPLOY_KEY \
    --set pixie-chart.clusterName=$aksName

# Ingress Controller
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

# Zipkin
echo "Deploying Zipkin ..."

helm upgrade ${zipkin[name]} \
    --install \
    --wait \
    --debug \
    --create-namespace \
    --namespace ${zipkin[namespace]} \
    --set dockerhubName=$DOCKERHUB_NAME \
    ../charts/zipkin

# First
echo "Deploying first ..."

helm upgrade ${first[name]} \
    --install \
    --wait \
    --debug \
    --create-namespace \
    --namespace ${first[namespace]} \
    --set dockerhubName=$DOCKERHUB_NAME \
    ../charts/first

# Second
echo "Deploying second ..."

helm upgrade ${second[name]} \
    --install \
    --wait \
    --debug \
    --create-namespace \
    --namespace ${second[namespace]} \
    --set dockerhubName=$DOCKERHUB_NAME \
    ../charts/second

# Third
echo "Deploying third ..."

helm upgrade ${third[name]} \
    --install \
    --wait \
    --debug \
    --create-namespace \
    --namespace ${third[namespace]} \
    --set dockerhubName=$DOCKERHUB_NAME \
    ../charts/third

# Fourth
echo "Deploying fourth ..."

helm upgrade ${fourth[name]} \
    --install \
    --wait \
    --debug \
    --create-namespace \
    --namespace ${fourth[namespace]} \
    --set dockerhubName=$DOCKERHUB_NAME \
    ../charts/fourth

# Fifth
echo "Deploying fifth ..."

helm upgrade ${fifth[name]} \
    --install \
    --wait \
    --debug \
    --create-namespace \
    --namespace ${fifth[namespace]} \
    --set dockerhubName=$DOCKERHUB_NAME \
    --set newRelicLicenseKey=$NEWRELIC_LICENSE_KEY \
    ../charts/fifth
