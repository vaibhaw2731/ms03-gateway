Creating VM running Jenkins:
*Create new VM instance.
*Select N2 machine configuration for faster computation
*Boot disk as Ubuntu-18.04 LTS with SSD of 64 GB is used.
*Under Identity and API access Select
Allow full access to all Cloud APIs
\*After the VM is created: 1. Install Java (Update and install Java)
sudo apt update
sudo apt install openjdk-8-jdk

    	2. Jenkins
    		( https://linuxize.com/post/how-to-install-jenkins-on-ubuntu-18-04/ )
    		a. Add the Jenkins Debian repository
    			wget -q -O - https://pkg.jenkins.io/debian/jenkins.io.key | sudo apt-key add -
    			sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'

    		b. Install Jenkins
    			sudo apt update
    			sudo apt install jenkins
    			systemctl status jenkins (Check Status if installed properly or not)
    			sudo systemctl restart jenkins

    		Adjust firewall accordingly ( Open port 8080 )

    		c. Launch Jenkins
    			http://your_ip_or_domain:8080
    			sudo cat /var/lib/jenkins/secrets/initialAdminPassword (Location to Password)
    			Install suggested Plugins
    			Install additional plugins :
    				1. Pipeline Utility Steps
    				2. Kubernetes Continuous Deploy
    			Add credentials :
    				1. GIT_CREDENTIALS - Username/Password of the github
    				2. DOCKER_CRED - Secret text (Password of the docker hub a/c)
    			Global configuration of Jenkins
    				Add Maven with name : Maven-3.6.2
    	3. Docker
    		a. Install Docker
    			curl -fsSL get.docker.com | /bin/bash
    		b. Add Jenkins user to docker group
    			sudo usermod -aG docker jenkins
    		c. Restart Jenkins
    			sudo systemctl restart jenkins

    	4. Install gcloud
    		a. Add the Cloud SDK distribution URI as a package source
    			echo "deb [signed-by=/usr/share/keyrings/cloud.google.gpg] http://packages.cloud.google.com/apt cloud-sdk main" | sudo tee -a /etc/apt/sources.list.d/google-cloud-sdk.list

    		b. Import the Google Cloud Platform public key
    			curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key --keyring /usr/share/keyrings/cloud.google.gpg add -

    		c. Update the package list and install the Cloud SDK
    			sudo apt-get update && sudo apt-get install google-cloud-sdk
    	5. Install kubectl
    		a. Download kubectl
    			curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl

    		b. Make the kubectl binary executable.
    			chmod +x ./kubectl

    		c. Move the binary in to your PATH.
    			sudo mv ./kubectl /usr/local/bin/kubectl

    		d. Test to ensure the version you installed is up-to-date:
    			kubectl version

Creating a kubernetes engine:
*Select kubernetes engine on GCP console.
*Give name to the cluster
_Mention the number of nodes : default-3  

Deploying application on kubernetes cluster from your local computer:
_ Create the jhipster application with mongodb.
_ Inside the application folder run "jhipster kubernetes" : 1. Give a valid name to the kubernetes namespace. 2. Put Docker repository name as the username of your docker hub a/c.
_ Inside the application folder run the following command for docker build :
mvnw -ntp -Pprod verify jib:dockerBuild
_ Tag your application with docker image name using the following command
docker image tag <tagnane> vaibhaw2731/<tagename>
_ Push your docker image
docker push vaibhaw2731/<tagname>
_ Connect your local computer to the gcp kubernetes using the command in connect to the cluster
For example : gcloud container clusters get-credentials vaiagraw-kubernetes1 --zone us-central1-a --project payment-platform-204588
_ Inside the application folder run the following command to deploy the application:
sh kubectl-apply.sh
_ The application will be deployed on the kubernetes engine.
_ Go to services & Ingress in the sidebar to see the external ip of the application.

Deploying application on kubernetes cluster through Jenkins pipeline:
_ Create the jhipster application with mongodb.
_ Inside the application folder run "jhipster kubernetes" : 1. Give a valid name to the kubernetes namespace. 2. Put Docker repository name as the username of your docker hub a/c. \* Upload the application folder to the github repository.

    Jenkins pipeline :
    	node{

    stage("Git clone"){

        git credentialsId: 'GIT_CREDENTIALS', url: '<link to the github repository>'

    }

    stage("Maven clean build"){

        def mavenHome = tool name:"Maven-3.6.2",type:"maven"
        def mavenCMD="${mavenHome}/bin/mvn clean package"

        sh "${mavenCMD}"
    }

    stage("Build docker image"){

                def mavenHome = tool name:"Maven-3.6.2",type:"maven"
                def mavenCMD="${mavenHome}/bin/mvn -ntp -Pprod verify jib:dockerBuild"



                sh "${mavenCMD}"
            }





    stage("Docker Push"){

        withCredentials([string(credentialsId: 'DOCKER_CRED', variable: 'DOCKER_CRED')]) {
            sh "docker login -u <docker_hub_username> -p ${DOCKER_CRED}"
        }

        sh "docker image tag ms03 <docker_hub_username>/ms03"
        sh "docker push <docker_hub_username>/ms03"
    }
    stage("Deploy"){
        sh "<connection to the kubernetes cluster command>"
        sh "sh kubectl-apply.sh"
    }

}

For example:  
node{
  
 stage("Git clone"){
  
 git credentialsId: 'GIT_CREDENTIALS', url: 'https://github.com/vaibhaw2731/ms03-gateway.git'
  
 }
  
 stage("Maven clean build"){
  
 def mavenHome = tool name:"Maven-3.6.2",type:"maven"
def mavenCMD="${mavenHome}/bin/mvn clean package"
        
        sh "${mavenCMD}"
}
  
 stage("Build docker image"){
  
 def mavenHome = tool name:"Maven-3.6.2",type:"maven"
def mavenCMD="\${mavenHome}/bin/mvn -ntp -Pprod verify jib:dockerBuild"

                sh "${mavenCMD}"
            }





    stage("Docker Push"){

        withCredentials([string(credentialsId: 'DOCKER_CRED', variable: 'DOCKER_CRED')]) {
            sh "docker login -u vaibhaw2731 -p ${DOCKER_CRED}"
        }

        sh "docker image tag ms03 vaibhaw2731/ms03"
        sh "docker push vaibhaw2731/ms03"
    }
    stage("Deploy"){
        sh "gcloud container clusters get-credentials vaiagraw-kubernetes1 --zone us-central1-a --project payment-platform-204588"
        sh "sh kubectl-apply.sh"
    }

}
