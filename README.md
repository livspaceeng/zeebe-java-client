# zeebe-java-client-starter
Starter code to get started with Zeebe Java client.

## BPMN Modeler
To create BPMN workflows, please download [zeebe-modeler](https://github.com/zeebe-io/zeebe-modeler/releases/tag/v0.8.0).
- **Mac users:** [zeebe-modeler-0.9.1-mac.dmg](https://github.com/zeebe-io/zeebe-modeler/releases/download/v0.9.1/zeebe-modeler-0.9.1-mac.dmg)
- **Windows users:** [zeebe-modeler-0.9.1-win-x64.zip](https://github.com/zeebe-io/zeebe-modeler/releases/download/v0.9.1/zeebe-modeler-0.9.1-mac.zip)

## Setting up Zeebe broker on local machine:
- Prerequisites:
  + [Docker](https://docs.docker.com/install/)
  + [Docker Compose](https://docs.docker.com/compose/install/)
- Clone this repo and run the following command from terminal or shell (inside the cloned directory): `> docker-compose up`. It will pickup the [docker-compose.yml](./docker-compose.yml) present in the cloned directory.

## Simple Monitor View
Just navigate to `http://localhost:8082/` on your browser, and you would be able to see your Active/Deployed workflows.

Now, you can run your application and connect to localhost to deploy and run workflows! Enjoy!

## Other Clients
- [Go client](https://github.com/zeebe-io/zeebe/tree/master/clients/go)
- [Python client](https://pypi.org/project/zeebe-grpc/) : Community edition
- [Node.js](https://github.com/CreditSenseAU/zeebe-client-node-js) : Community edition
