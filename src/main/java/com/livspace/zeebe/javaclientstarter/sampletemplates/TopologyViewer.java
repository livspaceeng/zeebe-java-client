package com.livspace.zeebe.javaclientstarter.sampletemplates;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.ZeebeClientBuilder;
import io.zeebe.client.api.response.Topology;

public class TopologyViewer {

  public static void main(final String[] args) {
    final String broker = "api.dev.livspace.com:8080/zeebe";

    final ZeebeClientBuilder builder =
        ZeebeClient.newClientBuilder().brokerContactPoint(broker).usePlaintext();

    try (ZeebeClient client = builder.build()) {
      System.out.println("Requesting topology with initial contact point " + broker);

      final Topology topology = client.newTopologyRequest().send().join();

      System.out.println("Topology:");
      topology
          .getBrokers()
          .forEach(
              b -> {
                System.out.println("    " + b.getAddress());
                b.getPartitions()
                    .forEach(
                        p ->
                            System.out.println(
                                "      " + p.getPartitionId() + " - " + p.getRole()));
              });

      System.out.println("Done.");
    }
  }
}
