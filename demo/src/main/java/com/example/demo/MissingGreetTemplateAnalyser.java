package com.example.demo;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

class MissingGreetTemplateAnalyser extends AbstractFailureAnalyzer<MissingGreetTemplate> {

	@Override
	protected FailureAnalysis analyze(Throwable rootFailure, MissingGreetTemplate cause) {
		return new FailureAnalysis(
				cause.getMessage(),
				"Define greet.template property.",
				rootFailure);
	}
}
