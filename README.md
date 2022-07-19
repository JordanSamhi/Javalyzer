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
sh build.sh
</pre>

### Running

<pre>
java -jar target/Javalyzer-0.1-jar-with-dependencies.jar <i>options</i>
</pre>

Options:

* ```-i``` : Input file to analyze (JAR and APK file supported)
* ```-cg``` : Extract Call Graph (CHA, RTA, VTA, SPARK)
* ```-cfg``` : Extract methods control flow graphs (see below for explanation)
* ```-f``` : Set output format (JSON, YAML)
* ```-o``` : Set output folder
* ```-h``` : Print help message

### Usage

#### Extracting Call Graph

To extract the default call graph (CHA), use the following command:

<pre>
java -jar target/Javalyzer-0.1-jar-with-dependencies.jar -i PATH_TO_APP -o OUTPUT_FOLDER -cg
</pre>

To extract the call graph constructed with another algorithm, e.g., SPARK, use the following command:

<pre>
java -jar target/Javalyzer-0.1-jar-with-dependencies.jar -i PATH_TO_APP -o OUTPUT_FOLDER -cg SPARK
</pre>

Both commands will generate the following file: OUTPUT_FOLDER/callgraph.json

#### Extracting Control Flow Graphs

There are two ways to extract control flow graphs from apps:

1. All available methods:

<pre>
java -jar target/Javalyzer-0.1-jar-with-dependencies.jar -i PATH_TO_APP -o OUTPUT_FOLDER -cfg ALL
</pre>

Javalyzer will avoid extracting the control flow graphs of system classes' methods (e.g., android.telephony.TelephonyManager) as well as library classes' methods (e.g., com.facebook).

2. Targeted methods:

<pre>
java -jar target/Javalyzer-0.1-jar-with-dependencies.jar -i PATH_TO_APP -o OUTPUT_FOLDER -cfg class1:method1|...|class2:method2
</pre>

Javalyzer accepts a list of pairs of class name and method name separated by '|'  
Class names and method names are separated by ':'  
classX represents the class name in the form: com.example.MyClass  
methodX represents the method name in the form: myMethod  
You may notice that Javalyzer does not distinguish between methods with same name but different signatures, in this case, the control flow graphs of all methods with the same name will be extracted.  
If methods given as input do not exist, no error are thrown, simply nothing will be extracted.

- Example:

<pre>
java -jar target/Javalyzer-0.1-jar-with-dependencies.jar -i PATH_TO_APP -o OUTPUT_FOLDER -cfg com.example.MainActivity:onCreate
</pre>


Both commands will generate the following file: OUTPUT_FOLDER/controlflowgraph.json

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## License
This project is licensed under the GNU LESSER GENERAL PUBLIC LICENSE 2.1 - see the [LICENSE](LICENSE) file for details

## Contact

For any question regarding Javalyzer, please contact me at:
[Jordan Samhi](mailto:jordan.samhi@uni.lu)
