package org;

import org.openjdk.jmh.annotations.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(1)
@State(Scope.Benchmark)
public class StringReplacementBenchmark {

	private String template;

	@Setup
	public void setup() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://raw.githubusercontent.com/json-iterator/test-data/master/large-file.json"))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		template = response.body();
	}

	@Benchmark
	public String originalImplementation() {
		if (!template.contains("\u0013"))
			return template;

		return template.replace("\u0013", "\\u0013");
	}

	@Benchmark
	public String optimizedImplementation() {
		return replace(template, "\u0013", "\\u0013");
	}

	private static String replace(String input, String target, String replacement) {
		int targetLength = target.length();
		int start = 0;
		int index = input.indexOf(target, start);

		if (index == -1)
			return input;

		StringBuilder result = new StringBuilder(input.length() + 16); // +16 for some padding

		while (index != -1) {
			result.append(input, start, index);
			result.append(replacement);
			start = index + targetLength;
			index = input.indexOf(target, start);
		}

		result.append(input, start, input.length());
		return result.toString();
	}

	public static void main(String[] args) throws Exception {
		org.openjdk.jmh.Main.main(args);
	}
}
