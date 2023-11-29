pipeline {
    agent any

    stages {
        stage('拉取git代码') {
            steps {
                checkout scmGit(branches: [[name: '${TAG}']], extensions: [], userRemoteConfigs: [[credentialsId: 'ced296a9-58b8-4e41-926e-e13a1885d932', url: 'https://git.anyserver.online:20443/tqsummer/hello-jenkins.git']])
            }
        }
        stage('Maven构建代码') {
            steps {
                sh '/var/jenkins_home/softwares/apache-maven-3.9.5/bin/mvn clean package -DskipTests'
            }
        }
        stage('检测代码质量') {
            steps {
                sh '/var/jenkins_home/softwares/sonar-scanner-5.0.1.3006/bin/sonar-scanner -Dsonar.projectname=${JOB_NAME} -Dsonar.projectKey=${JOB_NAME} -Dsonar.sources=./ -Dsonar.java.binaries=./jenkins-app01/target -Dsonar.login=sqa_5f9d4bad8e7739b80018c2666b5cc0c4e0b64c59'
            }
        }
        stage('上传镜像到Harbor') {
            steps {
                sh '''mkdir -p docker-build
                    cp jenkins-app01/docker/Dockerfile docker-build
                    cp jenkins-app01/target/*.jar docker-build
                    IMAGE_NAME=${JOB_NAME}:${TAG}
                    HARBOR_IMAGE_NAME=docker-hub.anyserver.online/my-jenkins-app/${IMAGE_NAME}
                    docker buildx build -t ${IMAGE_NAME} docker-build/
                    docker login -u appdev -p Appdev12345 docker-hub.anyserver.online
                    docker tag ${IMAGE_NAME} ${HARBOR_IMAGE_NAME}
                    docker push ${HARBOR_IMAGE_NAME}
                    docker rmi ${HARBOR_IMAGE_NAME}
                    docker rmi ${IMAGE_NAME}
                    rm -fr docker-build'''
            }
        }
        stage('上传Docker-compose文件到服务器') {
            steps {
                sshPublisher(publishers: [sshPublisherDesc(configName: 'dev-server01', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: 'jenkins-app01/docker/docker-compose.*')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            }
        }
        stage('服务部署') {
            steps {
                echo '服务部署成功'
            }
        }
    }
    post {
        success {
            dingtalk(
                    robot: 'dingding-jenkins-bot',
                    type: 'MARKDOWN',
                    title: "success: ${JOB_NAME}",
                    text: ["- 成功构建：${JOB_NAME}! \n- 版本：${TAG} \n- 持续时间：${currentBuild.durationString}" ]
            )
        }
        failure {
            dingtalk(
                    robot: 'dingding-jenkins-bot',
                    type: 'MARKDOWN',
                    title: "success: ${JOB_NAME}",
                    text: ["- 成功构建：${JOB_NAME}! \n- 版本：${TAG} \n- 持续时间：${currentBuild.durationString}" ]
            )
        }
    }
}
