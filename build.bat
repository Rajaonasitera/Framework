cd Framework/
javac -d . *.java
jar -cvf ../TestFramework/WEB-INF/lib/Framework.jar etu1814
jar -cvf ../TestFramework/java/Framework.jar etu1814
cd ../TestFramework/java
jar xf Framework.jar
javac -d ../WEB-INF/classes *.java
cd ..
jar -cvf D:\Tomcat\webapps\Framework.war WEB-INF *.jsp
cd ..