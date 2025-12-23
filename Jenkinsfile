pipeline {
    agent any
    options {
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
        // 保留最近 10 次构建的日志
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
    
    stages {
        stage('Checkout') {
            agent any
            steps {
                checkout scm
                script {
                    env.GIT_COMMIT_SHORT = sh(
                        script: "git rev-parse --short HEAD",
                        returnStdout: true
                    ).trim()
                    env.VUE_IMAGE_NAME = "vue-app:${env.GIT_COMMIT_SHORT}"
                    env.JAVA_IMAGE_NAME = "java-app:${env.GIT_COMMIT_SHORT}"
                    env.PYTHON_IMAGE_NAME = "python-app:${env.GIT_COMMIT_SHORT}"
                    env.COMPOSE_PROJECT_NAME = "python-project-${env.BUILD_NUMBER}"
                }
            }
        }
        stage('Test Applications') {
            parallel {
                stage('Test Python Project') {
                    agent {
                        node {
                            label 'build-in'
                        }
                    }
                    steps {
                        dir('python-project') {
                            sh '''
                                COMPOSE_PROJECT_NAME=${COMPOSE_PROJECT_NAME} \
                                docker compose -f docker-compose.test.yml \
                                up --build --exit-code-from app --remove-orphans
                            '''
                        }
                    }
                }
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
                stage('Build Python Project') {
                    agent {
                        node {
                            label 'build-in'
                        }
                    }
                    steps {
                        dir('python-project') {
                            sh 'docker build -t ${PYTHON_IMAGE_NAME} .'
                        }
                    }
                }   
            }
        }
    }
    post {
        always {
            script {
                if (!env.COMPOSE_PROJECT_NAME) {
                    env.COMPOSE_PROJECT_NAME = "python-project-${env.BUILD_NUMBER}"
                }
                if (fileExists('python-project/docker-compose.test.yml')) {
                    dir('python-project') {
                        sh '''
                            COMPOSE_PROJECT_NAME=${COMPOSE_PROJECT_NAME} \
                            docker compose -f docker-compose.test.yml \
                            down -v --remove-orphans
                        '''
                    }
                }
            }
            echo ""
            echo "===== Build Summary ====="
            sh '''
                echo "Branch: ${GIT_BRANCH}"
                echo "Commit: ${GIT_COMMIT_SHORT}"
                echo "Build Number: ${BUILD_NUMBER}"
                echo ""
                echo "Docker Images:"
                docker images | grep -E "vue-app|java-app|python-app" || echo "No images"
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
