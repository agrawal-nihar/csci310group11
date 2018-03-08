mvn cobertura:clean
mvn install
mvn site
mvn cobertura:cobertura > test.log
