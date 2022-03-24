# Javalyzer

Tool based on Soot to extract different structures from JAVA programs and Android apps.

⚠️ Works better with Java 8

## Getting started

### Downloading the tool

<pre>
git clone https://github.com/JordanSamhi/Javalyzer.git
</pre>

### Installing the tool

<pre>
cd Javalyzer
mvn clean install
</pre>

### Issues

If you stumble upon a stack overflow error while building AndroBomb, increase memory available with this command:

<pre>
export MAVEN_OPTS=-Xss32m
</pre>

Then, try to rebuild.

### Running

<pre>
java -jar target/Javalyzer-0.1-jar-with-dependencies.jar <i>options</i>
</pre>

Options:

* ```-i``` : Input file to analyze
* ```-cg``` : Extract Call Graph
* ```-f``` : Set output format
* ```-o``` : Set output folder
* ```-h``` : Print help message

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## License
This project is licensed under the GNU LESSER GENERAL PUBLIC LICENSE 2.1 - see the [LICENSE](LICENSE) file for details

## Contact

For any question regarding Javalyzer, please contact me at:
[Jordan Samhi](mailto:jordan.samhi@uni.lu)
