pipeline{
 agent any

  tools{
   maven "3.6.3"
   'org.jenkinsci.plugins.docker.commons.tools.DockerTool' 'docker'
  }

 stages{
   stage("Build Mvn"){
     steps{
       sh "mvn -version"
       sh "mvn clean"
       sh "mvn install"
      }
   }
   stage("Build Docker Image"){
     steps{
       sh "docker build -t zidan3/worldnav ."
          }
   }
   stage("Push Docker Image"){
     steps{
      withCredentials([string(credentialsId: 'docker-pwd', variable: 'dockerHubPassword')]) {
      sh "docker login -u zidan3 -p ${dockerHubPassword}"
           }
      sh "docker push zidan3/worldnav"
          }
       }
   }
  post{
    always{
    //Always fresh workspace, just a good practice
     cleanWs()
    }
  }
 }
