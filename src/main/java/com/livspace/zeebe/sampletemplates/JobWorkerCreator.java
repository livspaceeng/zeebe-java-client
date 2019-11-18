package com.livspace.zeebe.sampletemplates;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.ZeebeClientBuilder;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.client.api.worker.JobHandler;
import io.zeebe.client.api.worker.JobWorker;
import java.time.Duration;

public class JobWorkerCreator {

  public static void main(final String[] args) {
    final String broker = "127.0.0.1:26500";

    final String jobType = "payment-service";

    final ZeebeClientBuilder builder =
        ZeebeClient.newClientBuilder().brokerContactPoint(broker).usePlaintext();

    try (ZeebeClient client = builder.build()) {

      System.out.println("Opening job worker.");

      final JobWorker workerRegistration =
          client
              .newWorker()
              .jobType(jobType)
              .handler(new ExampleJobHandler())
              .timeout(Duration.ofSeconds(10))
              .open();

      System.out.println("Job worker opened and receiving jobs.");

      workerRegistration.close();
    }
  }

  private static class ExampleJobHandler implements JobHandler {
    @Override
    public void handle(final JobClient client, final ActivatedJob job) {
      // here: business logic that is executed with every job
      System.out.println(job);
      client.newCompleteCommand(job.getKey()).send().join();
    }
  }
}
