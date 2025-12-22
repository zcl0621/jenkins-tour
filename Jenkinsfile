pipeline {
    agent any
    options {
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
        // 保留最近 10 次构建的日志
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
    
    environment {
        GIT_COMMIT_SHORT = sh(
            script: "git rev-parse --short HEAD",
            returnStdout: true
        ).trim()
        VUE_IMAGE_NAME = "vue-app:${GIT_COMMIT_SHORT}"
        JAVA_IMAGE_NAME = "java-app:${GIT_COMMIT_SHORT}"
    }

    stages {
        stage('Checkout') {
            agent any
            steps {
                checkout scm
            }
        }
        stage('Build Applications') {
            parallel {
                stage('Build Vue Project') {
                    agent {
                        node {
                            label 'agent-1'
                        }
                    }
                    steps {
                        dir('vue-project') {
                            sh 'docker build -t ${VUE_IMAGE_NAME} .'
                        }
                    }
                }
                stage('Build Java Project') {
                    agent {
                        node {
                            label 'build-in'
                        }
                    }
                    steps {
                        dir('java-project') {
                            sh 'docker build -t ${JAVA_IMAGE_NAME} .'
                        }
                    }
                }   
            }
        }
    }
    post {
        always {
            echo ""
            echo "===== Build Summary ====="
            sh '''
                echo "Branch: ${GIT_BRANCH}"
                echo "Commit: ${GIT_COMMIT_SHORT}"
                echo "Build Number: ${BUILD_NUMBER}"
                echo ""
                echo "Docker Images:"
                docker images | grep -E "vue-app|java-app" || echo "No images"
            '''
        }
        success {
            echo 'Build success'   
        }
        failure {
            echo 'Build failed'   
        }
    }
}