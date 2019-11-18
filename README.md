# zeebe-java-client
Java Client example code to get started with Zeebe.

#### BPMN Modeler
To create BPMN workflows, please download [zeebe-modeler] (https://github.com/zeebe-io/zeebe-modeler/releases/tag/v0.7.0).
**Mac users:** [zeebe-modeler-0.7.0-mac.dmg] (https://github.com/zeebe-io/zeebe-modeler/releases/download/v0.7.0/zeebe-modeler-0.7.0-mac.dmg)
**Windows users:** [zeebe-modeler-0.7.0-win-x64.zip] (https://github.com/zeebe-io/zeebe-modeler/releases/download/v0.7.0/zeebe-modeler-0.7.0-win-x64.zip)

#### Setting up Zeebe broker on local machine
1. Intall docker on your local machine.
**Mac users:** [Install docker desktop on Mac] (https://docs.docker.com/docker-for-mac/install/)
**Windows users:** [Install docker desktop on Windows] (https://docs.docker.com/docker-for-windows/install/)
2. Run the following command from terminal or shell: `> docker-compose up`

#### Simple Monitor View
Just navigate to `http://localhost:8082/` on your browser, and you would be able to see your Active/Deployed workflows.

Now, you can run your application and connect to localhost to deploy and run workflows!
