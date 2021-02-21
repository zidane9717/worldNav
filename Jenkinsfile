pipeline{
 agent any

  tools{
   maven "3.6.3"
  }

 stages{
   stage("Build Mvn"){
     steps{
       sh "mvn -version"
       sh "mvn clean"
      }
   }
   stage("Build Docker Image"){
     steps{
       sh "docker build -t zidan3/worldnav:1.0 ."
          }
   }
   stage("Push Docker Image"){
     steps{
      withCredentials([string(credentialsId: 'docker-pwd', variable: 'dockerHubPassword')]) {
      sh "docker login -u zidan3 -p ${dockerHubPassword}"
           }
      sh "docker push zidan3/worldnav:1.0"
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