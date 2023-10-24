### Updated README.md Content

```markdown
# Simple Benchmark Project

This is a Maven project for benchmarking, packaged with JMH and Docker.

## Building the Docker Image

Run the following command to build the Docker image:

```
docker build -t simple-benchmark-project .
```

This will create a Docker image named `simple-benchmark-project`.

## Running the Docker Container

Once the Docker image is built, you can run a container using the following command:

```
docker run --rm -it simple-benchmark-project
```

This will start a container from the `simple-benchmark-project` image. The `--rm` flag ensures that the container is removed after it exits, and the `-it` flags allocate a pseudo-TTY and keep STDIN open even if not attached, which are useful for interactive applications.

### Important Note
Ensure that the `Main-Class` attribute in your JAR's manifest is properly set to the class that contains your benchmark's `main` method. Update the `pom.xml` file with the `maven-jar-plugin` configuration to specify the main class.

## Benchmarking

Once the container is running, it will automatically start executing the benchmarks defined in your project. Ensure that your benchmark classes and methods are properly annotated according to JMH's requirements, and that the `main` method is set up to execute the benchmarks.
