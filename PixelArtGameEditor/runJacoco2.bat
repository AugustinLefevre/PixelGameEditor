path C:\Users\augustin.lefevre\Documents\tools\jdk-17.0.2\bin;%path%
mkdir classes
javac -cp junit-4.12.jar:classes C:\Users\augustin.lefevre\Documents\repo_git-perso\PixelGameEditor\PixelArtGameEditor\src\* -d classes
mkdir test-classes
javac -cp junit-4.12.jar:classes C:\Users\augustin.lefevre\Documents\repo_git-perso\PixelGameEditor\PixelArtGameEditor\test\*.java -d test-classes
java -javaagent:C:\Users\augustin.lefevre\Documents\repo_git-perso\PixelGameEditor\jacoco\lib\jacocoagent.jar -cp junit-4.12.jar:hamcrest-core-1.3.jar:classes:test-classes org.junit.runner.JUnitCore TileTest
java -jar C:\Users\augustin.lefevre\Documents\repo_git-perso\PixelGameEditor\jacoco\lib\jacococli.jar report jacoco.exec --classfiles classes --sourcefiles src --html report
pause