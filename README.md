编译准备
1.数据监测平台将pub-interface进行打包：
mvn clean:assembly:assembly


2.将jar包安装到本地maven库 
mvn install:install-file -Dfile=pub-interface-0.0.1-SNAPSHOT.jar -DgroupId=ty -DartifactId=pub-interface -Dversion=0.0.1 -Dpackaging=jar
