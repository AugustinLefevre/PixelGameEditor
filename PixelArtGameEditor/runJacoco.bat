
path C:\Users\augustin.lefevre\Documents\tools\jdk-17.0.2\bin;%path%
javac -cp junit-4.12.jar:classes C:\Users\augustin.lefevre\Documents\repo_git-perso\PixelGameEditor\PixelArtGameEditor\test\*.java

jar cvfm temp.jar manifest.txt *.class

java -javaagent:C:\Users\augustin.lefevre\Documents\repo_git-perso\PixelGameEditor\jacoco\lib\jacocoagent.jar=destfile=jacoco.exec -jar temp.jar

java -jar C:\Users\augustin.lefevre\Documents\repo_git-perso\PixelGameEditor\jacoco\lib\jacococli.jar report  --classfiles "temp.jar" --html "C:\Users\augustin.lefevre\Documents\repo_git-perso\PixelGameEditor\PixelArtGameEditor\report" --sourcefiles "C:\Users\augustin.lefevre\Documents\repo_git-perso\PixelGameEditor\PixelArtGameEditor\src"

pause