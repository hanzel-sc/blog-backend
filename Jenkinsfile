pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'Maven 3'
        TERRASCAN = '/usr/local/bin/terrascan'
        TFSEC = '/usr/local/bin/tfsec'
    }
    tools {
        terraform 'Terraform'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'pentest', url: 'https://github.com/hanzel-sc/blog-backend.git'
            }
        }


        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        // Linux commands
                        sh "${MAVEN_HOME}/bin/mvn test"
                        sh "${MAVEN_HOME}/bin/mvn clean verify"
                        sh "${MAVEN_HOME}/bin/mvn clean install"
                    } else {
                        // Windows commands
                        bat "${MAVEN_HOME}\\bin\\mvn test"
                        bat "${MAVEN_HOME}\\bin\\mvn clean verify"
                        bat "${MAVEN_HOME}\\bin\\mvn clean install"
                    }
                }
            }
        }

        stage('Run Pentest Scan') {
                    steps {
                        sh 'chmod +x scripts/scan.sh'
                        sh './scripts/scan.sh'
                    }
                }
        stage('Publish Reports') {
            steps {
                archiveArtifacts artifacts: 'reports/*.json', fingerprint: true
            }
        }
    }
}


