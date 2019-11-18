package com.livspace.zeebe.javaclient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.DeploymentEvent;
import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.client.api.worker.JobWorker;

/**
 * Client Application
 *
 * <p>This is a sample app code written only for testing purposes.
 * DO NOT USE THIS AS A TEMPLATE FOR WRITING PRODUCTION READY CODE.
 * For production-ready samples, please refer to:
 * {@link com.livspace.zeebe.sampletemplates} package.
 */
public class App {

  public static void main(String[] args) throws InterruptedException {
    System.out.println(":::: Starting up the Client app! ::::");

    final ZeebeClient client =
        ZeebeClient.newClientBuilder()
            // Change the contact point if needed
            .brokerContactPoint("127.0.0.1:26500") // localhost
            .usePlaintext()
            .build();

    System.out.println("Trying to connect...");

    //     After the client connection is successful
    final DeploymentEvent deployment =
        client.newDeployCommand().addResourceFromClasspath("order-process.bpmn").send().join();
    System.out.println("Connected! Workflow deployed!");

    final int version = deployment.getWorkflows().get(0).getVersion();
    final String processId = deployment.getWorkflows().get(0).getBpmnProcessId();
    System.out.println("WF Version: " + version + " , WF ProcessID: " + processId);

    // After the workflow has been deployed
    // Generating some data
    final Map<String, Object> data = new HashMap<>();
    data.put("orderId", 123);
    data.put("orderItems", Arrays.asList("Item1", "Item2", "Item3"));

    final WorkflowInstanceEvent wfInstance =
        client
            .newCreateInstanceCommand()
            .bpmnProcessId("order-process")
            .latestVersion() // Change the version if required
            .variables(data)
            .send()
            .join();
    final long workflowInstanceKey = wfInstance.getWorkflowInstanceKey();
    System.out.println("Workflow instance created. Key: " + workflowInstanceKey);

    // After the workflow instance is created
    final JobWorker paymentServiceWorker =
        client
            .newWorker()
            .jobType("payment-service")
            .handler(
                (jobClient, job) -> {
                  // Generating some more data
                  final Map<String, Object> variables = job.getVariablesAsMap();
                  System.out.println("Process order: " + variables.get("orderId"));
                  double orderTotal = 100;
                  System.out.println("Collecting money for the order: $" + orderTotal);
                  final Map<String, Object> result = new HashMap<>();
                  result.put("orderTotal", orderTotal);
                  // Completing the job and publishing extra data
                  jobClient.newCompleteCommand(job.getKey()).variables(result).send().join();
                })
            .open();

    final JobWorker itemQueryServiceWorker =
        client
            .newWorker()
            .jobType("item-query-service")
            .handler(
                (jobClient, job) -> {
                  System.out.println("Fetch Items for job: " + job.getKey());
                  jobClient.newCompleteCommand(job.getKey()).send().join();
                })
            .open();

    final JobWorker dispatcherServiceWorker =
        client
            .newWorker()
            .jobType("dispatcher-service")
            .handler(
                (jobClient, job) -> {
                  System.out.println("Shipping Package for job: " + job.getKey());
                  jobClient.newCompleteCommand(job.getKey()).send().join();
                })
            .open();

    // Waiting for the jobs to be completed
    Thread.sleep(5000);

    // Don't close if you need to keep polling to get work
    paymentServiceWorker.close();
    itemQueryServiceWorker.close();
    dispatcherServiceWorker.close();

    client.close();
    System.out.println(":::: Client Connection Closed! ::::");
  }
}
