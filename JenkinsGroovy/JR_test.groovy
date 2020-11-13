node {

    def branch = scm.branches[0].name

    stage('checkout') {

        def paramBranch = "${env.BRANCH}"
        def nullStr = "null"
        if(paramBranch?.trim()&&(nullStr!=paramBranch)){
            echo paramBranch
            branch = "${env.BRANCH}"
            echo "set empty"
        }
        echo "代码分支"+branch
        checkout([
                $class: 'GitSCM',
                branches: [[name: branch]],
                doGenerateSubmoduleConfigurations: scm.doGenerateSubmoduleConfigurations,
                extensions: scm.extensions,
                userRemoteConfigs: scm.userRemoteConfigs
        ])

    }

    stage('test') {

        def maven = docker.image('maven:3-alpine')
        maven.inside('-v /root/.m2/:/root/.m2/'){
            sh 'mvn clean package -f pom.xml -Dmaven.test.skip=true'
//            sh 'mvn clean -f xuanzhuang-api-test/pom.xml test '
        }
        sh 'java -jar target/testng-1.0-SNAPSHOT.jar test'
    }


    stage('AfterTest') {
    }





}