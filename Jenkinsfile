pipeline {
    agent any
    environment {
        MAVEN_HOME = tool 'Maven 3'
    }
    stages {
        stage('Checkout') {
            steps {
               git branch: 'develop', url: 'https://github.com/hanzel-sc/blog-backend.git'
            }
        }
        stage('Build') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn clean install"
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                    pkill -f blog-backend.jar || true
                    cp target/blog-backend.jar ~/deployed/
                    nohup java -jar ~/deployed/blog-backend-0.0.1-SNAPSHOT.jar > ~/deployed/log.txt 2>&1 &

                '''
            }
        }
    }
}