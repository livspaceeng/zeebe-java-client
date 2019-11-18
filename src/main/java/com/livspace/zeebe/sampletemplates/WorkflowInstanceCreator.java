package com.livspace.zeebe.sampletemplates;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.ZeebeClientBuilder;
import io.zeebe.client.api.response.WorkflowInstanceEvent;

public class WorkflowInstanceCreator {

  public static void main(final String[] args) {
    final String broker = "127.0.0.1:26500";

    final String bpmnProcessId = "order-process";

    final ZeebeClientBuilder builder =
        ZeebeClient.newClientBuilder().brokerContactPoint(broker).usePlaintext();

    try (ZeebeClient client = builder.build()) {

      System.out.println("Creating workflow instance");

      final WorkflowInstanceEvent workflowInstanceEvent =
          client
              .newCreateInstanceCommand()
              .bpmnProcessId(bpmnProcessId)
              .latestVersion()
              .send()
              .join();

      System.out.println(
          "Workflow instance created with key: " + workflowInstanceEvent.getWorkflowInstanceKey());
    }
  }
}
