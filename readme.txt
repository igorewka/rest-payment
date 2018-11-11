Running the app:
1. Without building/packaging:
    `gradlew bootRun`
2. With building/packaging:
    `gradlew build && java -jar build/libs/rest-payment-1.0-SNAPSHOT.jar`
3. With building/packaging already executed:
    `java -jar build/libs/rest-payment-1.0-SNAPSHOT.jar`

Building the app:
    `gradlew build`

p.s.
If compiled externally(not with the provided build.gradle, e.g. Intellij IDEA or Eclipse)
    then the following javac parameter should be provided: `-parameters`
It's required for Jackson (un)marshalling using constructors with much less annotations.