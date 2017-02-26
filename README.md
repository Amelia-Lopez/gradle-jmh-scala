I found that writing JMH benchmarks in Scala using IntelliJ with Gradle as my build tool was non-trivial,
so I created this example project as a proof of concept.

The following versions were used:

| Library/Tool  | Version       |
| ------------- | ------------- |
| Gradle        | 3.1           |
| JMH           | 1.17.4        |
| Scala         | 2.12.1        |
| Java          | 1.8.0_121     |
| IntelliJ      | 2016.3.4      |

## JMH and Gradle

This part is straightforward as there is a great plugin that will set up the Gradle build for you:  
https://github.com/melix/jmh-gradle-plugin

The plugin supports multiple JVM languages and works with Java out of the box.
Java benchmarks should be placed in the `src/jmh/java` directory,
and you would run JMH using `./gradlew jmh`.

## Benchmarks in Scala

You can add support for Scala as you normally would in a Gradle build.

```groovy
plugins {
    id 'scala'
    id 'me.champeau.gradle.jmh' version '0.3.1'
}

repositories {
    mavenCentral()
}

dependencies {
    compile 'org.scala-lang:scala-library:2.12.1'
}
```

With this setup, Scala benchmarks should be placed in the `src/jmh/scala` directory.

## Coding in IntelliJ

My hope was to be able to easily run the JMH benchmarks from within IntelliJ,
but unfortunately the only JMH plugin for IntelliJ exclusively supports Java.
You can still do so by running the `jmh` Gradle task from within IntelliJ,
but at that point, it doesn't really buy you anything over running it from the command line.

In addition to adding the `idea` plugin to the Gradle build,
you also have to tell IntelliJ to treat the Scala directory like test code in order to find all of the JMH dependencies.
Also, when I tried running `./gradlew idea` and opening the generated IntelliJ project,
I saw some odd behavior.
I found it easier to avoid the Gradle-generated IntelliJ project and to let IntelliJ import the Gradle project itself.

```groovy
plugins {
    id 'scala'
    id 'idea'
    id 'me.champeau.gradle.jmh' version '0.3.1'
}

repositories {
    mavenCentral()
}

dependencies {
    compile 'org.scala-lang:scala-library:2.12.1'
}

idea {
    module {
        testSourceDirs += sourceSets.jmh.scala.srcDirs
    }
}
```

## Potential Issues

### Java and Scala Benchmarks

If you have benchmarks in both Java and Scala, the Gradle build will fail while generating the JAR that JMH runs.
To get around this, configure the following in your Gradle build:

```groovy
jmh {
    duplicateClassesStrategy = 'warn'
}
```

See the JMH Gradle Plugin README section [Duplicate dependencies and classes](https://github.com/melix/jmh-gradle-plugin#duplicate-dependencies-and-classes) for more details.
