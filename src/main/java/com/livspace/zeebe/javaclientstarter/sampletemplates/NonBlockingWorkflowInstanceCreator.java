package com.livspace.zeebe.javaclientstarter.sampletemplates;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.ZeebeClientBuilder;
import io.zeebe.client.api.ZeebeFuture;
import io.zeebe.client.api.response.WorkflowInstanceEvent;

public class NonBlockingWorkflowInstanceCreator {
  public static void main(final String[] args) {
    final String broker = "127.0.0.1:26500";
    final int numberOfInstances = 100;
    final String bpmnProcessId = "order-process";

    final ZeebeClientBuilder builder =
        ZeebeClient.newClientBuilder().brokerContactPoint(broker).usePlaintext();

    try (ZeebeClient client = builder.build()) {
      System.out.println("Creating " + numberOfInstances + " workflow instances");

      final long startTime = System.currentTimeMillis();

      long instancesCreating = 0;

      while (instancesCreating < numberOfInstances) {
        // this is non-blocking/async => returns a future
        final ZeebeFuture<WorkflowInstanceEvent> future =
            client.newCreateInstanceCommand().bpmnProcessId(bpmnProcessId).latestVersion().send();

        // could put the future somewhere and eventually wait for its completion

        instancesCreating++;
      }

      // creating one more instance; joining on this future ensures
      // that all the other create commands were handled
      client.newCreateInstanceCommand().bpmnProcessId(bpmnProcessId).latestVersion().send().join();

      System.out.println("Took: " + (System.currentTimeMillis() - startTime));
    }
  }
}
