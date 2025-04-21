pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'Maven 3'
        SONARQUBE = 'SonarQubeServer'
        SONAR_PROJECT_KEY = 'blog-backend'
        SONAR_HOST_URL = 'http://192.168.25.214:9000'
        SONAR_TOKEN = credentials('sonar-token')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'develop', url: 'https://github.com/hanzel-sc/blog-backend.git'
            }
        }

        stage('Build') {
            steps {
                bat "${MAVEN_HOME}/bin/mvn test"
                bat "${MAVEN_HOME}/bin/mvn clean install"
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${env.SONARQUBE}") {
                    bat "${MAVEN_HOME}/bin/mvn sonar:sonar"
                }
            }
        }

        stage('Check Sonar Issues') {
            steps {
                script {
                    def count = bat (
                        script: """
                        curl -s -u %SONAR_TOKEN%: "%SONAR_HOST_URL%/api/issues/search?componentKeys=%SONAR_PROJECT_KEY%&severities=BLOCKER,CRITICAL" | jq '.total'
                        """,
                        returnStdout: true
                    ).trim()

                    if (count.toInteger() > 0) {
                        error "Build stopped: Found $count critical Sonar issues"
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                bat '''
                taskkill /F /IM java.exe || echo "No running java process found"
                copy target\\blog-backend.jar C:\\Jenkins\\deployed\\blog-backend.jar
                start java -jar C:\\Jenkins\\deployed\\blog-backend.jar > C:\\Jenkins\\deployed\\log.txt 2>&1
                '''
            }
        }
    }

    post {
        failure {
            emailext (
                subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Build failed during SonarQube analysis.\n\nSonar Dashboard: ${SONAR_HOST_URL}/dashboard?id=${SONAR_PROJECT_KEY}",
                to: "marcusfitzerbach@gmail.com"
            )
        }
    }
}
