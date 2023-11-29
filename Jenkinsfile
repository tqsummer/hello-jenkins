pipeline {
    agent any

    stages {
        stage('拉取git代码') {
            steps {
                echo '拉取git代码成功'
            }
        }
        stage('检测代码质量') {
            steps {
                echo '检测代码质量成功'
            }
        }
        stage('构建代码') {
            steps {
                echo '构建代码成功'
            }
        }
        stage('上传镜像到Harbor') {
            steps {
                echo '上传镜像到Harbor成功'
            }
        }
        stage('服务部署') {
            steps {
                echo '服务部署成功'
            }
        }
    }
}
