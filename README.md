# Reactive Commons POC

This project is a Proof of Concept (POC) that sets up two microservices: one responsible for sending messages and another for listening to them, using RabbitMQ as the message broker and the Reactive Commons library for asynchronous communication between services.

The objective is to demonstrate the integration and operation of an event-driven system using modern messaging and reactive programming technologies.

# Usage Instructions

1.  **Prerequisites:**
    *   Docker and Docker Compose installed.
    *   Access to a Kubernetes-compatible environment (optional, if you want to deploy on k8s).

2.  **Start the services with Docker Compose:**
    *   From the project root, run:
        ```sh
        docker-compose up -d
        ```
    *   This will start the necessary containers, including RabbitMQ and the microservices.

3.  **Deploy on Kubernetes (optional):**
    *   The deployment files are located in the `k8s/` folder.
    *   Apply the manifests with:
        ```sh
        kubectl apply -f k8s/
        ```

4.  **Performance Tests:**
    *   Go to the `performance-test/` folder and run the corresponding script for load testing.

5.  **Monitoring:**
    *   Grafana and Prometheus are configured in the `grafana/` folder to monitor the services and messaging.

For more details on configuration and customization, review the files within each specific folder.

# Technical Details

*   **Sender Microservice:**
    *   Exposes an HTTP endpoint to send messages.
    *   Publishes messages to a RabbitMQ exchange using Reactive Commons.

*   **Listener Microservice:**
    *   Subscribes to the RabbitMQ queue and processes received messages reactively.

*   **Broker:**
    *   RabbitMQ is the message broker used for communication between services.

*   **Library:**
    *   [Reactive Commons](https://bancolombia.github.io/reactive-commons-java/) facilitates reactive integration with RabbitMQ and event handling.

# Endpoint Usage Example

Suppose the sender microservice exposes an endpoint to send messages:

```bash
curl --location 'http://localhost:8080/api/v1/sender/send' \
--header 'Content-Type: application/json' \
--data '{
    "title": "Investments",
    "message": "Test notification"
}
'
```

The listener microservice will receive and process this message automatically.

